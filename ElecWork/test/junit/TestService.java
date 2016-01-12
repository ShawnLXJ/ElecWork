package junit;

import java.util.Date;
import java.util.List;

import org.junit.Test;

/*import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;*/
import cn.lxj.swork.container.ServiceProvider;
//import cn.lxj.swork.container.ServiceProviderCore;
import cn.lxj.swork.domain.Elec;
import cn.lxj.swork.form.ElecForm;
import cn.lxj.swork.service.ElecService;

public class TestService {
    
    
   /* public void saveElec(){
        ApplicationContext  ac  = new  ClassPathXmlApplicationContext("beans.xml");
        ElecService  elecService =(ElecService)ac.getBean(ElecService.SERVICE_NAME);
      //实例化对象，赋值，执行保存。
        Elec elec = new Elec();
        elec.seteName("service成功！");
        elec.seteDate(new Date());
        elec.seteRemark("测试service备注成功！");
        elecService.save(elec);
    }*/
    
    @Test
    public void saveElec(){
        ElecService elecService1 = (ElecService) ServiceProvider .getService(ElecService.SERVICE_NAME);
        Elec elec = new Elec();
        elec.setEname("张无");
        elec.setEdate(new Date());
        elec.setEremark("张无极！");
        elecService1.save(elec);
    }
    
    @SuppressWarnings("unused")
    @Test
    public void findcollection() {
        ElecService elecService = (ElecService)ServiceProvider.getService(ElecService.SERVICE_NAME);
        ElecForm elecForm = new ElecForm();
        elecForm.setEname("s");
        elecForm.setEremark("测");
        List<Elec> list = elecService.findBycondition(elecForm);
    }
}
