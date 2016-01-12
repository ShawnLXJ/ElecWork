package cn.lxj.swork.dao;


import java.util.List;

import cn.lxj.swork.domain.ElecUserRole;

public interface ElecUserRoleDao extends CommonDao<ElecUserRole> {
    public final static String SERVICE_NAME="cn.lxj.swork.dao.impl.ElecUserRoleDaoImpl";

    List<Object[]> findElecUserListByRoleId(String roleID);

}
