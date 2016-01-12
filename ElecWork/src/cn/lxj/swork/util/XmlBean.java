package cn.lxj.swork.util;
/*
 * 存放Function.xml中存放的权限
 * 权限名称 权限code 父级权限code 父级权限名称
 * 
 * */
public class XmlBean {
    private String code;//权限code 
    private String name;//权限名称
    private String parentCode;//父级权限code 
    private String parentName;//父级权限名称
    
  /* 
   *  判断页面中复选框是否被选中，
   * flag=1，标示该角色拥有该权限
   *  flag=0，标示该角色不具有该权限
   *  
   */
    
    private String flag;
    
    
 
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentCode() {
        return parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
