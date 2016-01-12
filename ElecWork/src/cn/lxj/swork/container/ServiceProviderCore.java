package cn.lxj.swork.container;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceProviderCore {
    protected static ApplicationContext ac;
    public static void load(String filename){
        ac = new ClassPathXmlApplicationContext(filename);
    }
}
