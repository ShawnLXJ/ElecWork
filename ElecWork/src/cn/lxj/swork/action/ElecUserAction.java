package cn.lxj.swork.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.form.ElecSystemDdlForm;
import cn.lxj.swork.form.ElecUserForm;
import cn.lxj.swork.service.ElecLogService;
import cn.lxj.swork.service.ElecSystemDdlService;
import cn.lxj.swork.service.ElecUserService;
import cn.lxj.swork.util.ChartUtils;
import cn.lxj.swork.util.ExcelFileGenerator;

/**
 *@Author 李雪建
 *@ClassName ElecUserAction
 *@PackageName cn.lxj.swork.action
 *@Description  <!- 用户管理
 */
@SuppressWarnings("serial")
public class ElecUserAction extends BaseAction implements ModelDriven<ElecUserForm>{

	private ElecUserService elecUserService =(ElecUserService)ServiceProvider.getService(ElecUserService.SERVICE_NAME);
	
	private ElecSystemDdlService elecSystemDdlService = (ElecSystemDdlService)ServiceProvider.getService(ElecSystemDdlService.SERVICE_NAME);
	
	private ElecLogService elecLogService = (ElecLogService)ServiceProvider.getService(ElecLogService.SERVICE_NAME);
	
	private ElecUserForm elecUserForm = new ElecUserForm();
	public ElecUserForm getModel() {
        return elecUserForm;
    }
	
	
	 /**  
     * @Name: home
     * @Description:查询数据并显示到前台页面的方法
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 跳转到 userIndex.jsp
     */
	
	public String home() {
	    List<ElecUserForm> list = elecUserService.findElecUserList(elecUserForm,request);
	    request.setAttribute("userlist", list);
	    //2015-8-4添加分页功能
	  //使用initflag标识，判断当前跳转的userIndex.jsp还是userList.jsp
        String initflag = request.getParameter("initflag");
        if(initflag!=null && initflag.equals("1")){
            return "page";
        }
        //end
	    return "home";
	}
	
	/**  
     * @Name: add
     * @Description:跳转到添加用户的界面
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 跳转到 userAdd.jsp
     */
    public String add() {
        this.initSystemDdl();
        return "add";
    }
	
	
	/**  
     * @Name: save
     * @Description:根据条件判断保存或者更新添加的用户的信息
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 跳转到 userAdd.jsp
     */

    public String save() {
        elecUserService.saveElecUser(elecUserForm);
        //将新增和修改的信息添加到日志表中 begin
        if(elecUserForm.getUserId()!=null && !elecUserForm.getUserId().equals("")){
            //修改保存
            elecLogService.saveElecLog(request, "用户管理：修改用户【"+elecUserForm.getUserName()+"】的信息");
        }
        else{
            //新增保存
            elecLogService.saveElecLog(request, "用户管理：新增用户【"+elecUserForm.getUserName()+"】的信息");
        }
       // elecUserService.saveElecUser(elecUserForm);
        String roleflag = request.getParameter("roleflag");
        if(roleflag!=null && roleflag.equals("1")){
            return edit();
        }
        return "save";
     }
    
    /**  
     * @Name: edit
     * @Description:编辑用户的信息
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 跳转到 userEdit.jsp
     */
    /* 
     * 使用viewflag来判断用户能否使用保存键
     * 值为1则只能查看，为空则可以编辑
     * 
 */ 
    public String edit() {
        elecUserForm = elecUserService.findUser(elecUserForm);
        //使用值栈传递值
        //ActionContext.getContext().getValueStack().push(elecUserForm);
      //使用值栈的形式传递elecUserForm对象,所以不用上面那句话也可以传递值栈
        this.initSystemDdl();
        String viewflag = elecUserForm.getViewflag();
        request.setAttribute("viewflag", viewflag);
        String roleflag = elecUserForm.getRoleflag();
        request.setAttribute("roleflag", roleflag);
        return "edit";
     }
    
    
    /**  
     * @Name: initSystemDdl
     * @Description:初始化新增和编辑用户使用数据字典的项
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 无
     */

    public void initSystemDdl() {
        List<ElecSystemDdlForm> sexlist = elecSystemDdlService.findElecSystemDdlList("性别");
        List<ElecSystemDdlForm> jctidlist = elecSystemDdlService.findElecSystemDdlList("所属单位");
        List<ElecSystemDdlForm> isdutylist = elecSystemDdlService.findElecSystemDdlList("是否在职");
        request.setAttribute("sexlist", sexlist);
        request.setAttribute("jctidlist", jctidlist);
        request.setAttribute("isdutylist", isdutylist);
        
     }

    /**  
     * @Name: delete
     * @Description:删除用户
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 无
     */
    public String delete(){
        elecUserService.deleteElecUser(elecUserForm);
        String name = elecUserForm.getUserName();
        if(elecUserForm.getUserId()!=null && !elecUserForm.getUserId().equals("")){
            //修改保存
            elecLogService.saveElecLog(request, "用户管理：删除用户【"+name+"】！");
        }
        return "delete";
    }
    
    
    /**  
     * @Name: export
     * @Description:将数据导入到excel表中
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: 无
     */
    @SuppressWarnings("rawtypes")
    public String export(){
      //获取导出的表头和数据
        //获取表头,存放到ArrayList filedName对象中(登录名    用户姓名    性别  联系电话    是否在职)
        ArrayList filedName = elecUserService.getExcelFiledNameList(); 
        /**
           再实例化一个ArrayList filedData集合 filedData.add(dataList);
        */
        ArrayList filedData = elecUserService.getExcelFiledDataList(elecUserForm);
        try {
            //获取输出流
            OutputStream out = response.getOutputStream();
            //重置输出流
            response.reset();
            //设置导出Excel报表的导出形式
            response.setContentType("application/vnd.ms-excel");
            //response.setContentType("application/OCTET-STREAM;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=elec_user.xls"); 
            System.out.println("jinlaimeiy有！");
            ExcelFileGenerator generator = new ExcelFileGenerator(filedName,filedData);
            generator.expordExcel(out);
            //设置输出形式
            System.setOut(new PrintStream(out));
            //刷新输出流
            out.flush();
            //关闭输出流
            if(out!=null){
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**  
     * @Name: importpage
     * @Description:跳转到导入excel的页面
    * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: String 跳转到userImport.jsp
     */
     public String importpage(){
         return "importpage";
     }
     /**  
     * @Name: importdata
     * @Description:从excel中读取数据，存放到数据库中
      * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: String 跳转到userImport.jsp
     */
     public String importdata(){
         elecUserService.saveElecUserWithExcel(elecUserForm);
         return "importdata";
     }
     
     
     /**  
      * @Name: chart
      * @Description:使用柱状图按照所属单位统计用户
       * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
      * @Parameters: 无
      * @Return: String 跳转到userReport.jsp
      */
      public String chart(){
          List<ElecUserForm> list = elecUserService.findUserByChart();
          String filename = ChartUtils.getUserBarChart(list);
          request.setAttribute("filename", filename);
          return "chart";
      }
    
    
    
}


    