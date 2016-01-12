package cn.lxj.swork.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ElecRolePopedom implements Serializable {
    private String roleId;//主键ID
    private String popedomCode;//配置web文件中权限编码的code字符串的连接。
    private String remark;//备注
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getPopedomCode() {
        return popedomCode;
    }
    public void setPopedomCode(String popedomCode) {
        this.popedomCode = popedomCode;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}