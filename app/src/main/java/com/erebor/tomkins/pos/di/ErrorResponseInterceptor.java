package com.erebor.tomkins.pos.di;

import android.text.Html;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import retrofit2.HttpException;

public class ErrorResponseInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private long maxContentLength = Long.MAX_VALUE;

    ErrorResponseInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response result;

        result = chain.proceed(request);

        ResponseBody responseBody = result.body();
        String bodyText = "";

        if (!HttpHeaders.hasBody(result)) return result;

        BufferedSource source = getNativeSource(result);
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                return result;
            }
        }

        if (isPlaintext(buffer))
            bodyText = readFromBuffer(buffer.clone(), charset);

        if (bodyText.isEmpty()) return result;

        if (result.code() == 400) {
            String message;
            try {
                JSONObject jsonObject = new JSONObject(responseBody.string());
                message = jsonObject.getString("message");
                message = message == null ? jsonObject.getString("error") : message;
            } catch (JSONException | IOException e) {
                message = e.getMessage();
            }
            throw new HttpException(createErrorResponse(result.code(), message, responseBody));
        }

        if (result.code() != 200) {
            throw new HttpException(createErrorResponse(result.code(), Html.fromHtml(responseBody.string()).toString(), responseBody));
        }

        return result;
    }


    private retrofit2.Response<?> createErrorResponse(int code, String message, ResponseBody responseBody) {
        return retrofit2.Response.error(responseBody, new Response.Builder()
                .code(code)
                .message(message)
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    private BufferedSource getNativeSource(BufferedSource input, boolean isGzipped) {
        if (isGzipped) {
            GzipSource source = new GzipSource(input);
            return Okio.buffer(source);
        } else {
            return input;
        }
    }

    private BufferedSource getNativeSource(Response response) throws IOException {
        if (bodyGzipped(response.headers())) {
            BufferedSource source = response.peekBody(maxContentLength).source();
            if (source.buffer().size() < maxContentLength) {
                return getNativeSource(source, true);
            } else {
                Log.w("ErrorInterceptor", "gzip encoded response was too long");
            }
        }
        return response.body().source();
    }

    private boolean bodyGzipped(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return "gzip".equalsIgnoreCase(contentEncoding);
    }

    private String readFromBuffer(Buffer buffer, Charset charset) {
        long bufferSize = buffer.size();
        long maxBytes = Math.min(bufferSize, maxContentLength);
        String body = "";
        try {
            body = buffer.readString(maxBytes, charset);
        } catch (EOFException e) {
            body += "EOF";
        }
        if (bufferSize > maxContentLength) {
            body += "MAXLENGTH";
        }
        return body;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
