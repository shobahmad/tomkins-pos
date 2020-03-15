package com.erebor.tomkins.pos.base;

import java.util.Date;

public interface BaseDatabaseModel {

    void setLastUpdate(Date lastUpdate);
    Date getLastUpdate();
}
