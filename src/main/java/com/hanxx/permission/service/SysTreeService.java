package com.hanxx.permission.service;

import com.google.common.collect.Multimap;
<<<<<<< HEAD
import com.hanxx.permission.tree.AclModuleLevelDto;
=======
import com.hanxx.permission.tree.AclModelLevelDto;
>>>>>>> origin/master
import com.hanxx.permission.tree.DeptLevelDto;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-16:11
 * 树形结构的service
 */
public interface SysTreeService {

<<<<<<< HEAD
    // 部门
=======
    /**
     * 部门相关
     * @return
     */

>>>>>>> origin/master
    List<DeptLevelDto> deptTree();

    List<DeptLevelDto> deptToTree(List<DeptLevelDto> list);

    void transformDeptTree(List<DeptLevelDto> dtos,String level,Multimap<String,DeptLevelDto> multimap);

<<<<<<< HEAD
    // 权限管理模块

    List<AclModuleLevelDto> aclModuleTree();

    List<AclModuleLevelDto> aclModuleToTree(List<AclModuleLevelDto> list);

    void transformAclModuleTree(List<AclModuleLevelDto> dtos,String level,Multimap<String,AclModuleLevelDto> multimap);


=======
    /**
     * 权限管理模块相关
     * @return
     */
    List<AclModelLevelDto> aclModelTree();

    List<AclModelLevelDto> aclModelToTree(List<AclModelLevelDto> list);

    void transformAclModelTree(List<AclModelLevelDto> dtos,String level,Multimap<String,AclModelLevelDto> multimap);
>>>>>>> origin/master
}
