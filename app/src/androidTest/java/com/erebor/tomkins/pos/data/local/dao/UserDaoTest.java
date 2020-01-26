package com.erebor.tomkins.pos.data.local.dao;

import com.erebor.tomkins.pos.base.BaseDaoTest;
import com.erebor.tomkins.pos.data.local.Triggers;
import com.erebor.tomkins.pos.data.local.model.UserDBModel;

import org.junit.Test;

public class UserDaoTest extends BaseDaoTest<UserDao> {

    private final static UserDBModel RECORD = new UserDBModel(1, "email");

    @Override
    public UserDao getDao() {
        return database.userDAO();
    }

    @Override
    public void insertAndGetDataTest() {
        dao.insertReplace(RECORD).blockingGet();

        dao.getByPrimaryKey(String.valueOf(RECORD.getId()))
                .test()
                .assertValue(userDBModel -> userDBModel != null
                        && userDBModel.getEmail().equals(RECORD.getEmail()));
    }

    @Test
    public void triggerAccessTest() {
        database.getOpenHelper().getWritableDatabase().execSQL(Triggers.accessFromUser);

        dao.insertReplace(RECORD).blockingGet();

        AccessDao accessDao = database.accessDao();
        accessDao.getAllData()
                .test()
                .assertValue(accessDBModels -> {
                    System.out.println("Size: " + accessDBModels.size());
                    System.out.println("userid : " + accessDBModels.get(0).getUserId());
                    return !accessDBModels.isEmpty();
                });
//        accessDao.getByUserId(RECORD.getPrimaryKeyValue())
//                .test()
//                .assertValue(accessDBModel ->
//                        accessDBModel != null
//                        && accessDBModel.getUserId().equals(RECORD.getPrimaryKeyValue()));


    }
}