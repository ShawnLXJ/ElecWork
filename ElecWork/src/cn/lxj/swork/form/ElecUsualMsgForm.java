package cn.lxj.swork.form;


/**
 * VO值对象，对应页面表单的属性值
 
 *
 */
@SuppressWarnings("serial")
public class ElecUsualMsgForm implements java.io.Serializable {
	
    private String uid;//主键
    private String machRun;//机器运行情况
    private String staRun;//站点情况
    private String createTime;//创建时间 
    private String createPel;//创建人
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getMachRun() {
        return machRun;
    }
    public void setMachRun(String machRun) {
        this.machRun = machRun;
    }
    public String getStaRun() {
        return staRun;
    }
    public void setStaRun(String staRun) {
        this.staRun = staRun;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreatePel() {
        return createPel;
    }
    public void setCreatePel(String createPel) {
        this.createPel = createPel;
    }
   
    
}
