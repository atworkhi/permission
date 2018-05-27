package com.hanxx.permission.service;

import com.hanxx.permission.beans.PageQuery;
import com.hanxx.permission.beans.PageResult;
import com.hanxx.permission.model.SysAcl;
import com.hanxx.permission.param.AclParam;

/**
 * @Author:hangx
 * @Date: 2018/5/27 17:54
 * @DESC:
 */
public interface AclService {

    /**
     * 检查同一权限模块下的权限点名称是否相同
     * @param aclModuleId
     * @param name
     * @param id
     * @return
     */
    boolean check(int aclModuleId, String name, Integer id);

    /**
     * 创建权限点
     * @param param
     */
    void save(AclParam param);

    /**
     * 更新权限点
     * @param param
     */
    void update(AclParam param);

    /**
     * 分页查询权限模块下的权限点
     * @param aclModuleId
     * @param page
     * @return
     */
    PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery page);

    /**
     * 定义权限点的code值
     * @return
     */
    String generateCode();
}
