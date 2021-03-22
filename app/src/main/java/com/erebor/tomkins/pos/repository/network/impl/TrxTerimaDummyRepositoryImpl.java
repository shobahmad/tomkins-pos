package com.erebor.tomkins.pos.repository.network.impl;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDetDBModel;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.network.TrxTerimaRemoteRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TrxTerimaDummyRepositoryImpl implements TrxTerimaRemoteRepository {
    private final TrxTerimaLocalRepository trxTerimaLocalRepository;

    public TrxTerimaDummyRepositoryImpl(TrxTerimaLocalRepository trxTerimaLocalRepository) {
        this.trxTerimaLocalRepository = trxTerimaLocalRepository;
    }

    @Override
    public TrxTerimaDBModel getTrxTerima() {
        if (trxTerimaLocalRepository.getAllTrxTerima().size() == 1) {
            TrxTerimaDBModel trxTerimaDBModel = new TrxTerimaDBModel();
            trxTerimaDBModel.setNoDO("DO2");
            Calendar calendar = Calendar.getInstance();
            calendar.set(2021, 2, 3);
            trxTerimaDBModel.setTglKirimGBJ(calendar.getTime());
            trxTerimaDBModel.setTglTerimaCnt(null);
            trxTerimaDBModel.setStatusDO(0);

            List<TrxTerimaDetDBModel> trxTerimaDetDBModelList = new ArrayList<>();
            trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO2", "070310C-PAI", "33", 5, 0));
            trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO2", "070310C-PAI", "34", 5, 0));
            trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO2", "070310C-PAI", "36", 10, 0));
            trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO2", "070310C-PAI", "38", 10, 0));
            trxTerimaDBModel.setListDetail(trxTerimaDetDBModelList);
            calendar.setTimeInMillis(System.currentTimeMillis());
            trxTerimaDBModel.setLastUpdate(calendar.getTime());
            return trxTerimaDBModel;
        }
        
        if (trxTerimaLocalRepository.getAllTrxTerima().size() == 2) {
            return null;
        }

        TrxTerimaDBModel trxTerimaDBModel = new TrxTerimaDBModel();
        trxTerimaDBModel.setNoDO("DO1");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 2, 1);
        trxTerimaDBModel.setTglKirimGBJ(calendar.getTime());
        trxTerimaDBModel.setTglTerimaCnt(null);
        trxTerimaDBModel.setStatusDO(0);

        List<TrxTerimaDetDBModel> trxTerimaDetDBModelList = new ArrayList<>();
        trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO1", "110309A-PAI", "39", 10, 0));
        trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO1", "110309A-PAI", "41", 10, 0));
        trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO1", "110309A-PAI", "43", 10, 0));
        trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO1", "070310C-PAI", "33", 10, 0));
        trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO1", "070310C-PAI", "34", 20, 0));
        trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO1", "070310C-PAI", "36", 20, 0));
        trxTerimaDetDBModelList.add(new TrxTerimaDetDBModel("DO1", "070310C-PAI", "38", 20, 0));
        trxTerimaDBModel.setListDetail(trxTerimaDetDBModelList);
        calendar.setTimeInMillis(System.currentTimeMillis());
        trxTerimaDBModel.setLastUpdate(calendar.getTime());
        return trxTerimaDBModel;

    }
}
