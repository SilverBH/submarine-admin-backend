package org.javahub.submarine.modules.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.modules.security.mapstruct.JwtUserMapStruct;
import org.javahub.submarine.modules.system.entity.Menu;
import org.javahub.submarine.modules.system.entity.Role;
import org.javahub.submarine.modules.system.entity.User;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser extends BaseEntity implements UserDetails {

    @JsonIgnore
    public static final String SESSION_KEY = "jwtUser";

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态（启用禁用）
     */
    private User.UserStatus status;

    /**
     * 部门id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /**
     * 部门ids（包含自身）
     */
    private String deptIds;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * jwt密钥
     */
    @JsonIgnore
    private String jwtSecret;

    /**
     * 角色
     */
    private List<Role> roleList;

    /**
     * 角色
     */
    private List<Menu> menuList;


    @JsonIgnore
    private List<GrantedAuthority> authorities;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return User.UserStatus.enable.equals(status);
    }

    public List<String> getRoles() {
        return roleList.stream().map(Role::getCode).collect(Collectors.toList());
    }

    public List<String> getPermissions() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public List<String> getMenus() {
        return menuList.stream().map(Menu::getValue).collect(Collectors.toList());
    }

    public Boolean isSuperAdmin() {
        return roleList.stream().map(Role::getCode).anyMatch(item -> Role.RoleCode.SuperAdmin.toString().equals(item));
    }

    public Boolean isAdmin() {
        return roleList.stream().map(Role::getCode).anyMatch(item -> Role.RoleCode.Admin.toString().equals(item));
    }

    public static UserDetails createByUser(User user) {
        JwtUserMapStruct mapper = Mappers.getMapper(JwtUserMapStruct.class);
        JwtUser jwtUser = mapper.toDto(user);
        jwtUser.authorities = user.getPermissionList().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
        return jwtUser;
    }
}
