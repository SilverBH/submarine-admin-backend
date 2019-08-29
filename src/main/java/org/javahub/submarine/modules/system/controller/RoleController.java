package org.javahub.submarine.modules.system.controller;

import org.javahub.submarine.common.dto.Result;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.dto.RoleDto;
import org.javahub.submarine.modules.system.entity.Role;
import org.javahub.submarine.modules.system.mapstruct.RoleMapStruct;
import org.javahub.submarine.modules.system.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 角色
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 角色分页查询
     */
    @GetMapping("/list/page")
    public XPage<RoleDto> findListByPage(RoleDto roleDto, XPage<RoleDto> xPage) {
        XPage<Role> rolePage = roleService.findRoleList(roleDto.toEntity(), xPage);
        return rolePage.toDto();
    }

    /**
     * 角色查询
     */
    @GetMapping("/list/all")
    public List<RoleDto> findList(RoleDto roleDto) {
        List<Role> roleList = roleService.findRoleList(roleDto.toEntity());
        return CommonUtil.toDto(roleList, RoleMapStruct.class);
    }

    /**
     * 角色详情
     */
    @GetMapping("/detail")
    public RoleDto getById(long id) {
        Role role = roleService.getRoleById(id);
        return role.toDto();
    }

    /**
     * 角色保存
     */
    @PostMapping("/save")
    public Result save(RoleDto roleDto) {
        roleService.saveRole(roleDto.toEntity());
        return Result.successMsg("保存成功");
    }

    /**
     * 角色的权限保存
     */
    @PostMapping("/permission/save")
    public Result saveRolePermission(long id, @RequestParam(value = "permissionList") List<Long> permissionList) {
        roleService.saveRolePermission(id, permissionList);
        return Result.successMsg("保存成功");
    }

    /**
     * 角色的菜单保存
     */
    @PostMapping("/menu/save")
    public Result saveRoleMenu(long id, @RequestParam(value = "menuList") List<Long> menuList) {
        roleService.saveRoleMenu(id, menuList);
        return Result.successMsg("保存成功");
    }

    /**
     * 角色删除
     */
    @DeleteMapping("/del")
    public Result delete(RoleDto roleDto) {
        roleService.deleteRole(roleDto.getId());
        return Result.successMsg("删除成功");
    }
}
