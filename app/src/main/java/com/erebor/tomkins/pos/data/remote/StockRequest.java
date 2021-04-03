package com.erebor.tomkins.pos.data.remote;

import com.erebor.tomkins.pos.data.local.model.StokRealDBModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by ryandzhunter on 06/03/18.
 */

public class StockRequest {

    @Expose
    @SerializedName("last_update")
    private Date lastUpdate;

    @Expose
    @SerializedName("data")
    private List<StokRealDBModel> stokRealDBModel;

    public StockRequest(Date lastUpdate, List<StokRealDBModel> stokRealDBModel) {
        this.lastUpdate = lastUpdate;
        this.stokRealDBModel = stokRealDBModel;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<StokRealDBModel> getStokRealDBModel() {
        return stokRealDBModel;
    }

    public void setStokRealDBModel(List<StokRealDBModel> stokRealDBModel) {
        this.stokRealDBModel = stokRealDBModel;
    }
}
