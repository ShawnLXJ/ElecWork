package cn.lxj.swork.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Elec implements Serializable {
    private String eid;
    private String ename;
    private Date edate;
    private String eremark;
    public Date getEdate() {
        return edate;
    }
    public void setEdate(Date edate) {
        this.edate = edate;
    }
    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
    }
    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public String getEremark() {
        return eremark;
    }
    public void setEremark(String eremark) {
        this.eremark = eremark;
    }
	
}