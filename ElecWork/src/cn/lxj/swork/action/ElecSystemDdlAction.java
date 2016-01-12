package cn.lxj.swork.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.form.ElecSystemDdlForm;
import cn.lxj.swork.service.ElecSystemDdlService;
/**
 *@Author 李雪建
 *@ClassName ElecSystemDdlAction
 *@PackageName cn.lxj.swork.action
 *@Description  数据字典Action
 */
@SuppressWarnings("serial")
public class ElecSystemDdlAction extends BaseAction implements ModelDriven<ElecSystemDdlForm>{

	private ElecSystemDdlService elecSystemDdlService =(ElecSystemDdlService)ServiceProvider.getService(ElecSystemDdlService.SERVICE_NAME);
	private ElecSystemDdlForm elecSystemDdlForm = new ElecSystemDdlForm();
	
	public ElecSystemDdlForm getModel() {
        return elecSystemDdlForm;
    }
	
	
	 /**  
     * @Name: home展示数据
     * @Description:查询数据，而且去掉重复的值
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: ElecSystemDdlM elecSystemDdl 对象
     * @Return: 跳转到dictionaryIndex.jsp
     */
	   public String home() {
	       List<ElecSystemDdlForm> list = elecSystemDdlService.findKeyWord();
	       request.setAttribute("slist", list);
	       return "home";
	   }
	
	   /**  
	     * @Name: edit展示数据
	     * @Description:选中数据后跳转到选定的页面展示
	     * @Author: 李雪建（作者）
	     * @Version: V1.00 （版本号）
	     * @Parameters: 
	     * @Return: String edit跳转到dictionaryEdit.jsp
	     */
	       public String edit() {
	           String keyWord  = elecSystemDdlForm.getKeyWord();
	           List<ElecSystemDdlForm> list= elecSystemDdlService.findElecSystemDdlList(keyWord);
	           request.setAttribute("sList", list);
	           return "edit";
	       }
	       
	       
	       /**  
	         * @Name: save数据字典
	         * @Description:保存数据字典
	         * @Author: 李雪建（作者）
	         * @Version: V1.00 （版本号）
	         * @Parameters: 无
	         * @Return: 重定向到到dictionaryIndex.jsp
	         */
	           public String save() {
	               elecSystemDdlService.saveElecSystemDdl(elecSystemDdlForm);
	               return "save";
	           }
	
}
