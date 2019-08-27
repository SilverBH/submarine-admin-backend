package org.javahub.submarine.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.dto.PermissionDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Permission extends BaseEntity {

    /**
     * 名称（中文）
     */
    private String name;


    /**
     * 权限值
     */
    private String value;

    /**
     * 父级id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;

    /**
     * 父级ids
     */
    private String pids;

    /**
     * 子节点
     */
    @TableField(exist=false)
    private List<Permission> children;

    public PermissionDto toDto() {
        PermissionDto permissionDto = super.copyProperties(PermissionDto.class);
        permissionDto.setChildren(CommonUtil.toDto(children));
        return permissionDto;
    }

}