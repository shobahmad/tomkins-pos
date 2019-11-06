package com.erebor.tomkins.pos.data.converters;

import java.util.UUID;

public class Generators {
    public static String getRandomUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = String.valueOf(uuid);

        return uuidString.replaceAll("-", "");
    }
}
