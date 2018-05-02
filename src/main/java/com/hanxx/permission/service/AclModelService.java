package com.hanxx.permission.service;

import com.hanxx.permission.model.SysAclModule;
import com.hanxx.permission.param.AclModelParam;

/**
 * @Author hanxx
 * @Date 2018/4/24-16:25
 * 模块管理接口
 */
public interface AclModelService {

    /**
     *  验证统一部门等级下名字不能相同
     * @param parentId      父级目录ID
     * @param paramName  权限管理名称
     * @param paramId       权限管理ID
     * @return
     */
    boolean check(Integer parentId,String paramName,Integer paramId);

    /**
     *  获取当权限层级
     * @param deptId
     * @return
     */
    String getLevel(Integer deptId);

    /**
     * 保存新添加的权限
     * @param param
     */
    void save(AclModelParam param);

    /**
     * 更新权限
     * @param param
     */
    void update(AclModelParam param);

    /**
     * 因为更新时需要层级一块更新
     * @param before
     * @param after
     */
    void updateWithChild(SysAclModule before, SysAclModule after);
}
