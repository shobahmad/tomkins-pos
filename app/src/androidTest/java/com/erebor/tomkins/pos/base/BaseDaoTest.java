package com.erebor.tomkins.pos.base;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.erebor.tomkins.pos.data.local.TomkinsDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public abstract class BaseDaoTest<D extends BaseDao> {

    protected D dao;
    protected abstract D getDao();
    @Test
    public abstract void insertAndGetDataTest();

    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();

    protected TomkinsDatabase database;

    @Before
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TomkinsDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = getDao();
    }

    @After
    public void tearDown() {
        database.close();
    }
}
