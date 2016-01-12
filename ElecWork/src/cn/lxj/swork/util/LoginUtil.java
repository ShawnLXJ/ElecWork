package cn.lxj.swork.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.xwork.StringUtils;


public class LoginUtil {

    /**  
     * @Name: checkNumber
     * @Description: 首页登录中添加验证码的功能
     * @Author:李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: HttpServletRequest request对象
     * @Return: boolean true 验证成功
     *                  false 验证失败
     */
    
    public static boolean checkNumber(HttpServletRequest request) {
        HttpSession  session = request.getSession(false);// 意思是如果当前Session没有就为null；
        if(session ==null){
            return false;
        }
        
        String check_number_key = (String)session.getAttribute("CHECK_NUMBER_KEY");
        String check_number = request.getParameter("checkNumber");
        if(StringUtils.isBlank(check_number)){
            return false;
        }
        return check_number_key.equalsIgnoreCase(check_number);
    }
    
    
    /**  
     * @throws UnsupportedEncodingException 
     * @Name: remeberMeByCookies
     * @Description: 首页登录中添加记住我的功能
     * @Author:李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-8-3 （创建日期）
     * @Parameters: HttpServletRequest request,HttpServletResponse response对象
     * @Return: void
     */
    public static void remeberMeByCookies(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //获取表单的姓名和密码值
        String name = request.getParameter("name");
       String password = request.getParameter("password");
       String cookieName = URLEncoder.encode(name,"UTF-8");
        /*创建2个Cookie，分别用来存放登录名和密码
       处理Cookie中存在的中文字符*/
       Cookie name_cookie = new Cookie("name",cookieName);
       Cookie password_cookie = new Cookie("password",password);
       //设置Cookie的有效路径，有效路径定义为项目的根路径
       name_cookie.setPath(request.getContextPath()+"/");
       password_cookie.setPath(request.getContextPath()+"/");
       /*  
        * 从页面中获取记住我的复选框的值，
        *    * 如果有值，设置Cookie的有效时长
        *    * 如果没有值，清空Cookie的有效时长
        * <input type="checkbox" name="remeberMe" id="remeberMe" value="yes">
        */       
       String remeberme = request.getParameter("remeberme");
       if(remeberme!=null && remeberme.equals("yes")){
           name_cookie.setMaxAge(7*24*60*60);
           password_cookie.setMaxAge(7*24*60*60);
       }
       else{
           name_cookie.setMaxAge(0);
           password_cookie.setMaxAge(0);
       }
      // response.addCookie(name_cookie);
       response.addCookie(name_cookie);
       response.addCookie(password_cookie);
      
    }
    
    
}
