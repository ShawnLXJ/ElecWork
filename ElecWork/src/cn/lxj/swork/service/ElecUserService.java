package cn.lxj.swork.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.lxj.swork.domain.ElecUser;
import cn.lxj.swork.form.ElecUserForm;


public interface ElecUserService {
    public final static String SERVICE_NAME = " cn.lxj.swork.service.impl.ElecUserServiceImpl";

    List<ElecUserForm> findElecUserList(ElecUserForm elecUserForm,HttpServletRequest request);

    void saveElecUser(ElecUserForm elecUserForm);

    ElecUserForm findUser(ElecUserForm elecUserForm);

    void deleteElecUser(ElecUserForm elecUserForm);

    String checkLoginName(String loginName);

    ElecUser findUserByLoginName(String name);

    String findElecPopedomByLoginName(String name);

    Hashtable<String, String> findRoleByLoginName(String name);

    @SuppressWarnings("rawtypes")
    ArrayList getExcelFiledNameList();

    @SuppressWarnings("rawtypes")
    ArrayList getExcelFiledDataList(ElecUserForm elecUserForm);

    void saveElecUserWithExcel(ElecUserForm elecUserForm);

    List<ElecUserForm> findUserByChart();
    
}
