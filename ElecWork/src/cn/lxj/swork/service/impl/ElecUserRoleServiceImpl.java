package cn.lxj.swork.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lxj.swork.dao.ElecRolePopedomDao;
import cn.lxj.swork.dao.ElecUserRoleDao;
import cn.lxj.swork.domain.ElecRolePopedom;
import cn.lxj.swork.domain.ElecUserRole;
import cn.lxj.swork.form.ElecUserForm;
import cn.lxj.swork.form.ElecUserRoleForm;
import cn.lxj.swork.service.ElecUserRoleService;
import cn.lxj.swork.util.XmlBean;

@Transactional(readOnly=true)
@Service(ElecUserRoleService.SERVICE_NAME)
public class ElecUserRoleServiceImpl implements ElecUserRoleService {
    
    
    @Resource(name=ElecUserRoleDao.SERVICE_NAME)
    private ElecUserRoleDao elecUserRoleDao;
    
    @Resource(name=ElecRolePopedomDao.SERVICE_NAME)
    private ElecRolePopedomDao elecRolePopedomDao ;

    /**  
     * @Name: readxml
     * @Description:从xml中读取权限信息放到xmlbean中
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return:  List<XmlBean> 
     */
    @SuppressWarnings("unchecked")
    public List<XmlBean> readxml() {
        // 使用dom4j读取配置文件
       ServletContext servletContext =  ServletActionContext.getServletContext();
     /*  
      * 如果是直接写死写上xml文件路径，需要写成
      * D:\\apache-tomcat-7.0.27\\webapps\\ElecWork\\WEB-INF\\classes\\Function.xml
      * 注意这个路径的写法是用的\\不是真实路径的写法
      * 但是如果写成用struts2的方法getRealPath时直接写项目发布的下面/WEB-INF/classes/Function.xml
      * 的路径
      */       
       String filepath = servletContext.getRealPath("/WEB-INF/classes/Function.xml");
        List<XmlBean> xmlList = new ArrayList<XmlBean>();
        File f = new File(filepath);
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(f);
            Element element = document.getRootElement();
            /*
             * 查找Function中对应的元素节点
             * Function:对应配置文件中的Function元素节点
             * FunctionCode：对应配置文件中Function元素节点下的FunctionCode元素节点
             * FunctionName：对应配置文件中Function元素节点下的FunctionName元素节点
             * ParentCode：对应配置文件中Function元素节点下的ParentCode元素节点
             * ParentName：对应配置文件中Function元素节点下的ParentName元素节点
             * */
            for(Iterator<Element> iter = element.elementIterator("Function");iter.hasNext();){
                Element xmlElement = iter.next();
                XmlBean xmlBean = new XmlBean();
                xmlBean.setCode(xmlElement.elementText("FunctionCode"));
                xmlBean.setName(xmlElement.elementText("FunctionName"));
                xmlBean.setParentCode(xmlElement.elementText("ParentCode"));
                xmlBean.setParentName(xmlElement.elementText("ParentName"));
                xmlList.add(xmlBean);
            }
        } catch (DocumentException e) {
          
            e.printStackTrace();
        }
       
        return xmlList;
    }



    /**  
     * @Name: readEditXml
     * @Description:使用角色ID查询该角色下的权限。并与系统进行匹配                     
     * 从function.xml中查询功能权限
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-31（创建日期）
     * @Parameters: String roleID
     * @Return:  List<XmlBean>
     */
    public List<XmlBean> readEditXml(String roleID) {
       ElecRolePopedom elecRolePopedom = elecRolePopedomDao.find(roleID);
       String popedom = "";
       if(elecRolePopedom!=null){
           popedom = elecRolePopedom.getPopedomCode();
       }
       List<XmlBean> list = this.reaXmlByPopedom(popedom);
       return list;
    }



    /**  
     * @Name: readEditXml
     * @Description:使用角色ID查询该角色下的权限。并与系统进行匹配  
     * 判断页面中复选框是否被选中，
     * flag=1，标示该角色拥有该权限
     *  flag=0，标示该角色不具有该权限                   
     * 从function.xml中查询功能权限
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-31（创建日期）
     * @Parameters: 
     * @Return:  List<XmlBean>
     */
    
    private List<XmlBean> reaXmlByPopedom(String popedom) {
        List<XmlBean> list = new ArrayList<XmlBean>();
        List<XmlBean> xmlList = this.readxml();
        for(int i=0;xmlList!=null && i<xmlList.size();i++){
            XmlBean xmlBean = xmlList.get(i);
            if(popedom.contains(xmlBean.getCode())){
                xmlBean.setFlag("1");
            }
            else{
                xmlBean.setFlag("0");
            }
            
            list.add(xmlBean);
        }
        return list;
    }



    /**  
     * @Name: findElecUserListByRoleId
     * @Description:查询用户列表集合，获取系统中的所有用户。并且与页面的角色匹配
     *  flag=1，标示该角色拥有该用户，选中
     *  flag=0，标示该角色不拥有该用户，不选中    
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-31（创建日期）
     * @Parameters: String roleID
     * @Return:  List<ElecUserForm>
     */
    public List<ElecUserForm> findElecUserListByRoleId(String roleID) {
        List<Object []> list =  elecUserRoleDao.findElecUserListByRoleId(roleID);
        List<ElecUserForm> formList = this.elecUserRoleListToVo(list);
        return formList;
    }


    /**  
     * @Name: elecUserRoleListToVo
     * @Description:将查询到的用户类型从Object转换成Vo对象
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-31（创建日期）
     * @Parameters: List<Object[]> list
     * @Return:  List<ElecUserForm>
     */
    private List<ElecUserForm> elecUserRoleListToVo(List<Object[]> list) {
        ElecUserForm elecUserForm = null;
        List<ElecUserForm> formList = new ArrayList<ElecUserForm>();
       for(int i=0;list!=null && i<list.size();i++) {
           Object [] objects= list.get(i);
           elecUserForm = new ElecUserForm();
           elecUserForm.setFlag(objects[0].toString());
           elecUserForm.setUserId(objects[1].toString());
           elecUserForm.setUserName(objects[2].toString());
           elecUserForm.setLoginName(objects[3].toString());
           formList.add(elecUserForm);
       }
        return formList;
    }



    /**  
     * @Name: saveRole
     * @Description:保存角色和权限的关联表，保存用户和角色的关联表              
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-31（创建日期）
     * @Parameters: ElecUserRoleForm elecUserRoleForm 
     *                         VO对象，存放角色ID、权限code数组、用户ID数组
     * @Return:  无
     */
    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
    public void saveRole(ElecUserRoleForm elecUserRoleForm) {
      //保存角色和权限的关联表
        this.saveRolePopedom(elecUserRoleForm);
       // 保存用户和角色的关联表
        this.saveUserRole(elecUserRoleForm);
    }

    /**  
     * @Name:saveUserRole
     * @Description:保存角色和用户的关联表
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-8-1（创建日期）
     * @Parameters: ElecUserRoleForm elecUserRoleForm 
     *                         VO对象，存放角色ID、权限code数组、用户ID数组
     * @Return:  无
     */
    private void saveUserRole(ElecUserRoleForm elecUserRoleForm) {
        // 角色id
        String roleId = elecUserRoleForm.getRoleid();
        //权限code结合
          String [] selectuser = elecUserRoleForm.getSelectuser();
          /**
           * 以roleID作为条件，查询用户和角色的关联表，获取用户和角色关联的集合对象
           */
          String hqlWhere = "and a.roleId = ?";
          Object [] params = {roleId};
          List<ElecUserRole> list = elecUserRoleDao.findBycondition(hqlWhere, params, null);
          /**
           * 以roleID作为条件，删除用户和角色的关联表
           */
          elecUserRoleDao.deletelist(list);
          //新增用户和角色的关联表
          List<ElecUserRole> lists = new ArrayList<ElecUserRole>();
          for(int i=0;selectuser!=null && i<selectuser.length;i++){
              String userID = selectuser[i];
              ElecUserRole elecUserRole = new ElecUserRole();
              elecUserRole.setUserId(userID);
              elecUserRole.setRoleId(roleId);
              lists.add(elecUserRole);
          }
          elecUserRoleDao.saveObjectList(lists);
    }



    /**  
     * @Name: saveRolePopedom
     * @Description:保存角色和权限的关联表保存用户和角色的关联表              
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-31（创建日期）
     * @Parameters: ElecUserRoleForm elecUserRoleForm 
     *                         VO对象，存放角色ID、权限code数组、用户ID数组
     * @Return:  无
     */
    private void saveRolePopedom(ElecUserRoleForm elecUserRoleForm) {
        // 角色Id
        String roleId = elecUserRoleForm.getRoleid();
      //权限code结合
        String [] selectoper = elecUserRoleForm.getSelectoper();
        
        StringBuffer popedom = new StringBuffer("");
        for(int i=0;selectoper!=null && i<selectoper.length;i++){
            popedom.append(selectoper[i]);
        }
      //使用角色ID查询角色和权限的关联表
        ElecRolePopedom elecRolePopedom = elecRolePopedomDao.find(roleId);
      //说明角色和权限关联表中存在该角色的记录，此时执行update的操作
        
        if(elecRolePopedom!=null) {
            elecRolePopedom.setPopedomCode(popedom.toString());
        }
        
        else{
            elecRolePopedom = new ElecRolePopedom();
            elecRolePopedom.setRoleId(roleId);
            elecRolePopedom.setPopedomCode(popedom.toString());
            elecRolePopedomDao.save(elecRolePopedom);
        }
        
    }

}
