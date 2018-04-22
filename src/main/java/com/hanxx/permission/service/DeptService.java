package com.hanxx.permission.service;

import com.hanxx.permission.model.SysDept;
import com.hanxx.permission.param.DeptParam;

/**
 * @Author hanxx
 * @Date 2018/4/20-10:29
 * @DESC: 部门
 */
public interface DeptService {

    /**
     *  验证添加或修改部门的名称是否存在
     * @param parentId      父级部门ID
     * @param paramName  部门名称
     * @param paramId       部门ID
     * @return
     */
    boolean check(Integer parentId,String paramName,Integer paramId);

    /**
     *  获取当前部门层级
     * @param deptId
     * @return
     */
    String getLevel(Integer deptId);

    /**
     * 保存新添加的部门
     * @param param
     */
    void save(DeptParam param);

    /**
     * 更新部门
     * @param param
     */
    void update(DeptParam param);

    /**
     * 更新之前与之后的数据判断是否更新成功
     * @param before
     * @param after
     */
    void updateWithChild(SysDept before, SysDept after);
}
