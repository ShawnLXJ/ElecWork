package cn.lxj.swork.action;

import com.opensymphony.xwork2.ModelDriven;
import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.domain.Elec;
import cn.lxj.swork.form.ElecForm;
import cn.lxj.swork.service.ElecService;
import cn.lxj.swork.util.StringCoverDate;
@SuppressWarnings("serial")
public class ElecAction extends BaseAction implements ModelDriven<ElecForm>{

	//private ElecService elecService = (ElecService)ServiceProvider.getService(ElecService.SERVICE_NAME);
	
	private ElecForm elecForm = new ElecForm();
	
	public ElecForm getModel() {
        return elecForm;
    }
	/**  
	* @Name: save
	* @Description: 保存ElecText对象的方法
	* @Author: 李雪建（作者）
	* @Version: V1.00 （版本号）
	* @Parameters: 无
	* @Return: 跳转到system/textAdd.jsp
	*/
	public String save(){
	
		
	    /*System.out.println(elecForm1+"888"+elecForm1.getEdate());
        System.out.println(elecForm1.getEdate()+elecForm1.getEname()+elecForm1.getEremark()+"666");
        System.out.println("asas");
	    System.out.println(request.getParameter("ename"));
		return "save";*/
    
//	    VO对象转换成PO对象
//        实例化PO对象
      Elec elec = new Elec();
      elec.setEname(elecForm.getEname());//测试名称
      elec.setEdate(StringCoverDate.stringConvertDate(elecForm.getEdate()));//测试日期
      elec.setEremark(elecForm.getEremark());//测试备注
     /* ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
      ElecService elecService = (ElecService) ac.getBean(ElecService.SERVICE_NAME);*/
      ElecService elecService1 = (ElecService) ServiceProvider .getService(ElecService.SERVICE_NAME);
      elecService1.save(elec);
      return "save";
	}

	
}
