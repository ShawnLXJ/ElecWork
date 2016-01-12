package cn.lxj.swork.service;

import java.util.List;

import cn.lxj.swork.form.ElecUsualMsgForm;

public interface ElecUsualMsgService {
    public final static String SERVICE_NAME = " cn.lxj.swork.service.impl.ElecUsualMsgServiceImpl";
    List<ElecUsualMsgForm> findElecUsualMsgData();
    void saveUsualMsg(ElecUsualMsgForm elecUsualMsgForm);
    List<ElecUsualMsgForm> findElecUsualMsgByCurrentDate();
    
}
