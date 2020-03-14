package com.erebor.tomkins.pos.helper.impl;

import android.content.Context;

import com.erebor.tomkins.pos.helper.ResourceHelper;

public class ResourceHelperImpl implements ResourceHelper {
    private Context context;

    public ResourceHelperImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getResourceString(int id, String... args) {
        String string = context.getResources().getString(id);
        for (int arg = 1; arg <= args.length; arg++) {
            String argValue = args[arg - 1];
            if (argValue == null)
                argValue = "";
            string = string.replace("$s" + arg, argValue);
        }
        return string;
    }

    @Override
    public String getResourceString(int id) {
        return context.getResources().getString(id);
    }
}
