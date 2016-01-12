package cn.lxj.swork.form;


/**
 * VO值对象，对应页面表单的属性值
 
 *
 */
@SuppressWarnings("serial")
public class ElecSystemDdlForm implements java.io.Serializable {
	
    private String seqId;//主键，自增长
    private String keyWord;//查询关键字
    private String ddlCode;//数据字典的code
    private String ddlName;//数据字典的value
    
    private String keywordname;//保存数据字典的关键字
    
    /*保存数据字典的状态，
    new是新建数据字典，add是在原有数据类型的基础上进行修改和编辑
    */
    private String typeflag;
    
    //保存数据字典的数据项的名称
    private String [] itemname;
    
    public String getKeywordname() {
        return keywordname;
    }
    public void setKeywordname(String keywordname) {
        this.keywordname = keywordname;
    }
    public String getTypeflag() {
        return typeflag;
    }
    public void setTypeflag(String typeflag) {
        this.typeflag = typeflag;
    }
    public String[] getItemname() {
        return itemname;
    }
    public void setItemname(String[] itemname) {
        this.itemname = itemname;
    }
    public String getSeqId() {
        return seqId;
    }
    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }
    public String getKeyWord() {
        return keyWord;
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    public String getDdlCode() {
        return ddlCode;
    }
    public void setDdlCode(String ddlCode) {
        this.ddlCode = ddlCode;
    }
    public String getDdlName() {
        return ddlName;
    }
    public void setDdlName(String ddlName) {
        this.ddlName = ddlName;
    }
    
    
}
