package cn.lxj.swork.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.form.ElecUsualMsgForm;
import cn.lxj.swork.service.ElecUsualMsgService;
/**
 *@Author 李雪建
 *@ClassName ElecUsualMsgAction
 *@PackageName cn.lxj.swork.action
 *@Description 待办事宜 Action
 */
@SuppressWarnings("serial")
public class ElecUsualMsgAction extends BaseAction implements ModelDriven<ElecUsualMsgForm>{

	private ElecUsualMsgService elecUsualMsgService =(ElecUsualMsgService)ServiceProvider.getService(ElecUsualMsgService.SERVICE_NAME);
	private ElecUsualMsgForm elecUsualMsgForm = new ElecUsualMsgForm();
	
	public ElecUsualMsgForm getModel() {
        return elecUsualMsgForm;
    }
	
	
	 /**  
     * @Name: show展示数据
     * @Description:展示数据到前台页面的方法
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: ElecUsualMsg elecUsualsg 对象
     * @Return: 无
     */
	public String home() {
	    List<ElecUsualMsgForm>  list = elecUsualMsgService.findElecUsualMsgData();
        request.setAttribute("usualList", list);
        return "home";
	}
	
	
	
	  /**  
	    * @Name: save数据
	    * @Description:保存数据到后台页面的方法
	    * @Author: 李雪建（作者）
	    * @Version: V1.00 （版本号）
	    * @Parameters: ElecUsualMsg elecUsualsg 对象
	    * @Return: 无
	    */
	
	public String save() {
	    elecUsualMsgService.saveUsualMsg(elecUsualMsgForm);
	    return "save";
	    
	}
}
