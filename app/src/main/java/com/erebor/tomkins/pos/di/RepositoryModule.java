package com.erebor.tomkins.pos.di;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.StockReportDao;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDetDao;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.repository.local.StockReportLocalRepository;
import com.erebor.tomkins.pos.repository.local.StockUpdateLocalRepository;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.local.impl.StockReportLocalRepositoryImpl;
import com.erebor.tomkins.pos.repository.local.impl.StockUpdateLocalRepositoryImpl;
import com.erebor.tomkins.pos.repository.local.impl.TrxTerimaLocalRepositoryImpl;
import com.erebor.tomkins.pos.repository.network.TomkinsService;
import com.erebor.tomkins.pos.repository.network.TrxTerimaRemoteRepository;
import com.erebor.tomkins.pos.repository.network.impl.TrxTerimaRemoteRepositoryImpl;
import com.erebor.tomkins.pos.tools.SharedPrefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    StockReportLocalRepository providesStockReportLocalRepository(StockReportDao stockReportDao) {
        return new StockReportLocalRepositoryImpl(stockReportDao);
    }

    @Provides
    @Singleton
    StockUpdateLocalRepository providesStockUpdateLocalRepository(StokRealDao stokRealDao) {
        return new StockUpdateLocalRepositoryImpl(stokRealDao);
    }

    @Provides
    @Singleton
    TrxTerimaLocalRepository providesTrxTerimaLocalRepository(TomkinsDatabase tomkinsDatabase,
                                                              TrxTerimaDao trxTerimaDao, TrxTerimaDetDao trxTerimaDetDao,
                                                              StokRealDao stokRealDao) {
        return new TrxTerimaLocalRepositoryImpl(tomkinsDatabase, trxTerimaDao, trxTerimaDetDao, stokRealDao);
    }



    @Provides
    @Singleton
    TrxTerimaRemoteRepository providesTrxTerimaRemoteRepository(SharedPrefs sharedPrefs,
                                                                TrxTerimaLocalRepository trxTerimaLocalRepository,
                                                                TomkinsService tomkinsService,
                                                                DateConverterHelper dateConverterHelper) {
        return new TrxTerimaRemoteRepositoryImpl(sharedPrefs, trxTerimaLocalRepository, tomkinsService, dateConverterHelper);
    }



}
