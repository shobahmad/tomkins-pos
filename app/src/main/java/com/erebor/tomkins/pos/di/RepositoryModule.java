package com.erebor.tomkins.pos.di;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;
import com.erebor.tomkins.pos.data.local.dao.StockReportDao;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDao;
import com.erebor.tomkins.pos.data.local.dao.TrxTerimaDetDao;
import com.erebor.tomkins.pos.repository.local.StockReportLocalRepository;
import com.erebor.tomkins.pos.repository.local.StockUpdateLocalRepository;
import com.erebor.tomkins.pos.repository.local.TrxTerimaLocalRepository;
import com.erebor.tomkins.pos.repository.local.impl.StockReportLocalRepositoryImpl;
import com.erebor.tomkins.pos.repository.local.impl.StockUpdateLocalRepositoryImpl;
import com.erebor.tomkins.pos.repository.local.impl.TrxTerimaLocalRepositoryImpl;

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

}
