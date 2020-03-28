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
        return context.getResources().getString(id, args);
    }

    @Override
    public String getResourceString(int id) {
        return context.getResources().getString(id);
    }
}
