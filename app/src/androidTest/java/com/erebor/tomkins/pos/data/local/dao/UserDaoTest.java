package com.erebor.tomkins.pos.data.local.dao;

import com.erebor.tomkins.pos.base.BaseDaoTest;
import com.erebor.tomkins.pos.data.local.model.UserModel;

public class UserDaoTest extends BaseDaoTest<UserDao> {

    private final static UserModel RECORD = new UserModel(1, "email");

    @Override
    public UserDao getDao() {
        return database.userDAO();
    }

    @Override
    public void insertAndGetDataTest() {
        dao.insertReplace(RECORD).blockingGet();

        dao.getByPrimaryKey(String.valueOf(RECORD.getId()))
                .test()
                .assertValue(userModel -> userModel != null
                        && userModel.getEmail().equals(RECORD.getEmail()));
    }
}