package org.javahub.submarine.modules.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseDto;
import org.javahub.submarine.modules.system.entity.Role;
import org.javahub.submarine.modules.system.mapstruct.RoleMapStruct;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto extends BaseDto {


    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 权限
     */
    private List<PermissionDto> permissionList;

    /**
     * 菜单
     */
    private List<MenuDto> menuList;

    public Role toEntity() {
        RoleMapStruct mapStruct = Mappers.getMapper( RoleMapStruct.class );
        return mapStruct.toEntity(this);
    }

}