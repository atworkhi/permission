package com.hanxx.permission.dao;

import com.hanxx.permission.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    /**
     * 获取全部权限模块
     * @return
     */
    List<SysAclModule> getAll();

    /**
     * 通过level获取权限管理模块列表
     * @param level
     * @return
     */
    List<SysAclModule> getChildAclModelListByLevel(@Param("level") String level);

    /**
     * 批量更新
     * @param sysAclModules
     */
    void betchUpdateLevel(@Param("sysAclModules") List<SysAclModule> sysAclModules);

    /**
     *  统计同一层级下的权限模块名称是否存在
     * @param parentId
     * @param sysModelName
     * @param id
     * @return
     */
    int countByNameAndParentId(@Param("parentId") int parentId, @Param("sysModelName") String sysModelName,@Param("id") Integer id);
}