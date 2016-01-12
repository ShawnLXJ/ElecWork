package cn.lxj.swork.container;

import org.apache.commons.lang.StringUtils;
@SuppressWarnings("static-access")
public class ServiceProvider {
    private static ServiceProviderCore spc;
    
    static {
        spc = new ServiceProviderCore();
        spc.load("beans.xml");
        
    }
    
    public static Object getService(String serviceName) {
        if(StringUtils.isBlank(serviceName)) {//如果为空是true，不为空是false
            System.out.println("ddd");
            throw new RuntimeException("服务不存在！");
        }
        
        Object object = null;
        if(spc.ac.containsBean(serviceName)){//是否包含这个类
            object = spc.ac.getBean(serviceName);
        }
        
        if(object==null){
            throw new RuntimeException("服务名称！"+serviceName+"服务节点不存在！");
        }
        return object;
    }
}
