package cn.lxj.swork.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.lxj.swork.domain.Elec;
import cn.lxj.swork.service.ElecService;
import cn.lxj.swork.dao.ElecDao;
import cn.lxj.swork.form.ElecForm;

@Transactional(readOnly=true)
@Service(ElecService.SERVICE_NAME)
public class ElecServiceImpl implements ElecService {
    
    @Resource(name=ElecDao.SERVICE_NAME)
    private ElecDao elecDao;
    
    /**  
    * @Name: saveElec
    * @Description: 保存Elec的方法
    * @Author: 李雪建（作者）
    * @Version: V1.00 （版本号）
    * @Parameters: Elec elec 对象
    * @Return: 无
    */
    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
    public void save(Elec elec) {
        elecDao.save(elec);
    }

    public List<Elec> findBycondition(ElecForm elecTextForm) {
        /**
         * 组织HQL语句的Where条件
         *      select * from elec_text o where 1=1     放置DAO层
                and o.textName like '%张%'              放置Service层
                and o.textRemark like '%李%'
                order by o.textDate desc , o.textName asc 
         */
        String hqlWhere = "";
        List<String> paramsList = new ArrayList<String>();
        if(elecTextForm!=null && StringUtils.isNotBlank(elecTextForm.getEname())){
            hqlWhere += " and a.ename like ?";
            paramsList.add("%"+elecTextForm.getEname()+"%");
        }
        if(elecTextForm!=null && StringUtils.isNotBlank(elecTextForm.getEremark())){
            hqlWhere += " and a.eremark like ?";
            paramsList.add("%"+elecTextForm.getEremark()+"%");
        }
        Object [] params = paramsList.toArray();
        /**
         * 组织排序语句
         *     order by o.textDate desc , o.textName asc 
         */
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("a.edate", "desc");
        orderby.put("a.ename", "asc");
        //查询列表
        List<Elec> list = elecDao.findBycondition(hqlWhere,params,orderby);
        for(int i=0;list!=null && i<list.size();i++){
            Elec elec = list.get(i);
            System.out.println(elec.getEname() + "　" + elec.getEremark());
        }
        return null;
    }
}
