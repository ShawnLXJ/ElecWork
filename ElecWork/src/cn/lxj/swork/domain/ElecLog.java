package cn.lxj.swork.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ElecLog implements Serializable {
    private String logId;      //主键ID
    private String ipAddress;  //IP地址
    private String opeName;    //操作人
    private Date opeTime;      //操作时间
    private String details;    //操作明细
   
    public String getLogId() {
        return logId;
    }
    public void setLogId(String logId) {
        this.logId = logId;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getOpeName() {
        return opeName;
    }
    public void setOpeName(String opeName) {
        this.opeName = opeName;
    }
    public Date getOpeTime() {
        return opeTime;
    }
    public void setOpeTime(Date opeTime) {
        this.opeTime = opeTime;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}