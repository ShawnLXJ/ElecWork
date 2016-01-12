package cn.lxj.swork.dao;


import java.util.List;

import cn.lxj.swork.domain.ElecUser;

public interface ElecUserDao extends CommonDao<ElecUser> {
    public final static String SERVICE_NAME="cn.lxj.swork.dao.impl.ElecUserDaoImpl";

    List<Object> findElecPopedomByLoginName(String name);

    List<Object[]> findRoleByLoginName(String name);

    List<Object[]> findUserByChart();

}
