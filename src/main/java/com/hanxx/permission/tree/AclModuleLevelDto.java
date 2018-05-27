package com.hanxx.permission.tree;

import com.google.common.collect.Lists;
import com.hanxx.permission.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author:hangx
 * @Date: 2018/5/2 20:27
 * @DESC: 树级结构
 */
@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule {
    // 前段获取的(树型数据)
    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule aclModule){
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule,dto);
        return dto;
    }
}
