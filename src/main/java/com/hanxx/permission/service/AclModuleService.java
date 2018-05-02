package com.hanxx.permission.service;

import com.hanxx.permission.model.SysAclModule;
import com.hanxx.permission.param.AclModuleParam;


/**
 * @Author:hangx
 * @Date: 2018/5/2 21:10
 * @DESC: 模块管理服务类
 */
public interface AclModuleService {

    /**
     *  验证添加或修改模块的名称是否存在
     * @param parentId      父级部门ID
     * @param paramName  部门名称
     * @param paramId       部门ID
     * @return
     */
    boolean check(Integer parentId,String paramName,Integer paramId);

    /**
     *  获取当前模块层级
     * @param deptId
     * @return
     */
    String getLevel(Integer deptId);

    /**
     * 保存新添加的模块
     * @param param
     */
    void save(AclModuleParam param);

    /**
     * 更新模块
     * @param param
     */
    void update(AclModuleParam param);

    /**
     * 更新之前与之后的数据判断是否更新成功
     * @param before
     * @param after
     */
    void updateWithChild(SysAclModule before, SysAclModule after);
}
