package cn.lxj.swork.dao;

import java.util.List;

import cn.lxj.swork.domain.ElecUsualMsg;

public interface ElecUsualMsgDao extends CommonDao<ElecUsualMsg> {
    public final static String SERVICE_NAME="cn.lxj.swork.dao.impl.ElecUsualMsgDaoImpl";

    List<Object[]> findElecUsualByCurrentDate(String currentdate);

    

   
}
