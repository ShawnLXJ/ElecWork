package cn.lxj.swork.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.lxj.swork.form.ElecLogForm;

public interface ElecLogService {
    public final static String SERVICE_NAME = " cn.lxj.swork.service.impl.ElecLogServiceImpl";

    List<ElecLogForm> findElecLogListByCondition(ElecLogForm elecLogForm);

    void saveElecLog(HttpServletRequest request, String string);

    void deleteElecLogByLogIDs(ElecLogForm elecLogForm);
    
}
