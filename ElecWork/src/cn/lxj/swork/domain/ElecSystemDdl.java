package cn.lxj.swork.domain;

/*持久层对象，对应表elec_systemddl*/
public class ElecSystemDdl {
    private int seqId;//主键，自增长
    private String keyWord;//查询关键字
    private int ddlCode;//数据字典的code
    private String ddlName;//数据字典的value
    public int getSeqId() {
        return seqId;
    }
    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }
    public String getKeyWord() {
        return keyWord;
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    public int getDdlCode() {
        return ddlCode;
    }
    public void setDdlCode(int ddlCode) {
        this.ddlCode = ddlCode;
    }
    public String getDdlName() {
        return ddlName;
    }
    public void setDdlName(String ddlName) {
        this.ddlName = ddlName;
    }
    
    
}
