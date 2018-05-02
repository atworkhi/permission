package com.hanxx.permission.service;

import com.google.common.collect.Multimap;
import com.hanxx.permission.tree.AclModuleLevelDto;
import com.hanxx.permission.tree.DeptLevelDto;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-16:11
 * 树形结构的service
 */
public interface SysTreeService {

    // 部门
    List<DeptLevelDto> deptTree();

    List<DeptLevelDto> deptToTree(List<DeptLevelDto> list);

    void transformDeptTree(List<DeptLevelDto> dtos,String level,Multimap<String,DeptLevelDto> multimap);

    // 权限管理模块

    List<AclModuleLevelDto> aclModuleTree();

    List<AclModuleLevelDto> aclModuleToTree(List<AclModuleLevelDto> list);

    void transformAclModuleTree(List<AclModuleLevelDto> dtos,String level,Multimap<String,AclModuleLevelDto> multimap);


}
