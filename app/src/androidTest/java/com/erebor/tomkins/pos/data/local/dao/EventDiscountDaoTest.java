//package com.erebor.tomkins.pos.data.local.dao;
//
//import com.erebor.tomkins.pos.base.BaseDaoTest;
//import com.erebor.tomkins.pos.data.local.model.EventHargaDBModel;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import static org.junit.Assert.*;
//
//public class EventDiscountDaoTest extends BaseDaoTest<EventDiscountDao> {
//
//
//    @Override
//    protected EventDiscountDao getDao() {
//        return database.eventDiscountDao();
//    }
//
//    @Override
//    public void insertAndGetDataTest() {
//        EventHargaDao eventHargaDao = database.eventHargaDao();
//        EventHargaDetDao eventHargaDetDao = database.eventHargaDetDao();
//
//        Calendar calendar = Calendar.getInstance();
//        Date dateStart = calendar.getTime();
//        Date dateEnd = calendar.getTime();
//        Date lastUpdate = calendar.getTime();
//        eventHargaDao.insertReplaceSync(new EventHargaDBModel("EVENT1", dateStart, dateEnd, lastUpdate));
//
//        eventHargaDetDao.insertReplaceSync()
//    }
//}