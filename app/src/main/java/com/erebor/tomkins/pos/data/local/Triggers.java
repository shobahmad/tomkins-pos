package com.erebor.tomkins.pos.data.local;

public class Triggers {

    public static String accessFromUser = "CREATE TRIGGER access_user AFTER INSERT ON user FOR EACH ROW " +
            " BEGIN " +
            "   INSERT OR IGNORE INTO access" +
            "   (id, user_id)" +
            "   VALUES (null, new.id);" +
            " END";

}
