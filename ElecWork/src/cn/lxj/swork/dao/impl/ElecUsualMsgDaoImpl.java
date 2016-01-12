package cn.lxj.swork.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.lxj.swork.dao.ElecUsualMsgDao;
import cn.lxj.swork.domain.ElecUsualMsg;

@Repository(ElecUsualMsgDao.SERVICE_NAME)
public class ElecUsualMsgDaoImpl extends CommonDaoImpl<ElecUsualMsg>implements ElecUsualMsgDao{

   
    @SuppressWarnings("unchecked")
    public List<Object[]> findElecUsualByCurrentDate(String currentdate) {
       final String sql = "select a.StaRun,a.MachRun "    +
                           "from elec_usualmsg a"+
                           " where a.CreateTime ='"+currentdate+"'";
       
       List<Object []> list = (List<Object []>)this.getHibernateTemplate().execute(new HibernateCallback() {
        public Object doInHibernate(Session session) throws HibernateException,
                SQLException {
          Query query = session.createSQLQuery(sql);
            return query.list();
        }
    });
        return list;
    }

    

    
}
