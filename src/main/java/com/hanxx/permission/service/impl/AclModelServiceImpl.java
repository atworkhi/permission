package com.hanxx.permission.service.impl;

import com.google.common.base.Preconditions;
import com.hanxx.permission.common.RequestHolder;
import com.hanxx.permission.dao.SysAclModuleMapper;
import com.hanxx.permission.exception.ParamValidateException;
import com.hanxx.permission.model.SysAclModule;
import com.hanxx.permission.param.AclModelParam;
import com.hanxx.permission.service.AclModelService;
import com.hanxx.permission.util.BeanValidation;
import com.hanxx.permission.util.IPUtils;
import com.hanxx.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

;import java.util.Date;
import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/24-16:26
 */
@Service
public class AclModelServiceImpl  implements AclModelService {

    @Autowired
    private SysAclModuleMapper aclModuleMapper;

    @Override
    public boolean check(Integer parentId, String paramName, Integer paramId) {
        return aclModuleMapper.countByNameAndParentId(parentId,paramName,paramId)>0;
    }

    @Override
    public String getLevel(Integer id) {
        SysAclModule aclModule = aclModuleMapper.selectByPrimaryKey(id);
        if(aclModule == null){
            return  null;
        }
        return aclModule.getLevel();
    }

    @Override
    public void save(AclModelParam param) {
        // 参数的验证
        BeanValidation.validateException(param);
        // 验证部门是否已经存在
        if (check(param.getParentId(), param.getName(), param.getId())){
            throw new ParamValidateException("同一层级下权限管理模块名称相同");
        }
        SysAclModule aclModule =SysAclModule.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark()).build();
       // 层级
        aclModule.setLevel(LevelUtil.calLevel(getLevel(param.getParentId()),param.getParentId()));

        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
       aclModule.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
       aclModule.setOperateTime(new Date());

       aclModuleMapper.insertSelective(aclModule);
    }

    @Override
    public void update(AclModelParam param) {
        // 参数验证：
        // 参数的验证
        BeanValidation.validateException(param);
        // 验证部门是否已经存在
        if (check(param.getParentId(), param.getName(), param.getId())){
            throw new ParamValidateException("同一层级下权限管理模块名称相同");
        }
        // 获取更新模块
        SysAclModule before =aclModuleMapper.selectByPrimaryKey(param.getId());
        // 验证需要更新的是否为空
        Preconditions.checkNotNull(before,"您需要更新的权限管理模块不存在");
       // 更新后的名称是否存在
        if (check(param.getParentId(),param.getName(),param.getId())){
            throw new ParamValidateException("同一层级下权限管理模块名称已存在");
        }
        // 更新内容
        SysAclModule after =SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark()).build();

        after.setLevel( LevelUtil.calLevel( getLevel( param.getParentId() ),param.getParentId() ) );

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateWithChild(before,after);
    }

    /**
     * 同一目录下的目录也需要更新
     * @param before
     * @param after
     */
    @Transactional
    @Override
    public void updateWithChild(SysAclModule before, SysAclModule after) {

        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();

        // 判断更新后的模块树是否变化
        if (!newLevelPrefix.equals(oldLevelPrefix)){
            List<SysAclModule> aclModuleList = aclModuleMapper.getChildAclModelListByLevel(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(aclModuleList)){
                for (SysAclModule aclModule : aclModuleList){
                    String level  = aclModule.getLevel();
                    //顶级目录
                    if (level.indexOf(oldLevelPrefix) ==0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                aclModuleMapper.betchUpdateLevel(aclModuleList);
            }
        }
        aclModuleMapper.updateByPrimaryKeySelective(after);
    }
}
