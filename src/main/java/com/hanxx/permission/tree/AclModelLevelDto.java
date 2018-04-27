package com.hanxx.permission.tree;

import com.google.common.collect.Lists;
import com.hanxx.permission.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/27-16:46
 */
@Getter
@Setter
@ToString
public class AclModelLevelDto extends SysAclModule{

    private List<AclModelLevelDto> aclModelLevelDtoList = Lists.newArrayList();

    public static AclModelLevelDto newTree(SysAclModule aclModule){
        AclModelLevelDto dto = new AclModelLevelDto();
        BeanUtils.copyProperties(aclModule,dto);
        return dto;
    }

}
