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
     * 获取全部
     * @return
     */
    List<SysAclModule> getAll();

    /**
     * 查询同一层下是否同名
     * @param parentId
     * @param name
     * @param id
     * @return
     */
    int countByNameInParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    /**
     * 批量更新
     * @param aclModules
     */
    void batchUpdate(@Param("aclModules") List<SysAclModule> aclModules);

    /**
     * 获取权限管理模块 通过 层级
     * @param level
     * @return
     */
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);
}