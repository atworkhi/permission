package com.hanxx.permission.service.impl;

import com.google.common.base.Preconditions;
import com.hanxx.permission.beans.PageQuery;
import com.hanxx.permission.beans.PageResult;
import com.hanxx.permission.common.RequestHolder;
import com.hanxx.permission.dao.SysUserMapper;
import com.hanxx.permission.exception.ParamValidateException;
import com.hanxx.permission.model.SysUser;
import com.hanxx.permission.param.UserParam;
import com.hanxx.permission.service.UserService;
import com.hanxx.permission.util.BeanValidation;
import com.hanxx.permission.util.IPUtils;
import com.hanxx.permission.util.MD5Utils;
import com.hanxx.permission.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:hangx
 * Date: 2018/4/21 22:28
 * DESC: 用户增删改查的实现
 */
@Service
public class UserServiceImpl implements UserService{

    //导入userMapper
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser findKeyWord(String keyWord) {
        SysUser user = userMapper.findByKeyword(keyWord);
        return user;
    }

    @Override
    public boolean checkPhone(String phone, Integer userId) {
        return userMapper.countByTelephone(phone,userId)>0;
    }

    @Override
    public boolean checkEmail(String email, Integer userId) {
        return userMapper.countByMail(email,userId)>0;
    }

    @Override
    public void save(UserParam param) {
        // 验证参数
        BeanValidation.validateException(param);
        // 验证手机号是否已经存在
        if ( checkPhone(param.getTelephone(),param.getId()) ){
            throw new ParamValidateException("此手机号码已被注册，请更换手机号");
        }
        // 验证邮箱是否已经存在
        if (checkEmail(param.getMail(), param.getId())){
            throw new ParamValidateException("此邮箱已被注册，请更换邮箱");
        }
        String password = PasswordUtil.randomPassword();
        password = "12345678";
        // md5加密
        String encryptedPassword = MD5Utils.encode(password);
        // 定义user对象，用来接受userParam参数
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        user.setPassword(encryptedPassword);
        //设置user的其他参数
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // TODO: 发送邮件验证

        // 插入
        userMapper.insertSelective(user);
    }

    @Override
    public void update(UserParam param) {
        // 验证参数
        BeanValidation.validateException(param);
        // 验证手机号是否已经存在
        if ( checkPhone(param.getTelephone(),param.getId()) ){
            throw new ParamValidateException("此手机号码已被注册，请更换手机号");
        }
        // 验证邮箱是否已经存在
        if (checkEmail(param.getMail(), param.getId())){
            throw new ParamValidateException("此邮箱已被注册，请更换邮箱");
        }
        // 更新之前数据
        SysUser before = userMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的用户不能为空");
        SysUser after =SysUser.builder().id(param.getId()).username(param.getUsername()).mail(param.getMail())
                .telephone(param.getTelephone()).status(param.getStatus()).deptId(param.getDeptId())
                .remark(param.getRemark()).build();
        //设置user的其他参数
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IPUtils.getRemoteIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        // 执行更新
        userMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {
        // 参数校验
        BeanValidation.validateException(pageQuery);
        // 获取部门下的员工数
        int count = userMapper.countByDeptId(deptId);
        if (count>0){
            // 把员工放在list
            List<SysUser> list = userMapper.getPageByDeptId(deptId,pageQuery);
            // 返回员工放在 PageResult.data中
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }else {
            return PageResult.<SysUser>builder().build();
        }
    }
}
