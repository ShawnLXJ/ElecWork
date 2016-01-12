package cn.lxj.swork.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

    private static final long serialVersionUID = 1L;

    protected HttpServletRequest request = null;
    protected  HttpServletResponse response = null;
    
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

 
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
        
    }
    
    
    
}
