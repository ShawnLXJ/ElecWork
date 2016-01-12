package cn.lxj.swork.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.form.ElecLogForm;
import cn.lxj.swork.service.ElecLogService;

public class ElecLogAction extends BaseAction implements ModelDriven<ElecLogForm> {

    private static final long serialVersionUID = 1L;

    private ElecLogService elecLogService = (ElecLogService)ServiceProvider.getService(ElecLogService.SERVICE_NAME);
    
    private ElecLogForm elecLogForm = new ElecLogForm();
    
    public ElecLogForm getModel() {
        return elecLogForm;
    }
    
    /**  
     * @Name: home
     * @Description: 查询日志列表信息
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: String home 跳转到logIndex.jsp
     */
     public String home(){
         List<ElecLogForm> list = elecLogService.findElecLogListByCondition(elecLogForm);
         request.setAttribute("logList", list);
         return "home";
     }
     /**  
     * @Name: delete
     * @Description: 删除查询得到的日志列表信息
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: String delete 重定向到logIndex.jsp
     */
    public String delete(){
       //第一种方式
//       String [] logids = elecLogForm.getLogid();
//       elecLogDao.deleteObjectByIDs(logids);
         //第二种方式
        /*第三种方式,可以得到前台的所有日志Id，通过日志Id删除
         String logid [] = request.getParameterValues("logId");
         System.out.println(logid.length+"||||||||||d");*/
         elecLogService.deleteElecLogByLogIDs(elecLogForm);
         return "delete";
     }
}
