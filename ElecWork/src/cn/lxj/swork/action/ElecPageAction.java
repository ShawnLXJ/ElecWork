package cn.lxj.swork.action;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.List;
import com.opensymphony.xwork2.ModelDriven;

import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.domain.ElecUser;
import cn.lxj.swork.form.ElecPageForm;
import cn.lxj.swork.form.ElecUsualMsgForm;
import cn.lxj.swork.service.ElecLogService;
import cn.lxj.swork.service.ElecUserService;
import cn.lxj.swork.service.ElecUsualMsgService;
import cn.lxj.swork.util.LoginUtil;
import cn.lxj.swork.util.MD5keyBean;

/**
 *@Author 李雪建
 *@ClassName ElecPageAction
 *@PackageName cn.lxj.swork.action
 *@Description 首页登录 
 */
public class ElecPageAction extends BaseAction implements ModelDriven<ElecPageForm> { 
    private static final long serialVersionUID = 6331850912536848112L;
    private ElecPageForm elecPageForm = new ElecPageForm();
    
    private ElecUsualMsgService elecUsualMsgService = (ElecUsualMsgService)ServiceProvider.getService(ElecUsualMsgService.SERVICE_NAME);
    
  //调用日志管理的业务层
    private ElecLogService elecLogService = (ElecLogService)ServiceProvider.getService(ElecLogService.SERVICE_NAME);
    
  /*//使用log4j
    Log log = LogFactory.getLog(ElecPageAction.class);*/
    
    private ElecUserService elecUserService = (ElecUserService)ServiceProvider.getService(ElecUserService.SERVICE_NAME);
    public ElecPageForm getModel() {
        return elecPageForm;
    }
    
    
    /**  
     * @throws UnsupportedEncodingException 
     * @Name: home
     * @Description: 从登陆页面获取登陆名和密码，验证是否合法
     *               如果合法：则验证成功，跳转到home.jsp
     *               如果不合法：则验证失败，回退到index.jsp
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: String home 
     *               验证成功，跳转到home.jsp
     *               验证失败，回退到index.jsp
     */
    public String home() throws UnsupportedEncodingException {
        boolean flag = LoginUtil.checkNumber(request);
        if(!flag){
            this.addFieldError("error", "验证码输入为空或有误！");
            return "error";
        }
        
        String name  = elecPageForm.getName();
        String pwd = elecPageForm.getPassword();
        MD5keyBean md5keyBean  = new MD5keyBean();
        String md5pwd = md5keyBean.getkeyBeanofStr(pwd);
        ElecUser elecUser  = elecUserService.findUserByLoginName(name);
        
        if(elecUser==null){
            this.addFieldError("error", "您当前输入的登录名不存在");
            return "error";
        }
        
        if(pwd==null || pwd.equals("") || !elecUser.getLoginPwd().equals(md5pwd)){
            this.addFieldError("error", "您当前输入的密码有误或不存在");
            return "error";
        }
        
        request.getSession().setAttribute("globle_user", elecUser);
        //获取当前登录名所具有的权限
        String popedom = elecUserService.findElecPopedomByLoginName(name);
        
        if(popedom==null || popedom.equals("")){
            this.addFieldError("error", "当前登录名没有分配权限，请与管理员联系");
            return "error";
        }
        
        request.getSession().setAttribute("globle_popedom", popedom);
        
        Hashtable<String, String> ht = elecUserService.findRoleByLoginName(name);
        
        if(ht==null){
            this.addFieldError("error", "当前登录名没有分配角色，请与管理员联系");
            return "error";
        }
        
        request.getSession().setAttribute("globle_role", ht);
       LoginUtil.remeberMeByCookies(request,response);
      /* java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
       String d = date.toString();
       log.info("用户名:【"+elecUser.getUserName()+"】登录系统！时间是:"+d);*/
       elecLogService.saveElecLog(request,"登录模块：当前用户【"+elecUser.getUserName()+"】登录系统");
        return "home";
    }
    
    public String title() {
        return "title";

    }
    public String left() {
        return "left";

    }
    public String change1() {
        return "change1";
    }
    
    public String loading() {
        return "loading";

    }
    
    public String alermJX() {
        return "alermJX";

    }
    
    
    /**  
     * @Name:logout
     * @Description:退出登录
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return:  String 跳转到 index.jsp
     */
    public String logout() {
        //清空session，退出登录
       /*request.getSession().removeAttribute("globle_user");
        * 该方法是移除指定的session，在退出登录时不适合，因为可能有许多的session不一样名字
        * 下面的方法是清空所有的会话session
        *
        *  */
        request.getSession().invalidate();
        return "logout";

}
    
    /**  
     * @Name:alermSB
     * @Description:设备运行情况
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return:  String 跳转到 alermSB.jsp
     */
    public String alermSB() {
        List<ElecUsualMsgForm> list = elecUsualMsgService.findElecUsualMsgByCurrentDate();
        request.setAttribute("pagelist", list);
    return "alermSB";

    }
    public String alermYS() {
        return "alermYS";
    
    }
    
    /**  
     * @Name:alermZD
     * @Description:站点运行情况
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return:  String 跳转到 alermZD.jsp
     */
    public String alermZD() {
        List<ElecUsualMsgForm> list = elecUsualMsgService.findElecUsualMsgByCurrentDate();
       request.setAttribute("pagelist", list);
        return "alermZD";
    }
    
    public String alermXZ() {
        return "alermXZ";
    }
}