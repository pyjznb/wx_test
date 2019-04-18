package com.xmcc.dao.impl;

import com.xmcc.dao.BatchDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BathDaoImpl<T> implements BatchDao<T> {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public void batchInsert(List<T> list) {
        int size = list.size();
        //循环存入缓存区
        for (int i = 0; i < size; i++) {
            manager.persist(list.get(i));
            //每100条写入一次数据库  如果不足100条 就直接将全部数据写入数据库
            if (i % 100 == 0 || i == size - 1){
                manager.flush();//每满100条 ，就刷新一次数据
                manager.clear();//存入一次数据库  就清理一次缓存区的内容
            }
        }
    }
}
