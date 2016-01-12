package cn.lxj.swork.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import cn.lxj.swork.util.PageInfo;

//公用接口。实现公用的增删查改方法。
public interface CommonDao<T> {
	public void save(T t);
	public void update(T t);
	public T find(Serializable eid);
	public void deletes(Serializable... eids);
	public void deletelist(Collection<T> list);
	List<T> findBycondition(String hqlWhere,Object[] params, LinkedHashMap<String, String> orderby);
	List<T> findCollectionByConditionWithPage(String hqlWhere,final Object[] params, LinkedHashMap<String, String> orderby,final PageInfo pageInfo);
	void saveObjectList(Collection<T> list);
}
