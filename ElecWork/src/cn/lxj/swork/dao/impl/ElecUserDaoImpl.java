package cn.lxj.swork.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.lxj.swork.dao.ElecUserDao;
import cn.lxj.swork.domain.ElecUser;

@Repository(ElecUserDao.SERVICE_NAME)
public class ElecUserDaoImpl extends CommonDaoImpl<ElecUser>implements ElecUserDao{

    /**  
     * @Name: findElecPopedomByLoginName
     * @Description: 使用登录名获取当前登录名所具有的权限，查询数据库表
     *                                                elec_user
     *                                                elec_user_role
     *                                                elec_role_popedom
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: String name 登录名
     * @Return: List<Object> 存放该用户具有的权限集合
     */
    @SuppressWarnings("unchecked")
    public List<Object> findElecPopedomByLoginName(final String name) {
        final String sql = "select a.Popedomcode as popedom from elec_role_popedom a "+
                             "left outer join elec_user_role b on a.RoleId  =  b.RoleId  "+
                             "inner join elec_user c on  b.UserId = c.UserId  "+
                              "and c.LoginName = ? "+
                             "where c.IsDuty = '1'";
        List<Object> list = (List<Object>)this.getHibernateTemplate().execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                    @SuppressWarnings("deprecation")
                    Query query = session.createSQLQuery(sql)
                     .addScalar("popedom",Hibernate.STRING);
                    query.setParameter(0, name);  
                return query.list();
            }
        });
        return list; 
    }

    /**  
     * @Name: findElecRoleByLogonName
     * @Description: 使用登录名获取当前登录名所具有的角色，查询数据库表
     *                                                elec_user
     *                                                elec_user_role
     *                                                elec_systemddl
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: String name 登录名
     * @Return: List<Object[]> 存放该用户具有的角色集合
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findRoleByLoginName(final String name) {
        final String sql = "select a.DdlCode as code,a.DdlName as name from elec_user_role b  " +
                            "left outer join elec_systemddl a on b.RoleId = a.DdlCode  " +
                            "and a.KeyWord = '角色类型'  "+
                            "inner join elec_user c on b.UserId = c.UserId  " +
                            "and c.LoginName = ?  "+
                            "where c.IsDuty = '1'";
        List<Object[]> list = (List<Object[]>)this.getHibernateTemplate().execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                    @SuppressWarnings("deprecation")
                    Query query = session.createSQLQuery(sql)
                     .addScalar("code",Hibernate.STRING)
                     .addScalar("name",Hibernate.STRING);
                  query.setParameter(0, name);
                return query.list();
            }
        });
        return list;
    }

    /**  
     * @Name: findUserByChart
     * @Description:使用柱状图按照所属单位统计用户数量
      * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 无
     * @Return: List<Object[]> 结果集对象
     */
     @SuppressWarnings("unchecked")
    public List<Object[]> findUserByChart() {
         final String sql = "SELECT b.ddlName AS jctname,COUNT(*) AS jctcount FROM elec_user a " +
                      "LEFT OUTER JOIN elec_systemddl b ON a.JctId = b.ddlCode " +
                      "AND b.keyWord = '所属单位' " +
                      "GROUP BY a.jctId";
         List<Object[]> list = (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

             @SuppressWarnings("deprecation")
            public Object doInHibernate(Session session)
                     throws HibernateException, SQLException {
                 Query query = session.createSQLQuery(sql)
                               .addScalar("jctname",Hibernate.STRING)
                               .addScalar("jctcount",Hibernate.STRING);  
                 return query.list();
             }
             
         });
         return list;
     }

    
}
