package cn.lxj.swork.form;
/**
 * VO值对象，对应页面表单的属性值
 * VO对象与PO对象的关系：
 *     相同点：都是javabean
 *     不同点：PO对象中的属性关联数据库的字段
 *            VO对象中的属性可以随意增加、修改、删除，对应的页面表单属性
 *
 */
@SuppressWarnings("serial")
public class ElecLogForm implements java.io.Serializable {
	
    private String logId;      //主键ID
    private String ipAddress;  //IP地址
    private String opeName;    //操作人
    private String opeTime;    //操作时间
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
    public String getOpeTime() {
        return opeTime;
    }
    public void setOpeTime(String opeTime) {
        this.opeTime = opeTime;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    
}
