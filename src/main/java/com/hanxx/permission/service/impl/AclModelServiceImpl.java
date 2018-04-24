package com.hanxx.permission.service.impl;

import com.hanxx.permission.common.RequestHolder;
import com.hanxx.permission.dao.SysAclModuleMapper;
import com.hanxx.permission.exception.ParamValidateException;
import com.hanxx.permission.model.SysAclModule;
import com.hanxx.permission.param.AclModelParam;
import com.hanxx.permission.service.AclModelService;
import com.hanxx.permission.util.BeanValidation;
import com.hanxx.permission.util.IPUtils;
import com.hanxx.permission.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

;import java.util.Date;

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
        return false;
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

    }

    @Override
    public void updateWithChild(SysAclModule before, SysAclModule after) {

    }
}
