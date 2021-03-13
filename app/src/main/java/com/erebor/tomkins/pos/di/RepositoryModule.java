package com.erebor.tomkins.pos.di;

import com.erebor.tomkins.pos.data.local.dao.StockReportDao;
import com.erebor.tomkins.pos.data.local.dao.StokRealDao;
import com.erebor.tomkins.pos.repository.local.StockReportLocalRepository;
import com.erebor.tomkins.pos.repository.local.StockUpdateLocalRepository;
import com.erebor.tomkins.pos.repository.local.impl.StockReportLocalRepositoryImpl;
import com.erebor.tomkins.pos.repository.local.impl.StockUpdateLocalRepositoryImpl;

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

}
