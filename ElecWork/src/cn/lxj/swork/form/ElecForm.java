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
public class ElecForm implements java.io.Serializable {
	
	private String eid;
	private String ename;
	private String edate;
	private String eremark;
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
    public String getEdate() {
        return edate;
    }
    public void setEdate(String edate) {
        this.edate = edate;
    }
    public String getEremark() {
        return eremark;
    }
    public void setEremark(String eremark) {
        this.eremark = eremark;
    }
}
