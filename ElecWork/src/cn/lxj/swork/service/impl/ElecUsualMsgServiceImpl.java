package cn.lxj.swork.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lxj.swork.dao.ElecUsualMsgDao;
import cn.lxj.swork.domain.ElecUsualMsg;
import cn.lxj.swork.form.ElecUsualMsgForm;
import cn.lxj.swork.service.ElecUsualMsgService;

@Transactional(readOnly=true)
@Service(ElecUsualMsgService.SERVICE_NAME)
public class ElecUsualMsgServiceImpl implements ElecUsualMsgService {
    
    
    @Resource(name=ElecUsualMsgDao.SERVICE_NAME)
    private ElecUsualMsgDao elecUsualMsgDao;

    /**  
     * @Name:查询所有待办事宜结果
     * @Description:展示数据到前台页面的方法
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: ElecUsualMsgForm elecUsualsgForm 对象
     * @Return: 无
     */
    public List<ElecUsualMsgForm> findElecUsualMsgData() {
        String hqlWhere = "";
        Object [] params = null;
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("a.createTime","desc");
        List<ElecUsualMsg> list = elecUsualMsgDao.findBycondition(hqlWhere, params, orderby);
        List<ElecUsualMsgForm> elecUsualMsgForms = this.ElecUsualMsgToElecVoList(list);
        return elecUsualMsgForms;
    }

    private List<ElecUsualMsgForm> ElecUsualMsgToElecVoList(List<ElecUsualMsg> list) {
        List<ElecUsualMsgForm> msgForms = new ArrayList<ElecUsualMsgForm>();
        ElecUsualMsgForm elecUsualMsgForm =null;
        for(int i =0;list!=null && i<list.size();i++) {
            elecUsualMsgForm = new ElecUsualMsgForm();
            ElecUsualMsg elecUsualMsg = list.get(i);
            elecUsualMsgForm.setUid(elecUsualMsg.getUid());
           // elecUsualMsgForm.setCreatePel(elecUsualMsg.getCreatePel())
            elecUsualMsgForm.setCreateTime(String.valueOf(elecUsualMsg.getCreateTime()!=null?elecUsualMsg.getCreateTime():""));//时间为空则需转换为空，而不是null
            elecUsualMsgForm.setMachRun(elecUsualMsg.getMachRun());
            elecUsualMsgForm.setStaRun(elecUsualMsg.getStaRun());
            msgForms.add(elecUsualMsgForm);
        }
        return msgForms;
    }



    
    /**  
     * @Name:保存所有待办事宜
     * @Description:保存数据到后台数据库的方法
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-27（创建日期）
     * @Parameters: ElecUsualMsgForm elecUsualsgForm 对象
     * @Return: 无
     */
    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
    public void saveUsualMsg(ElecUsualMsgForm elecUsualMsgForm) {
        ElecUsualMsg  elecUsualMsg = this.elecUsualVotoPo(elecUsualMsgForm);
        elecUsualMsgDao.save(elecUsualMsg);
    }

    
    /**  
     * @Name:把前台的数据对象转换成后台数据库的数据，VO转PO
     * @Description:保存数据到后台数据库的方法
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-27（创建日期）
     * @Parameters: ElecUsualMsgForm elecUsualsgForm 对象
     * @Return: 无
     */
    
   
    private ElecUsualMsg elecUsualVotoPo(ElecUsualMsgForm elecUsualMsgForm) {
        
        ElecUsualMsg elecUsualMsg  = new ElecUsualMsg();
        if(elecUsualMsgForm!=null) {
            elecUsualMsg.setCreateTime(new Date());
            elecUsualMsg.setMachRun(elecUsualMsgForm.getMachRun());
            elecUsualMsg.setStaRun(elecUsualMsgForm.getStaRun());
        }
        return elecUsualMsg;
    }

    /**  
     * @Name:通过当前日期查询待办事宜列表
     * @Description:展示数据到前台页面的方法
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-27（创建日期）
     * @Parameters: 无
     * @Return:  List<ElecUsualMsgForm>
     */
    public List<ElecUsualMsgForm> findElecUsualMsgByCurrentDate() {
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        String currentdate = date.toString();
        List<Object[]> list = elecUsualMsgDao.findElecUsualByCurrentDate(currentdate);
        List<ElecUsualMsgForm> formlist = this.elecUsualMsgObjectToVo(list);
        return formlist;
    }

    
    /**  
     * @Name:elecUsualMsgObjectToVo
     * @Description:将elecUsualMsg Object数组转换为VO对象
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-27（创建日期）
     * @Parameters: 无
     * @Return:  List<ElecUsualMsgForm>
     */
    private List<ElecUsualMsgForm> elecUsualMsgObjectToVo(List<Object[]> list) {
       ElecUsualMsgForm elecUsualMsgForm = null;
       List<ElecUsualMsgForm> formlist = new ArrayList<ElecUsualMsgForm>();
        for(int i = 0;list!=null && i<list.size();i++){
           Object [] object = list.get(i);
           elecUsualMsgForm = new ElecUsualMsgForm();
           elecUsualMsgForm.setMachRun(object[0].toString());
           elecUsualMsgForm.setStaRun(object[1].toString());
           formlist.add(elecUsualMsgForm);
       }
        return formlist;
    }

}
