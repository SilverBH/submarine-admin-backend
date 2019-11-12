package com.htnova.scaffold.modules.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.scaffold.common.base.BaseDto;
import com.htnova.scaffold.modules.system.entity.Permission;
import com.htnova.scaffold.modules.system.mapstruct.PermissionMapStruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionDto extends BaseDto {

    /**
     * 类型
     */
    private Permission.PermissionType type;

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
     * 子节点
     */
    private List<PermissionDto> children;

    public static Permission toEntity(PermissionDto permissionDto) {
        PermissionMapStruct mapStruct = Mappers.getMapper( PermissionMapStruct.class );
        return mapStruct.toEntity(permissionDto);
    }

    public static PermissionDto toDto(Permission permission) {
        PermissionMapStruct mapStruct = Mappers.getMapper( PermissionMapStruct.class );
        return mapStruct.toDto(permission);
    }

}
