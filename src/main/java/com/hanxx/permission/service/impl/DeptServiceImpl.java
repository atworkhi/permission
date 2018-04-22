package com.hanxx.permission.service.impl;

import com.google.common.base.Preconditions;
import com.hanxx.permission.common.RequestHolder;
import com.hanxx.permission.dao.SysDeptMapper;
import com.hanxx.permission.exception.ParamValidateException;
import com.hanxx.permission.model.SysDept;
import com.hanxx.permission.param.DeptParam;
import com.hanxx.permission.service.DeptService;
import com.hanxx.permission.util.BeanValidation;
import com.hanxx.permission.util.IPUtils;
import com.hanxx.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-10:29
 * dept 服务层实现类
 */
@Service
public class DeptServiceImpl implements DeptService{

    //传入dept的 mapper
    @Resource
    private SysDeptMapper deptMapper;

    @Override
    public boolean check(Integer parentId, String deptName, Integer paramId) {
        // TODO:
        return deptMapper.countByNameAndParentId(parentId,deptName,paramId)>0;
    }

    @Override
    public String getLevel(Integer deptId) {
        SysDept dept = deptMapper.selectByPrimaryKey(deptId);
        if (dept == null){
            return null;
        }
        return dept.getLevel(); //返回上一层部门的level
    }

    /**
     * 保存部门类
     * @param param
     */
    @Override
    public void save(DeptParam param) {
        //验证参数
        BeanValidation.validateException(param);
        //验证部门是否已存在
        if (check(param.getParentId(),param.getName(),param.getId())){
            throw new ParamValidateException("同一层级下部门名称已存在");
        }
        SysDept dept =SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        // 计算部门层级
        dept.setLevel(LevelUtil.calLevel(getLevel(param.getParentId()),param.getParentId()));

        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());
        deptMapper.insertSelective(dept);
    }

    /**
     * 更新部门
     * @param param
     */
    @Override
    public void update(DeptParam param) {
        BeanValidation.validateException(param);
        //验证部门是否已存在
        if (check(param.getParentId(),param.getName(),param.getId())){
            throw new ParamValidateException("同一层级下部门名称已存在");
        }
        SysDept before = deptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"您要更新的部门不存在！");
        if (check(param.getParentId(),param.getName(),param.getId())){
            throw new ParamValidateException("同一层级下部门名称已存在");
        }
        SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calLevel(getLevel(param.getParentId()),param.getParentId()));

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before,after);

    }

    @Transactional
    @Override
    public void updateWithChild(SysDept before, SysDept after) {


        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        // 如果更新在其他部门需要变更部门的树
        if(!newLevelPrefix.equals(oldLevelPrefix)){
            List<SysDept> deptList =deptMapper.getChildDeptListByLevel(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(deptList)){
                for (SysDept dept : deptList){
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                deptMapper.batchUpdateLevel(deptList);
            }
        }
        deptMapper.updateByPrimaryKey(after);
    }
}
