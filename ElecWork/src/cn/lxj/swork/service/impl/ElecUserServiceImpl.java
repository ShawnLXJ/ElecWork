package cn.lxj.swork.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lxj.swork.dao.ElecSystemDdlDao;
import cn.lxj.swork.dao.ElecUserDao;
import cn.lxj.swork.domain.ElecUser;
import cn.lxj.swork.form.ElecUserForm;
import cn.lxj.swork.service.ElecUserService;
import cn.lxj.swork.util.GenerateSqlFromExcel;
import cn.lxj.swork.util.MD5keyBean;
import cn.lxj.swork.util.PageInfo;
import cn.lxj.swork.util.StringCoverDate;

@Transactional(readOnly=true)
@Service(ElecUserService.SERVICE_NAME)
public class ElecUserServiceImpl implements ElecUserService {
    
    
    @Resource(name=ElecUserDao.SERVICE_NAME)
    private ElecUserDao elecUserDao;
    
    @Resource(name=ElecSystemDdlDao.SERVICE_NAME)
    private ElecSystemDdlDao elecSystemDdlDao ;

    /**  
     * @Name:findElecUserList
     * @Description:查询用户，如果有参数则按条件查询没有则查询全部
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: ElecUserForm elecUserForm对象
     * @Return: List<ElecUserForm>
     */
    public List<ElecUserForm> findElecUserList(ElecUserForm elecUserForm,HttpServletRequest request) {
        //组织查询条件
        String hqlWhere = "";
        List<String> paramlist = new ArrayList<String>();
        if(elecUserForm!=null && elecUserForm.getUserName()!=null && !elecUserForm.getUserName().equals("")) {
            hqlWhere +="and a.userName like ?";
            paramlist.add("%" + elecUserForm.getUserName() + "%");
        }
        
        Object [] params  = paramlist.toArray();
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("a.onDutydate","desc");
       // List<ElecUser> list =   elecUserDao.findBycondition(hqlWhere, params, orderby);
      //2015-8-3修改，添加分页功能 begin
        PageInfo pageInfo = new PageInfo(request);
        List<ElecUser> list = elecUserDao.findCollectionByConditionWithPage(hqlWhere, params, orderby ,pageInfo);
        request.setAttribute("page", pageInfo.getPageBean());
        //end
        List<ElecUserForm> elecUserForms = this.elecUserPoToVo(list);
        return elecUserForms;
    }

    
    /**  
     * @Name:elecUserPoToVo
     * @Description:将用户的po对象类型转换为vo类型
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-29（创建日期）
     * @Parameters: List<ElecUser> list
     * @Return: List<ElecUserForm>
     */
    private List<ElecUserForm> elecUserPoToVo(List<ElecUser> list) {
      ElecUserForm  elecUserForm =null;
      List<ElecUserForm> elecUserForms = new ArrayList<ElecUserForm>();
        for(int i=0;list!=null && i<list.size();i++) {
            ElecUser elecUser =list.get(i);
            elecUserForm = new ElecUserForm();
            elecUserForm.setUserId(elecUser.getUserId());
            elecUserForm.setUserName(elecUser.getUserName());
            elecUserForm.setLoginName(elecUser.getLoginName());
            elecUserForm.setLoginPwd(elecUser.getLoginPwd());
            elecUserForm.setSexId(elecUser.getSexId()!=null && !elecUser.getSexId().equals("")?elecSystemDdlDao.findDdlName(elecUser.getSexId(),"性别"):"");
            elecUserForm.setContactTel(elecUser.getContactTel());
            elecUserForm.setIsDuty(elecUser.getIsDuty()!=null && !elecUser.getIsDuty().equals("")?elecSystemDdlDao.findDdlName(elecUser.getIsDuty(),"是否在职"):"");
            elecUserForms.add(elecUserForm);
        }
        return elecUserForms;
    }


    /**  
     * @Name:saveElecUser
     * @Description:保存添加的用户信息
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-30（创建日期）
     * @Parameters:ElecUserForm elecUserForm
     * @Return:无
     */
    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
    public void saveElecUser(ElecUserForm elecUserForm) {
        // 首先将vo对象转换为PO对象
        ElecUser elecUser = this.elecUserToPo(elecUserForm);
        if(elecUserForm.getUserId()!=null && !elecUserForm.getUserId().equals("")){
            elecUserDao.update(elecUser);
        }
        else{
            elecUserDao.save(elecUser);
           
        }
    }

    /**  
     * @Name:elecUserToPo
     * @Description:将用户从vo对象转换成Po对象
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-30（创建日期）
     * @Parameters:ElecUserForm elecUserForm
     * @Return:ElecUser 对象
     */
    private ElecUser elecUserToPo(ElecUserForm elecUserForm) {
        ElecUser elecuser = new ElecUser();
        if(elecUserForm!=null) {
            if(elecUserForm.getUserId()!=null && !elecUserForm.getUserId().equals("")){
                elecuser.setUserId(elecUserForm.getUserId());
                if(elecUserForm.getOffDutydate()!=null && !elecUserForm.getOffDutydate().equals("")){
                    elecuser.setOffDutydate(StringCoverDate.stringConvertDate(elecUserForm.getOffDutydate()));
                }
            }
            elecuser.setJctId(elecUserForm.getJctId());
            elecuser.setUserName(elecUserForm.getUserName());
            elecuser.setLoginName(elecUserForm.getLoginName());
          //使用MD5进行加密密码
            String md5flag = elecUserForm.getMd5flag();
            String password = elecUserForm.getLoginPwd();
            String md5Pwd  = "";
            if(password==null && "".equals(password)){
                password="123";
            }
         
            if(md5flag!=null && md5flag.equals("1")) {
                md5Pwd = password;
            }
            else {
                MD5keyBean md5keyBean = new MD5keyBean();
                 md5Pwd =  md5keyBean.getkeyBeanofStr(password);
            }
            elecuser.setLoginPwd(md5Pwd);
            elecuser.setSexId(elecUserForm.getSexId());
            if(elecUserForm.getBirthday()!=null && !elecUserForm.getBirthday().equals("")){
                elecuser.setBirthday(StringCoverDate.stringConvertDate(elecUserForm.getBirthday()));
            }
            elecuser.setAddress(elecUserForm.getAddress());
            elecuser.setContactTel(elecUserForm.getContactTel());
            elecuser.setEmail(elecUserForm.getEmail());
            elecuser.setMobile(elecUserForm.getMobile());
            elecuser.setIsDuty(elecUserForm.getIsDuty());
            if(elecUserForm.getOnDutydate()!=null && !elecUserForm.getOnDutydate().equals("")){
                elecuser.setOnDutydate(StringCoverDate.stringConvertDate(elecUserForm.getOnDutydate()));
            }
            elecuser.setRemark(elecUserForm.getRemark());
        }
        return elecuser;
    }


    /**  
     * @Name:findUser
     * @Description:查找用户的信息，使用ID查询
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-30（创建日期）
     * @Parameters:ElecUserForm elecUserForm 寻访用户ID
     * @Return:ElecUserForm 存放用户详细信息
     */
    public ElecUserForm findUser(ElecUserForm elecUserForm) {
        String userID = elecUserForm.getUserId();
        ElecUser elecUser = elecUserDao.find(userID);
      //把模型驱动传过来的兑现放到这个函数中后相当于把值放到了值栈中，
        //就不用前面的取值栈的方法了   就是把前面模型驱动传过来的对象直接传到方法中作为参数
        //具体如下elecUserToVo(elecUser，elecUserForm);elecUserForm是参数传过来的模型驱动的值
        elecUserForm  = this.elecUserToVo(elecUser,elecUserForm);
        
        return elecUserForm;
    }

    /**  
     * @Name:elecUserToVo
     * @Description:将用户的信息从po对象转换成Vo对象
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-30（创建日期）
     * @Parameters:ElecUser elecUser  存放用户详细信息
     * @Return:ElecUserForm 存放用户详细信息
     */
    private ElecUserForm elecUserToVo(ElecUser elecUser,ElecUserForm elecUserForm) {
       // ElecUserForm elecUserForm  = new ElecUserForm();
        if(elecUser != null) {
            elecUserForm.setUserId(elecUser.getUserId());
            elecUserForm.setJctId(elecUser.getJctId());
            elecUserForm.setUserName(elecUser.getUserName());
            elecUserForm.setLoginName(elecUser.getLoginName());
            elecUserForm.setLoginPwd(elecUser.getLoginPwd());
            elecUserForm.setSexId(elecUser.getSexId());
            elecUserForm.setBirthday(String.valueOf(elecUser.getBirthday()!=null && !elecUser.getBirthday().equals("")?elecUser.getBirthday():""));
            elecUserForm.setAddress(elecUser.getAddress());
            elecUserForm.setContactTel(elecUser.getContactTel());
            elecUserForm.setEmail(elecUser.getEmail());
            elecUserForm.setMobile(elecUser.getMobile());
            elecUserForm.setIsDuty(elecUser.getIsDuty());
            elecUserForm.setOnDutydate(String.valueOf(elecUser.getOnDutydate()!=null && !elecUser.getOnDutydate().equals("")?elecUser.getOnDutydate():""));
            elecUserForm.setOffDutydate(String.valueOf(elecUser.getOffDutydate()!=null && !elecUser.getOffDutydate().equals("")?elecUser.getOffDutydate():""));
            elecUserForm.setRemark(elecUser.getRemark());
        }
        return elecUserForm;
    }


    /**  
     * @Name:deleteElecUser
     * @Description:将用户的信息删除
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-30（创建日期）
     * @Parameters:ElecUserForm elecUserForm  存放用户详细信息
     * @Return:无
     */
    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
    public void deleteElecUser(ElecUserForm elecUserForm) {
        String userId = elecUserForm.getUserId();
        elecUserDao.deletes(userId);
        
    }


    /**  
     * @Name: checkLoginName
     * @Description: 使用登录名作为条件查询数据库，判断当前登录名在数据库中是否存在
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 205-8-1 （创建日期）
     * @Parameters: String logonName 登录名
     * @Return: String  checkflag=1：如果值为1，说明当前登录名在数据库中有重复记录，则不能进行保存
     *                  checkflag=2：如果值为2，说明当前登录名在数据库中没有重复值，可以进行保存
     */
     public String checkLoginName(String loginName) {
         String hqlWhere = " and a.loginName = ?";
         Object [] params = {loginName};
         List<ElecUser> list = elecUserDao.findBycondition(hqlWhere, params, null);
         String checkflag = "";
         if(list!=null && list.size()>0){
             checkflag = "1";
         }
         else{
             checkflag = "2";
         }
         System.out.println(checkflag+"checkLoginName+++++");
         return checkflag;
     }


     /**  
      * @Name: findUserByLoginName
      * @Description: 使用登录名获取用户的详细信息，用于首页登录的校验
      * @Author: 李雪建（作者）
      * @Version: V1.00 （版本号）
      * @Create Date: 2015-8-1（创建日期）
      * @Parameters: String name 登录名
      * @Return: ElecUser 存放用户详细信息
      */
    public ElecUser findUserByLoginName(String name) {
        String hqlWhere  = "and a.loginName = ?";
        Object [] params = {name};
        List<ElecUser> list = elecUserDao.findBycondition(hqlWhere, params, null);
        ElecUser elecUser =null;
        if(list!=null && list.size()>0){
            elecUser = list.get(0);
        }
        return elecUser;
    }


    /**  
     * @Name: findElecPopedomByLoginName
     * @Description: 使用登录名获取当前登录名所具有的权限
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-8-2 （创建日期）
     * @Parameters: String name 登录名
     * @Return: String 用户存放该用户具有的权限
     */
    public String findElecPopedomByLoginName(String name) {
        List<Object> list = elecUserDao.findElecPopedomByLoginName(name);
        StringBuffer buffer = new StringBuffer("");
        for(int i = 0;list!=null && i<list.size();i++){
            Object object = list.get(i);
            buffer.append(object.toString());
        }
        return buffer.toString();
    }


    /**  
     * @Name: findRoleByLoginName
     * @Description: 使用登录名获取当前登录名所具有的角色
     * @Author: 刘洋（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2011-12-29 （创建日期）
     * @Parameters: String name 登录名
     * @Return: Hashtable<String, String> 存放角色的集合
     */
    public Hashtable<String, String> findRoleByLoginName(String name) {
       List<Object []> list = elecUserDao.findRoleByLoginName(name);
       Hashtable<String, String> ht = null;
       if(list!=null && list.size()>0){
           ht = new Hashtable<String, String>();
           for(int i=0;i<list.size();i++){
               Object[] object = list.get(i);
               ht.put(object[0].toString(), object[1].toString());
           }
       }
        return ht;
    }


    /**  
     * @Name: getExcelFiledNameList
     * @Description: 获取excel的标题数据（登录名,用户姓名,性别    联系电话    是否在职）
     *               放到ArrayList集合中
   * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date:2015-8-4 （创建日期）
     * @Parameters: 无
     * @Return: ArrayList(Excel标题集数据)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList getExcelFiledNameList() {
        String [] titles = {"登录名","用户姓名","性别","联系电话","是否在职"};
        ArrayList filedName = new ArrayList();
        for(int i=0;i<titles.length;i++){
            String title = titles[i];
            filedName.add(title);
        }
        return filedName;
        
    }


    /**  
     * @Name: getExcelFiledDataList
     * @Description: 获取excel的数据内容
     *      获取数据,(zhugeliang 诸葛亮 男   88886666    是
                     liubei      刘备      男   12345678    是
                     )
            将zhugeliang  诸葛亮 男   88886666    是值存放到ArrayList dataList集合中
            再实例化一个ArrayList filedData集合 filedData.add(dataList);
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date:2015-8-4 （创建日期）
     * @Parameters: 无
     * @Return: ArrayList(Excel标题集数据)
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList getExcelFiledDataList(ElecUserForm elecUserForm) {
      //组织查询条件
        String hqlWhere = "";
        List<String> paramsList = new ArrayList<String>();
        if(elecUserForm!=null && elecUserForm.getUserName()!=null && !elecUserForm.getUserName().equals("")){
            hqlWhere += " and a.userName like ?";
            paramsList.add("%" + elecUserForm.getUserName() + "%");
        }
        Object [] params = paramsList.toArray();
        //组织排序
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("a.onDutydate", "desc");
        List<ElecUser> list = elecUserDao.findBycondition(hqlWhere, params, orderby);
        List<ElecUserForm> formList = this.elecUserPoToVo(list);
        //构造报表导出数据
        ArrayList filedData = new ArrayList();
        for(int i=0;formList!=null && i<formList.size();i++){
            ArrayList dataList = new ArrayList();
            ElecUserForm userForm = formList.get(i);
            dataList.add(userForm.getLoginName());
            dataList.add(userForm.getUserName());
            dataList.add(userForm.getSexId());
            dataList.add(userForm.getContactTel());
            dataList.add(userForm.getIsDuty());
            filedData.add(dataList);
        }
        
        return filedData;
    }


    /**  
     * @Name: saveElecUserWithExcel
     * @Description: 从Excel中读取用户信息数据，保存到数据库的表Elec_User中
     * @Author:李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-8-4（创建日期）
     * @Parameters: ElecUserForm elecUserForm 存放Excel文件的流对象
     * @Return: 无
     */
     @SuppressWarnings({ "unchecked", "static-access" })
    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
     public void saveElecUserWithExcel(ElecUserForm elecUserForm) {
         try {
             File file = elecUserForm.getFile();
             GenerateSqlFromExcel generater = new GenerateSqlFromExcel();
             ArrayList<String[]> arrayList = generater.generateStationBugSql(file);
             MD5keyBean md5 = new MD5keyBean();
             for(int i=0;arrayList!=null && i<arrayList.size();i++){
                 String[] data = arrayList.get(i);
                 //实例化PO对象，用PO对象进行保存
                 ElecUser elecUser = new ElecUser();
                 //登录名   密码  用户姓名    性别  所属单位    联系地址    是否在职
                 elecUser.setLoginName(data[0].toString());
                 elecUser.setLoginPwd(md5.getkeyBeanofStr(data[1].toString()));
                 elecUser.setUserName(data[2].toString());
                 elecUser.setSexId(data[3].toString());
                 elecUser.setJctId(data[4].toString());
                 elecUser.setContactTel(data[5].toString());
                 elecUser.setIsDuty(data[6].toString());
               //  elecUser.setBirthday(StringCoverDate.stringConvertDate(data[7].toString()));
                 elecUserDao.save(elecUser);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         
     }


     /**  
      * @Name: findUserByChart
      * @Description:使用柱状图按照所属单位统计用户数量
     * @Author:李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-8-5（创建日期）
      * @Parameters: 无
      * @Return: List<ElecUserForm> 结果集对象
      */
      public List<ElecUserForm> findUserByChart() {
          List<Object[]> list = elecUserDao.findUserByChart();
          List<ElecUserForm> formList = this.userChartPOListToVOList(list);
          return formList;
      }
      /**  
      * @Name: userChartPOListToVOList
      * @Description:使用柱状图按照所属单位统计用户数量，将PO对象转换成VO对象
      * @Author:李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-8-5（创建日期）
      * @Parameters: 无
      * @Return: List<ElecUserForm> 结果集对象
      */
      private List<ElecUserForm> userChartPOListToVOList(List<Object[]> list) {
          List<ElecUserForm> fomrList = new ArrayList<ElecUserForm>();
          ElecUserForm elecUserForm = null;
          for(int i=0;list!=null && i<list.size();i++){
              Object [] object = list.get(i);
              elecUserForm = new ElecUserForm();
              elecUserForm.setJctname(object[0].toString());
              elecUserForm.setJctcount(object[1].toString());
              fomrList.add(elecUserForm);
          }
          return fomrList;
      }
    
}
