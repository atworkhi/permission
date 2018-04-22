package com.hanxx.permission.service;

import com.hanxx.permission.beans.PageQuery;
import com.hanxx.permission.beans.PageResult;
import com.hanxx.permission.model.SysUser;
import com.hanxx.permission.param.UserParam;
import org.apache.ibatis.annotations.Param;

/**
 * Author:hangx
 * Date: 2018/4/21 22:27
 * DESC: 用户
 */
public interface UserService {

    /**
     * 验证新建用户手机号是否已经存在
     * @param phone
     * @param userId
     * @return
     */
    boolean checkPhone(String phone, Integer userId);

    /**
     * 验证用户手机号码是否存在
     * @param email
     * @param userId
     * @return
     */
    boolean checkEmail(String email, Integer userId);

    /**
     * 创建用户
     * @param param
     */
    void save(UserParam param);

    /**
     * 更新用户
     * @param param
     */
    void update(UserParam param);

    /**
     * 根据关键字查找用户信息 用于登陆
     * @param keyWord
     * @return
     */
    SysUser findKeyWord(String keyWord);

    PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery);


}
