package cn.lxj.swork.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ElecUserRole implements Serializable {
    private int seqId;//主键ID
    private String userId;//用户ID
    private String roleId;//角色ID
    private String remark;//备注
    public int getSeqId() {
        return seqId;
    }
    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}