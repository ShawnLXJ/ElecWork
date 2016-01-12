package cn.lxj.swork.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cn.lxj.swork.dao.CommonDao;
import cn.lxj.swork.util.ClassCover;
import cn.lxj.swork.util.PageInfo;

public class CommonDaoImpl<T> extends HibernateDaoSupport implements CommonDao<T> {
    
    @SuppressWarnings("rawtypes")
    private Class t = (Class) ClassCover.getClass(this.getClass());
	/*让sessionFactory注入到这个cn.lxj.swork.dao.impl.CommonDaoImpl类中，但是不用这个，用注解
	<bean id="lll" class="cn.lxj.swork.dao.impl.CommonDaoImpl">
	    <property name="sessionFactory"     ref="sessionFactory"></property>
	</bean>*/
	
	@Resource(name="sessionFactory")
	public final void setSessionFactorylxj(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
        }
	
	
	
	public void save(T t) {
        this.getHibernateTemplate().save(t);
    }
	
	
	
	
	public void update(T t) {
        this.getHibernateTemplate().update(t);
    }
	
    @SuppressWarnings("unchecked")
    public T find(Serializable eid){
	    //泛型转换
	   // ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
       // Class t = (Class) ClassCover.getClass(this.getClass());
        return (T)this.getHibernateTemplate().get(t,eid);//第一个参数是做要查找的类;
    }



    public void deletes(Serializable...  eids) {
        for(int i=0;eids!=null&&i<eids.length;i++){
            Serializable id = eids[i];
            Object object = (Object)this.getHibernateTemplate().get(t, id);
            this.getHibernateTemplate().delete(object);
        }
    }



    public void deletelist(Collection<T> list) {
        this.getHibernateTemplate().deleteAll(list);
    }



    /**  
     * @Name: findCollectionByConditionWithPage
     * @Description: 使用查询条件查询列表的集合（不分页）
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: String hqlWhere hql语句的where条件
     *              Object[] params where条件的查询参数
     *              LinkedHashMap<String, String> orderby 排序条件
     * @Return: List<T> 结果集列表集合
     */
    @SuppressWarnings("unchecked")
    public List<T> findBycondition(String hqlWhere,final Object[] params, LinkedHashMap<String, String> orderby) {
        /**
         * 组织HQL语句的Where条件
         *      select * from elec_text o where 1=1     放置DAO层
                and o.textName like ?              放置Service层
                and o.textRemark like ?
                order by o.textDate desc , o.textName asc 
         */
        String hql = "from "+t.getSimpleName()+" a where 1=1";
       // String hql = "from Elec a where 1=1";
        //组织排序条件
        String hqlOrderBy = this.orderByCondition(orderby);
        hql = hql + hqlWhere + hqlOrderBy;
        final String finalHql = hql;
        List<T> list = (List<T>)this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(finalHql);
                setParams(query,params);
                return query.list();
             
            }
        });
        return list;
    }

    /**  
    * @Name: setParams
    * @Description: 对where条件中的参数设置参数值
    * @Author: 李雪建（作者）
    * @Version: V1.00 （版本号）
    * @Parameters: Object[] params 参数值
    * @Return: 无
    */
    private void setParams(Query query,Object[] params) {
        for(int i=0;params!=null && i<params.length;i++){
            query.setParameter(i, params[i]);
        }
    }

    /**  
    * @Name: orderByCondition
    * @Description: 组织排序条件
    * @Author: 李雪建（作者）
    * @Version: V1.00 （版本号）
    * @Parameters: LinkedHashMap<String, String> orderby 排序条件
    * @Return: String 排序语句的字符串
    */
    private String orderByCondition(LinkedHashMap<String, String> orderby) {
        StringBuffer buffer = new StringBuffer("");
        if(orderby!=null){
            buffer.append(" order by ");
            for(Map.Entry<String, String> map:orderby.entrySet()){
                buffer.append(" " + map.getKey() + " " + map.getValue() + ",");
            }
            buffer.deleteCharAt(buffer.length()-1);
        }
        return buffer.toString();
    }



    /**  
     * @Name: saveObjectList
     * @Description: 使用集合的形式批量保存
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: Collection<T> list
     * @Return: void
     */
    public void saveObjectList(Collection<T> list) {
       this.getHibernateTemplate().saveOrUpdateAll(list);
        
    }
    
    
    /**  
     * @Name: findCollectionByConditionWithPage
     * @Description: 使用查询条件查询列表的集合（分页）
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: String hqlWhere hql语句的where条件
     *              Object[] params where条件的查询参数
     *              LinkedHashMap<String, String> orderby 排序条件
     * @Return: List<T> 结果集列表集合
     */
     @SuppressWarnings("unchecked")
    public List<T> findCollectionByConditionWithPage(String hqlWhere,
             final Object[] params, LinkedHashMap<String, String> orderby,
             final PageInfo pageInfo) {
         String hql = "from " + t.getSimpleName() + " a where 1=1";
         //组织排序条件
         String hqlOrderBy = this.orderByCondition(orderby);
         hql = hql + hqlWhere + hqlOrderBy;
         final String finalHql = hql;
         List<T> list = (List<T>)this.getHibernateTemplate().execute(new HibernateCallback(){
             public Object doInHibernate(Session session)
                     throws HibernateException, SQLException {
                 Query query = session.createQuery(finalHql);
                 setParams(query,params);
                 //添加分页功能
                 pageInfo.setTotalResult(query.list().size());//通过pageInfo对象设置列表中的总记录数
                 query.setFirstResult(pageInfo.getBeginResult());//当前页中的数据从第几条开始查询
                 query.setMaxResults(pageInfo.getPageSize());//当前页显示几条记录
                 return query.list();
             }
         });
         return list;
     }
}
