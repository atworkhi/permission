package com.hanxx.permission.dao;

import com.hanxx.permission.beans.PageQuery;
import com.hanxx.permission.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyword(@Param("keyword") String keyword);

    /**
     * 验证邮箱
     * @param mail
     * @param id
     * @return
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    /**
     * 验证手机号码
     * @param telephone
     * @param id
     * @return
     */
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    /**
     * 查询同一部门下的所有员工
     * @param deptId
     * @return
     */
    int countByDeptId(@Param("deptId") Integer deptId);

    /**
     * 根据部门ID实现分页查询
     * @param deptId
     * @param page
     * @return
     */
    List<SysUser> getPageByDeptId(@Param("deptId") Integer deptId, @Param("page") PageQuery page);


}