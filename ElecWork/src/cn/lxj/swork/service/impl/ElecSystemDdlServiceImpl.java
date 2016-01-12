package cn.lxj.swork.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lxj.swork.dao.ElecSystemDdlDao;
import cn.lxj.swork.domain.ElecSystemDdl;
import cn.lxj.swork.form.ElecSystemDdlForm;
import cn.lxj.swork.service.ElecSystemDdlService;

@Transactional(readOnly=true)
@Service(ElecSystemDdlService.SERVICE_NAME)
public class ElecSystemDdlServiceImpl implements ElecSystemDdlService {
    
    
    @Resource(name=ElecSystemDdlDao.SERVICE_NAME)
    private ElecSystemDdlDao elecSystemDdlDao;

    /**  
     * @Name:findKeyWord
     * @Description:查询关键字
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-28（创建日期）
     * @Parameters: 
     * @Return: List<ElecSystemDdlForm>
     */
    public List<ElecSystemDdlForm> findKeyWord() {
       List<Object> list = elecSystemDdlDao.findKeyWord();
       List<ElecSystemDdlForm> formlist = this.elecSystemDdlToVo(list);
        return formlist;
    }

    
    /**  
     * @Name:elecSystemDdTolVo
     * @Description:将从数据库查询到的数据（object对象）转换为vo
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Parameters: 
     * @Return: List<ElecSystemDdlForm> VO
     */
    private List<ElecSystemDdlForm> elecSystemDdlToVo(List<Object> list) {
        ElecSystemDdlForm elecSystemDdlForm = null;
        List<ElecSystemDdlForm> list2 = new ArrayList<ElecSystemDdlForm>();
        for(int i = 0;list!=null && i<list.size();i++)    {
            Object object = list.get(i);
            elecSystemDdlForm  = new ElecSystemDdlForm();
            elecSystemDdlForm.setKeyWord(object.toString());
            list2.add(elecSystemDdlForm);
        }
        return list2;
    }


    /**  
     *  @Name:findElecSystemDdlList
     * @Description:根据选中的数据类型，查询相应的数据项
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-28（创建日期）
     * @Parameters: 
     * @Return: List<ElecSystemDdlForm>
     */
    public List<ElecSystemDdlForm> findElecSystemDdlList(String keyWord) {
      
        String hqlWhere = "  and a.keyWord = ?";
        Object [] params = {keyWord};
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("a.ddlCode", "asc");
        List<ElecSystemDdl> list = elecSystemDdlDao.findBycondition(hqlWhere, params, orderby);
        List<ElecSystemDdlForm> formList = this.elecSystemDdlPoToVo(list);
        return formList;
    }


    
    /**  
     *  @Name:elecSystemDdlPoToVo
     * @Description:讲elecSystemDdl转换为VO对象
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-28（创建日期）
     * @Parameters: list po对象集合
     * @Return: List<ElecSystemDdlForm>
     */
    private List<ElecSystemDdlForm> elecSystemDdlPoToVo(List<ElecSystemDdl> list) {
        ElecSystemDdlForm elecSystemDdlForm = null;
        List<ElecSystemDdlForm> formlist = new ArrayList<ElecSystemDdlForm>();
        for(int i=0;list!=null && i<list.size();i++) {
            ElecSystemDdl elecSystemDdl = list.get(i);
            elecSystemDdlForm = new ElecSystemDdlForm();
            elecSystemDdlForm.setDdlCode(String.valueOf(elecSystemDdl.getDdlCode()));
            elecSystemDdlForm.setDdlName(elecSystemDdl.getDdlName());
            elecSystemDdlForm.setSeqId(String.valueOf(elecSystemDdl.getSeqId()));
            formlist.add(elecSystemDdlForm);
        }
                
        return formlist;
    }


    /**  
     *  @Name:saveElecSystemDdl
     * @Description:保存数九字典
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-28（创建日期）
     * @Parameters: lElecSystemDdlForm elecSystemDdlForm
     * @Return:void
     */
    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
    public void saveElecSystemDdl(ElecSystemDdlForm elecSystemDdlForm) {
      String keyWord = elecSystemDdlForm.getKeywordname();//获取数据字典的关键字
      String typeflag = elecSystemDdlForm.getTypeflag();//获取新增数据类型在原有数据类中的标示
      String [] itemname = elecSystemDdlForm.getItemname();//获取数据项的名称
          if(typeflag!=null && typeflag.equals("new")) {
              this.saveSystemDdl(keyWord,typeflag,itemname);
              
          }
          //否则进行修改和添加
          else{
              List<ElecSystemDdl> list =findElecSystemDdl(keyWord);
              elecSystemDdlDao.deletelist(list);//删除原来的值
              this.saveSystemDdl(keyWord,typeflag,itemname);//保存修改的值
          }
    }

    private List<ElecSystemDdl> findElecSystemDdl(String keyWord) {
        String hqlWhere = "  and a.keyWord = ?";
        Object [] params = {keyWord};
        LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
        orderby.put("a.ddlCode", "asc");
        List<ElecSystemDdl> list = elecSystemDdlDao.findBycondition(hqlWhere, params, orderby);
        return list;
    }


    /**  
     *  @Name:saveSystemDdl
     * @Description:封装保存数据字典的方法
     * @Author: 李雪建（作者）
     * @Version: V1.00 （版本号）
     * @Create Date: 2015-7-28（创建日期）
     * @Parameters:String keyWord, String typeflag, String[] itemname
     * @Return:void
     */
    private void saveSystemDdl(String keyWord, String typeflag,String[] itemname) {
        List<ElecSystemDdl> list = new ArrayList<ElecSystemDdl>();
        
        for(int i = 0;itemname!=null && i<itemname.length;i++) {
            ElecSystemDdl elecSystemDdl = new ElecSystemDdl();
            elecSystemDdl.setDdlName(itemname[i]);
            elecSystemDdl.setDdlCode(new Integer(i+1));
            elecSystemDdl.setKeyWord(keyWord);
            list.add(elecSystemDdl);
            //elecSystemDdlDao.save(elecSystemDdl);
        }
        elecSystemDdlDao.saveObjectList(list);
    } 

    
    


}
