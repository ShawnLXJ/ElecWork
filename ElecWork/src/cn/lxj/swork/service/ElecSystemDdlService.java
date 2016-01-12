package cn.lxj.swork.service;

import java.util.List;
import cn.lxj.swork.form.ElecSystemDdlForm;

public interface ElecSystemDdlService {
    public final static String SERVICE_NAME = " cn.lxj.swork.service.impl.ElecSystemDdlServiceImpl";

    List<ElecSystemDdlForm> findKeyWord();

    List<ElecSystemDdlForm> findElecSystemDdlList(String keyWord);

    void saveElecSystemDdl(ElecSystemDdlForm elecSystemDdlForm);
    
}
