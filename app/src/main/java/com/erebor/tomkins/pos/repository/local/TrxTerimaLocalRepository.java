package com.erebor.tomkins.pos.repository.local;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;

import java.util.Date;
import java.util.List;

public interface TrxTerimaLocalRepository {

    List<TrxTerimaDBModel> getAllTrxTerima();
    int getIncompleteTrxTerima();
    Date getLastUpdate();
    boolean saveTrxTerima(TrxTerimaDBModel trxTerimaDBModel);
}
