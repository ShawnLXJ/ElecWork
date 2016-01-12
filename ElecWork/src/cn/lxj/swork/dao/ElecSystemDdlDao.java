package cn.lxj.swork.dao;

import java.util.List;

import cn.lxj.swork.domain.ElecSystemDdl;

public interface ElecSystemDdlDao extends CommonDao<ElecSystemDdl> {
    public final static String SERVICE_NAME="cn.lxj.swork.dao.impl.ElecSystemDdlDaoImpl";

    List<Object> findKeyWord();

    String findDdlName(String ddlcode, String keyword);

  

   
}
