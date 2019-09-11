package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Permission;
import org.javahub.submarine.modules.system.entity.RolePermission;
import org.javahub.submarine.modules.system.mapper.PermissionMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames="PermissionService")
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Transactional(readOnly = true)
    @Cacheable
    public XPage<Permission> findPermissionList(Permission permission, XPage xPage) {
        XPage<Permission> permissionXPage = permissionMapper.findPage(xPage, permission);
        return permissionXPage;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<Permission> findPermissionList(Permission permission) {
        return permissionMapper.findList(permission);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void savePermission(Permission permission) {
        Permission parent = super.getById(permission.getPid());
        if(parent != null) {
            permission.setPids(String.format("%s%s,", Optional.ofNullable(parent.getPids()).orElse(""), parent.getId()));
        }
        super.saveOrUpdate(permission);
    }

    @Transactional
    @Cacheable
    public Permission getPermissionById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void deletePermission(long id) {
        // 删除角色的关联表
        rolePermissionService.remove(new LambdaQueryWrapper<>(new RolePermission()).eq(RolePermission::getPermissionId, id));
        // 删除子级和自身
        List<Permission> permissionList = super.lambdaQuery().like(Permission::getPids, id).list();
        List<Long> ids = permissionList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        ids.add(id);
        super.removeByIds(ids);
    }
}
