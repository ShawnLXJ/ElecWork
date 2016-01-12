package cn.lxj.swork.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.lxj.swork.dao.ElecSystemDdlDao;
import cn.lxj.swork.domain.ElecSystemDdl;

@Repository(ElecSystemDdlDao.SERVICE_NAME)
public class ElecSystemDdlDaoImpl extends CommonDaoImpl<ElecSystemDdl>implements ElecSystemDdlDao{

    /**  
     * @Name:findKeyWord
     * @Description:查询关键字，去掉重复值
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 
     * @Return: List<ElecSystemDdlForm>
     */
    public List<Object> findKeyWord() {
        String hql = "select distinct a.keyWord from ElecSystemDdl a";
       @SuppressWarnings("unchecked")
    List<Object> list = this.getHibernateTemplate().find(hql);
        return list;
    }

    /**  
     * @Name:findDdlName
     * @Description:获取数据字典对应的数据项名称。
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: String ddlcode, String keyword
     * @Return: String
     */
    @SuppressWarnings("unchecked")
    public String findDdlName(String ddlcode, String keyword) {
        String hql  = "from ElecSystemDdl where keyWord = '"+ keyword +"'"+" and ddlCode = "+ddlcode;
        List<ElecSystemDdl> list = this.getHibernateTemplate().find(hql);
        String ddlName = "";
        if(list != null && list.size()>0) {
            ElecSystemDdl elecSystemDdl = list.get(0);
            ddlName=  elecSystemDdl.getDdlName();
        }
        return ddlName;
    }
}
