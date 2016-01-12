package cn.lxj.swork.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.lxj.swork.dao.ElecUserRoleDao;
import cn.lxj.swork.domain.ElecUserRole;

@Repository(ElecUserRoleDao.SERVICE_NAME)
public class ElecUserRoleDaoImpl extends CommonDaoImpl<ElecUserRole>implements ElecUserRoleDao{

    /**  
     * @Name: findElecUserListByRoleId
     * @Description:查询用户列表集合，获取系统中的所有用户。并且与页面的角色匹配
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: String roleID
     * @Return:  List<ElecUserForm>
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findElecUserListByRoleId(final String roleID) {
       final String sql = "SELECT DISTINCT CASE elec_user_role.RoleID " +
                           "WHEN ? THEN '1' ELSE '0' END AS flag, " +
                            "elec_user.UserID as userID, " +
                             "elec_user.UserName as userName, " +
                              "elec_user.LoginName as loginName " +
                              "FROM elec_user " + 
                              "LEFT OUTER JOIN elec_user_role " + 
                              "ON elec_user.UserID = elec_user_role.UserID " + 
                              "AND elec_user_role.RoleID = ? " +
                              "WHERE elec_user.IsDuty='1'";
       
       List<Object []> list = (List<Object []>)this.getHibernateTemplate().execute(new HibernateCallback() {
        
        
        public Object doInHibernate(Session session) throws HibernateException,
                SQLException {
            @SuppressWarnings("deprecation")
            Query query = session.createSQLQuery(sql)
             .addScalar("flag", Hibernate.STRING)
              .addScalar("userId", Hibernate.STRING)
               .addScalar("UserName", Hibernate.STRING)
                .addScalar("loginName", Hibernate.STRING);
            
            query.setString(0, roleID);
            query.setString(1,roleID);
            return query.list();
        }
    });
       
        return list;
    }

    
}
