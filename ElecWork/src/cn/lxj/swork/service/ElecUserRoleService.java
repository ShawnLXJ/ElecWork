package cn.lxj.swork.service;

import java.util.List;

import cn.lxj.swork.form.ElecUserForm;
import cn.lxj.swork.form.ElecUserRoleForm;
import cn.lxj.swork.util.XmlBean;



public interface ElecUserRoleService {
    public final static String SERVICE_NAME = " cn.lxj.swork.service.impl.ElecUserRoleServiceImpl";

    List<XmlBean> readxml();

    List<XmlBean> readEditXml(String roleID);

    List<ElecUserForm> findElecUserListByRoleId(String roleID);

    void saveRole(ElecUserRoleForm elecUserRoleForm);

    
    
}
