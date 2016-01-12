package junit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.lxj.swork.container.ServiceProvider;
import cn.lxj.swork.dao.ElecDao;
import cn.lxj.swork.dao.ElecUserDao;
import cn.lxj.swork.domain.Elec;
import cn.lxj.swork.domain.ElecUser;

public class TestDao {
    @Test
    public void save(){
        ApplicationContext  ac  = new  ClassPathXmlApplicationContext("beans.xml");
        ElecDao eLectDao =(ElecDao)ac.getBean(ElecDao.SERVICE_NAME);
        //ElecUserDao eLectUserDao =(ElecUserDao)ac.getBean(ElecUserDao.SERVICE_NAME);
        //实例化对象，赋值，执行保存。
        Elec elecText = new Elec();
        elecText.setEname("成功0000！");
        elecText.setEdate(new Date());
        elecText.setEremark("测试Hibernate备注成功！");
        System.out.println(elecText.getEname()+"9090");
        eLectDao.save(elecText);
    }
    
    @Test
    //通过ID更新
    public void update(){
        ElecDao eLecDao = (ElecDao)ServiceProvider.getService(ElecDao.SERVICE_NAME);
        //实例化对象，赋值，执行保存。
        Elec elec = new Elec();
        elec.setEid("8aa5acf24ec43312014ec4d21b3a0005");
        elec.setEname("wewe7300！");
        elec.setEdate(new Date());
        elec.setEremark("update7300！");
        eLecDao.update(elec);
    }
    
    @Test
    public void update1(){
        ElecUserDao eLectUserDao = (ElecUserDao)ServiceProvider.getService(ElecUserDao.SERVICE_NAME);
        //实例化对象，赋值，执行保存。
        ElecUser elec = new ElecUser();
        elec.setUserId("8aa5acf24edcc9f5014edcd0d5cf0002");
        elec.setUserName("wewe7300！");
        elec.setOnDutydate(new Date());
        elec.setRemark("update7300！");
        eLectUserDao.update(elec);
    }
    
    
    @Test
    //通过ID查找
    public void find(){
        ElecDao eLecDao = (ElecDao)ServiceProvider.getService(ElecDao.SERVICE_NAME);
        Serializable eid = "8aa5acf24ec81465014ec814699a0001";
        Elec elec = eLecDao.find(eid);
        System.out.println(elec.getEname()+"   "+elec.getEdate());
    }
    
    @Test
    //通过ID删除
    public void deletes(){
        ElecDao eLecDao = (ElecDao)ServiceProvider.getService(ElecDao.SERVICE_NAME);
        Serializable [] eids = {"8aa5acf24ec43312014ec4d2dc0e0007","8aa5acf24ec50fef014ec50ffb940001"};
        eLecDao.deletes(eids);
    }
    
    @Test
    //通过集合对象删除
    public void deletelist() {
        ElecDao eLecDao = (ElecDao)ServiceProvider.getService(ElecDao.SERVICE_NAME);
        List<Elec> list = new ArrayList<Elec>();
        Elec elec = new Elec();
        elec.setEid("8aa5acf24ec454b7014ec454bb0f0002");
        Elec elec1 = new Elec();
        elec1.setEid("8aa5acf24ec43312014ec433187b0001");
        list.add(elec1);
        list.add(elec);
        eLecDao.deletelist(list);
    }
    
}
