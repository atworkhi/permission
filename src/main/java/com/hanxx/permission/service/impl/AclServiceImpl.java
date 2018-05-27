package com.hanxx.permission.service.impl;

import com.google.common.base.Preconditions;
import com.hanxx.permission.beans.PageQuery;
import com.hanxx.permission.beans.PageResult;
import com.hanxx.permission.common.RequestHolder;
import com.hanxx.permission.dao.SysAclMapper;
import com.hanxx.permission.exception.ParamValidateException;
import com.hanxx.permission.model.SysAcl;
import com.hanxx.permission.param.AclParam;
import com.hanxx.permission.service.AclService;
import com.hanxx.permission.util.BeanValidation;
import com.hanxx.permission.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author:hangx
 * @Date: 2018/5/27 17:54
 * @DESC:
 */
@Service
public class AclServiceImpl implements AclService {

    @Autowired
    private SysAclMapper aclMapper;

    @Override
    public boolean check(int aclModuleId, String name, Integer id) {
        return aclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    @Override
    public void save(AclParam param) {
        // 验证参数：
        BeanValidation.validateException(param);
        if (check(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamValidateException("此名称已在存在，请更换名称！");
        }
        SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId())
                .url(param.getUrl()).type(param.getType()).status(param.getStatus()).seq(param.getSeq())
                .remark(param.getRemark()).build();
        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        acl.setOperateTime(new Date());
        aclMapper.insertSelective(acl);
    }

    @Override
    public void update(AclParam param) {
        BeanValidation.validateException(param);
        if (check(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamValidateException("此名称已在存在，请更换名称！");
        }
        // 更新之前的数据
        SysAcl before = aclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在！");

        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId())
                .url(param.getUrl()).type(param.getType()).status(param.getStatus()).seq(param.getSeq())
                .remark(param.getRemark()).build();

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        aclMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery page) {
        BeanValidation.validateException(page);
        // 获取总条数
        int count = aclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = aclMapper.getPageByAclModuleId(aclModuleId, page);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

    @Override
    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }
}
