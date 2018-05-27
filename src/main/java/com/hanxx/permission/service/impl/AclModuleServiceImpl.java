package com.hanxx.permission.service.impl;

import com.google.common.base.Preconditions;
import com.hanxx.permission.common.RequestHolder;
import com.hanxx.permission.dao.SysAclModuleMapper;
import com.hanxx.permission.exception.ParamValidateException;
import com.hanxx.permission.model.SysAclModule;
import com.hanxx.permission.param.AclModuleParam;
import com.hanxx.permission.service.AclModuleService;
import com.hanxx.permission.util.BeanValidation;
import com.hanxx.permission.util.IPUtils;
import com.hanxx.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-10:29
 * 权限管理模块 服务层实现类
 */
@Service
public class AclModuleServiceImpl implements AclModuleService {

    @Resource
    private SysAclModuleMapper aclModuleMapper;

    @Override
    public boolean check(Integer parentId, String name, Integer id) {
        // TODO:
        return aclModuleMapper.countByNameInParentId(parentId, name, id) > 0;
    }

    @Override
    public String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = aclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }


    @Override
    public void save(AclModuleParam param) {
        //验证参数
        BeanValidation.validateException(param);
        //验证部门是否已存在
        if (check(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamValidateException("同一层级下模块名称已存在");
        }
        SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        // 计算部门层级
        aclModule.setLevel(LevelUtil.calLevel(getLevel(param.getParentId()), param.getParentId()));

        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        aclModuleMapper.insertSelective(aclModule);
    }

    /**
     * 更新部门
     *
     * @param param
     */
    @Override
    public void update(AclModuleParam param) {
        BeanValidation.validateException(param);
        //验证部门是否已存在
        if (check(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamValidateException("同一层级下模块名称已存在");
        }
        SysAclModule before = aclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "您要更新的模块不存在！");
        if (check(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamValidateException("同一层级下模块已存在");
        }
        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calLevel(getLevel(param.getParentId()), param.getParentId()));

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);

    }

    @Transactional
    @Override
    public void updateWithChild(SysAclModule before, SysAclModule after) {


        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        // 如果更新在其他部门需要变更部门的树
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysAclModule> aclModules = aclModuleMapper.getChildAclModuleListByLevel(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(aclModules)) {
                for (SysAclModule aclModule : aclModules) {
                    String level = aclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                aclModuleMapper.batchUpdate(aclModules);
            }
        }
        aclModuleMapper.updateByPrimaryKey(after);
    }
}
