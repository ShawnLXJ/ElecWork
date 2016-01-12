package cn.lxj.swork.domain;

import java.util.Date;

public class ElecUser {
    private String userId;//主键ID
    private String jctId;//所属单位
    private String userName;//用户名
    private String loginName;//登录名
    private String loginPwd;//登录密码
    private String sexId;//性别
    private Date birthday;//出生日期
    private String address;//联系地址
    private String contactTel;//联系电话
    private String email;//电子邮箱
    private String mobile;//手机号
    private String isDuty;//是否在职
    private Date onDutydate;//入职时间
    private Date offDutydate;//离职时间
    private String remark;//备注
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getJctId() {
        return jctId;
    }
    public void setJctId(String jctId) {
        this.jctId = jctId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getLoginPwd() {
        return loginPwd;
    }
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
    public String getSexId() {
        return sexId;
    }
    public void setSexId(String sexId) {
        this.sexId = sexId;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContactTel() {
        return contactTel;
    }
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getIsDuty() {
        return isDuty;
    }
    public void setIsDuty(String isDuty) {
        this.isDuty = isDuty;
    }
    
    public Date getOnDutydate() {
        return onDutydate;
    }
    public void setOnDutydate(Date onDutydate) {
        this.onDutydate = onDutydate;
    }
    public Date getOffDutydate() {
        return offDutydate;
    }
    public void setOffDutydate(Date offDutydate) {
        this.offDutydate = offDutydate;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
