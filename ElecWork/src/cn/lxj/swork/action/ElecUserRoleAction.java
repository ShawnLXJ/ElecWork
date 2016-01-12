package cn.lxj.swork.action;

import java.util.List;
import com.opensymphony.xwork2.ModelDriven;
import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.form.ElecSystemDdlForm;
import cn.lxj.swork.form.ElecUserForm;
import cn.lxj.swork.form.ElecUserRoleForm;
import cn.lxj.swork.service.ElecSystemDdlService;
import cn.lxj.swork.service.ElecUserRoleService;
import cn.lxj.swork.util.XmlBean;

@SuppressWarnings("serial")
public class ElecUserRoleAction extends BaseAction implements ModelDriven<ElecUserRoleForm>{

	private ElecUserRoleService elecUserRoleService =(ElecUserRoleService)ServiceProvider.getService(ElecUserRoleService.SERVICE_NAME);
	
	private ElecSystemDdlService elecSystemDdlService = (ElecSystemDdlService)ServiceProvider.getService(ElecSystemDdlService.SERVICE_NAME);
	
	
	private ElecUserRoleForm elecUserRoleForm = new ElecUserRoleForm();
	public ElecUserRoleForm getModel() {
        return elecUserRoleForm;
    }
	
	
	 /**  
     * @Name: home
     * @Description:查询所有角色的类型，从数据字典中获取，
     * 从function.xml中查询功能权限
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 跳转到 roleIndex.jsp
     */
	
    public String home(){
	    List<ElecSystemDdlForm> systemlist = elecSystemDdlService.findElecSystemDdlList("角色类型");
	    request.setAttribute("systemlist",systemlist);
	    List<XmlBean> xmllist  = elecUserRoleService.readxml();
	    request.setAttribute("xmllist",xmllist);
	    //System.out.println(xmllist.get(0).getParentName()+"+++++++++++++++"+xmllist.get(0).getParentCode());
	    return "home";
	    
	}
    
	
	/**  
     * @Name: edit
     * @Description:使用角色ID查询该角色下的权限。并与系统进行匹配
     *                          
     * 从function.xml中查询功能权限
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return:  String edit 跳转到 roleEdit.jsp
     */
    public String  edit(){
        String roleID = elecUserRoleForm.getRole();
        List<XmlBean> xmllist = elecUserRoleService.readEditXml(roleID);
        request.setAttribute("xmllist", xmllist);
        //查询用户集合
        List<ElecUserForm> elecUserForm = elecUserRoleService.findElecUserListByRoleId(roleID);
        request.setAttribute("userList", elecUserForm);
        return "edit";
    }
    
    
    /**  
     * @Name: save
     * @Description:保存前台给用赋予的角色的权限值和用户，保存两次，
     * 一个是保存到elec_user_role即保存到用户角色的关联表中
     * 另一个是保存到elec_role_popedom，即保存到角色和权限的关联表中
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return:跳转到 roleIndex.jsp
     */
    
    
    public String save() {
        elecUserRoleService.saveRole(elecUserRoleForm);
        return "save";
    }
    
}


    