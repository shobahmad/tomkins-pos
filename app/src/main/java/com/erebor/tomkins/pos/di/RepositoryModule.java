package com.erebor.tomkins.pos.di;

import com.erebor.tomkins.pos.data.local.dao.StockReportDao;
import com.erebor.tomkins.pos.repository.local.StockReportLocalRepository;
import com.erebor.tomkins.pos.repository.local.impl.StockReportLocalRepositoryImpl;

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

}
