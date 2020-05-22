package com.htnova.system.manage.entity;

import com.htnova.common.base.BaseEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserRole extends BaseEntity {
    /** 用户id */
    private Long userId;

    /** 角色id */
    private Long roleId;

    /** 角色列表 */
    private List<Role> roleList;
}
