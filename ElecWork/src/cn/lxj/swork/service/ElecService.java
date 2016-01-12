package cn.lxj.swork.service;

import java.util.List;

import cn.lxj.swork.domain.Elec;
import cn.lxj.swork.form.ElecForm;

public interface ElecService {
    public final static String SERVICE_NAME = " cn.lxj.swork.service.impl.ElecServiceImpl";
    public void save(Elec elec);
    public List<Elec> findBycondition(ElecForm elecForm);
    
}
