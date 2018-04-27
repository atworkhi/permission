package com.hanxx.permission.service;

import com.google.common.collect.Multimap;
import com.hanxx.permission.tree.AclModelLevelDto;
import com.hanxx.permission.tree.DeptLevelDto;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-16:11
 * 树形结构的service
 */
public interface SysTreeService {

    /**
     * 部门相关
     * @return
     */

    List<DeptLevelDto> deptTree();

    List<DeptLevelDto> toTree(List<DeptLevelDto> list);

    void transformDeptTree(List<DeptLevelDto> dtos,String level,Multimap<String,DeptLevelDto> multimap);

    /**
     * 权限管理模块相关
     * @return
     */
    List<AclModelLevelDto> aclModelTree();

    List<AclModelLevelDto> aclModelToTree(List<AclModelLevelDto> list);

    void transformAclModelTree(List<AclModelLevelDto> dtos,String level,Multimap<String,AclModelLevelDto> multimap);
}
