package com.hanxx.permission.dao;


import com.hanxx.permission.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(@Param("id")  Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getAll();

    List<SysDept> getChildDeptListByLevel(@Param("level") String level);

    /**
     * 批量更新level
     */
    void batchUpdateLevel(@Param("depts") List<SysDept> depts);

    int countByNameAndParentId(@Param("parentId") int parentId,@Param("deptName") String deptName,@Param("id") Integer id);
}