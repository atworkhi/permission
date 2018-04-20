package com.hanxx.permission.service;

import com.google.common.collect.Multimap;
import com.hanxx.permission.tree.DeptLevelDto;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-16:11
 * 树形结构的service
 */
public interface SysTreeService {

    List<DeptLevelDto> deptTree();

    List<DeptLevelDto> toTree(List<DeptLevelDto> list);

    void transformDeptTree(List<DeptLevelDto> dtos,String level,Multimap<String,DeptLevelDto> multimap);
}
