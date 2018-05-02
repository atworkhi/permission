package com.hanxx.permission.service.impl;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.hanxx.permission.dao.SysAclModuleMapper;
import com.hanxx.permission.dao.SysDeptMapper;
import com.hanxx.permission.model.SysAclModule;
import com.hanxx.permission.model.SysDept;
import com.hanxx.permission.service.SysTreeService;
<<<<<<< HEAD
import com.hanxx.permission.tree.AclModuleLevelDto;
=======
import com.hanxx.permission.tree.AclModelLevelDto;
>>>>>>> origin/master
import com.hanxx.permission.tree.DeptLevelDto;
import com.hanxx.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-16:15
 * 树形结构的服务类
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysAclModuleMapper aclModuleMapper;

    @Override
    public List<DeptLevelDto> deptTree() {
        List<SysDept> list = deptMapper.getAll();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : list) {
            DeptLevelDto dto = DeptLevelDto.newTrue(dept);
            dtoList.add(dto);
        }
        return deptToTree(dtoList);
    }

    /**
     * 转换成树形结构
     *
     * @param list
     * @return
     */
    public List<DeptLevelDto> deptToTree(List<DeptLevelDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        // map类型{key：[dept1],[dept2]}
        Multimap<String, DeptLevelDto> multimap = ArrayListMultimap.create();
        List<DeptLevelDto> depts = Lists.newArrayList();

        for (DeptLevelDto dto : list) {
            multimap.put(dto.getLevel(), dto);
            // 如果是顶级目录
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                depts.add(dto);
            }
        }
        // 按照 seq 从小到大排序
        Collections.sort(depts, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        // 递归
        transformDeptTree(depts, LevelUtil.ROOT, multimap);
        return depts;
    }

    //递归排序：
    public void transformDeptTree(List<DeptLevelDto> dtos, String level, Multimap<String, DeptLevelDto> multimap) {
        for (int i = 0; i < dtos.size(); i++) {
            //遍历每个元素
            DeptLevelDto dto = dtos.get(i);
            //处理当前的层级
            String nextLevel = LevelUtil.calLevel(level, dto.getId());
            // 处理下一层
            List<DeptLevelDto> temp = (List<DeptLevelDto>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(temp)) {
                // 排序
                Collections.sort(temp, deptLevelComparator);
                // 设置下一层部门
                dto.setDeptList(temp);
                // 进入下层处理
                transformDeptTree(temp, nextLevel, multimap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptLevelComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

<<<<<<< HEAD
    @Override
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = aclModuleMapper.getAll();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule aclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return aclModuleToTree(dtoList);
    }

    @Override
    public List<AclModuleLevelDto> aclModuleToTree(List<AclModuleLevelDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        // level -> [aclmodule1, aclmodule2, ...] Map<String, List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : list) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        Collections.sort(rootList, aclModuleSeqComparator);
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    @Override
    public void transformAclModuleTree(List<AclModuleLevelDto> dtos, String level, Multimap<String, AclModuleLevelDto> multimap) {
        for (int i = 0; i < dtos.size(); i++){
            AclModuleLevelDto dto = dtos.get(i);
            String nextLevel = LevelUtil.calLevel(level,dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)){
                Collections.sort(tempList,aclModuleSeqComparator);
                dto.setAclModuleLevelDtoList(tempList);
                transformAclModuleTree(tempList,nextLevel,multimap);
=======

    // 权限管理模块
    @Override
    public List<AclModelLevelDto> aclModelTree() {
        List<SysAclModule> list = aclModuleMapper.getAll();

        List<AclModelLevelDto>  aclModelLevelDtoList= Lists.newArrayList();
        for (SysAclModule aclModule : list) {
            AclModelLevelDto dto = AclModelLevelDto.newTree(aclModule);
            aclModelLevelDtoList.add(dto);
        }
        return aclModelToTree(aclModelLevelDtoList);
    }

    @Override
    public List<AclModelLevelDto> aclModelToTree(List<AclModelLevelDto> list) {
        if (CollectionUtils.isEmpty(list)){
            return Lists.newArrayList();
        }
        // map类型{key：[dept1],[dept2]}
        Multimap<String,AclModelLevelDto> multimap = ArrayListMultimap.create();
        List<AclModelLevelDto> aclModelLevelDtoList = Lists.newArrayList();

        for (AclModelLevelDto dto : list){
            multimap.put(dto.getLevel(),dto);
            // 如果是顶级目录
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                aclModelLevelDtoList.add(dto);
            }
        }
        // 按照 seq 从小到大排序
        Collections.sort(aclModelLevelDtoList, new Comparator<AclModelLevelDto>() {
            @Override
            public int compare(AclModelLevelDto o1, AclModelLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        // 递归
        transformAclModelTree(aclModelLevelDtoList, LevelUtil.ROOT, multimap);
        return aclModelLevelDtoList;
    }

    @Override
    public void transformAclModelTree(List<AclModelLevelDto> dtos, String level, Multimap<String, AclModelLevelDto> multimap) {

        for (int i = 0; i<dtos.size();i++){
            //遍历每个元素
            AclModelLevelDto dto =dtos.get(i);
            //处理当前的层级
            String nextLevel = LevelUtil.calLevel(level,dto.getId());
            // 处理下一层
            List<AclModelLevelDto> temp = (List<AclModelLevelDto>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(temp)){
                // 排序
                Collections .sort(temp, aclModelLevelDtoComparator);
                // 设置下一层部门
                dto.setAclModelLevelDtoList(temp);
                // 进入下层处理
                transformAclModelTree(temp,nextLevel,multimap);
>>>>>>> origin/master
            }
        }
    }

<<<<<<< HEAD
    public Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
=======
    public Comparator<AclModelLevelDto> aclModelLevelDtoComparator = new Comparator<AclModelLevelDto>() {
        @Override
        public int compare(AclModelLevelDto o1, AclModelLevelDto o2) {
>>>>>>> origin/master
            return o1.getSeq() - o2.getSeq();
        }
    };
}
