package cn.lxj.swork.dao.impl;

import org.springframework.stereotype.Repository;

import cn.lxj.swork.dao.ElecDao;
import cn.lxj.swork.domain.Elec;

@Repository(ElecDao.SERVICE_NAME)
public class ElecDaoImpl extends CommonDaoImpl<Elec>implements ElecDao{

}
