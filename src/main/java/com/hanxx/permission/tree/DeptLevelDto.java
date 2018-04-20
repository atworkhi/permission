package com.hanxx.permission.tree;

import com.google.common.collect.Lists;
import com.hanxx.permission.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-16:08
 * 部门树形结构的dto
 */
@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept{

    // 用来接收传入的树形结构层次
    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto newTrue(SysDept dept){
        DeptLevelDto dto = new DeptLevelDto();
        //把dept接收的数据拷贝到 dto
        BeanUtils.copyProperties(dept,dto);
        return dto;
    }
}

