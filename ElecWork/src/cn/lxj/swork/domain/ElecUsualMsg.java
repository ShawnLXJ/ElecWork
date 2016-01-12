package cn.lxj.swork.domain;

import java.util.Date;

public class ElecUsualMsg {
    private String Uid;//主键
    private String MachRun;//机器运行情况
    private String StaRun;//站点情况
    private Date CreateTime;//创建时间 
    private String CreatePel;//创建人
    public String getUid() {
        return Uid;
    }
    public void setUid(String uid) {
        Uid = uid;
    }
    public String getMachRun() {
        return MachRun;
    }
    public void setMachRun(String machRun) {
        MachRun = machRun;
    }
    public String getStaRun() {
        return StaRun;
    }
    public void setStaRun(String staRun) {
        StaRun = staRun;
    }
    public Date getCreateTime() {
        return CreateTime;
    }
    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }
    public String getCreatePel() {
        return CreatePel;
    }
    public void setCreatePel(String createPel) {
        CreatePel = createPel;
    }
}
