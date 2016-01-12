package cn.lxj.swork.form;

import java.io.File;

/**
 * VO值对象，对应页面表单的属性值
 
 *
 */
@SuppressWarnings("serial")
public class ElecUserForm implements java.io.Serializable {
	
    private String userId;//主键ID
    private String jctId;//所属单位
    private String userName;//用户名
    private String loginName;//登录名
    private String loginPwd;//登录密码
    private String sexId;//性别
    private String birthday;//出生日期
    private String address;//联系地址
    private String contactTel;//联系电话
    private String email;//电子邮箱
    private String mobile;//手机号
    private String isDuty;//是否在职
    private String onDutydate;//入职时间
    private String offDutydate;//离职时间
    private String remark;//备注
    
    
   /* 
    * 使用viewflag来判断用户能否使用保存键
    * 值为1则只能查看，为空则可以编辑
    * 
*/ 
    
    private String viewflag; 
    
    /**
     * 使用flag字段
     * 判断角色编辑的页面中，该用户是否被选中
     *  * 如果 flag = 0，表示该角色不拥有此用户，则页面中用户复选框不被选中
     *  * 如果 flag = 1，表示该角色拥有此用户，则页面中的用户复选框被选中
     */
    private String flag; 
    
    /*
     * 判断用户的密码是否经过改动，如果为改动，则赋值为1，不用对密码进行加密，直接进行保存！
        如果为null在进行保存用户的信息进行加密然后保存，
    */
    private String md5flag; 
    
    
  //使用jxl进行报表导入的时候使用
    private File file;
    
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    
    //存放人员的单位
    private String jctname;
    //存放人员的数量
    private String jctcount;
    
    public String getJctname() {
        return jctname;
    }
    public void setJctname(String jctname) {
        this.jctname = jctname;
    }
    public String getJctcount() {
        return jctcount;
    }
    public void setJctcount(String jctcount) {
        this.jctcount = jctcount;
    }


    
    /**
     * 用于判断当前操作人具有的角色是否系统管理员和高级管理员的标识
     *    * 如果当前操作人是系统管理员或者是高级管理员的时候，则点击“用户管理”的时候
     *      跳转到userIndex.jsp，可以查看用户列表信息
     *    * 如果当前操作人不是系统管理员或者是高级管理员的时候，则点击“用户管理”的时候
     *      需要跳转到userEdit.jsp，可以对当前登录人进行编辑并保存
     *          * 如果跳转到userEdit.jsp，点击“保存”的时候，需要重定向到userEdit.jsp
     *            此时设置重定向的标识使用roleflag，
     *               * 当roleflag=1的时候，需要重定向到userEdit.jsp
     *               * 当roleflag==null的时候，需要跳转到userIndex.jsp
     */
    private String roleflag;
    
    public String getRoleflag() {
        return roleflag;
    }
    public void setRoleflag(String roleflag) {
        this.roleflag = roleflag;
    }
    public String getMd5flag() {
        return md5flag;
    }
    public void setMd5flag(String md5flag) {
        this.md5flag = md5flag;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getViewflag() {
        return viewflag;
    }
    public void setViewflag(String viewflag) {
        this.viewflag = viewflag;
    }
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
    
    
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
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
    
    public String getOnDutydate() {
        return onDutydate;
    }
    public void setOnDutydate(String onDutydate) {
        this.onDutydate = onDutydate;
    }
    public String getOffDutydate() {
        return offDutydate;
    }
    public void setOffDutydate(String offDutydate) {
        this.offDutydate = offDutydate;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
   
    
}
