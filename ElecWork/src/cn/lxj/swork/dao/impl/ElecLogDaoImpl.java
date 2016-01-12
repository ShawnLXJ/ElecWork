package cn.lxj.swork.dao.impl;

import org.springframework.stereotype.Repository;

import cn.lxj.swork.dao.ElecLogDao;
import cn.lxj.swork.domain.ElecLog;

@Repository(ElecLogDao.SERVICE_NAME)
public class ElecLogDaoImpl extends CommonDaoImpl<ElecLog>implements ElecLogDao{


}
