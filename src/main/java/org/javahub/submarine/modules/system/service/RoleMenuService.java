package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.RoleMenu;
import org.javahub.submarine.modules.system.mapper.RoleMenuMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames="RoleMenuService")
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional(readOnly = true)
    @Cacheable
    public XPage<RoleMenu> findRoleMenuList(RoleMenu roleMenu, XPage xPage) {
        XPage<RoleMenu> roleMenuXPage = roleMenuMapper.findPage(xPage, roleMenu);
        return roleMenuXPage;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<RoleMenu> findRoleMenuList(RoleMenu roleMenu) {
        return roleMenuMapper.findList(roleMenu);
    }

    /**
     * 保存角色的菜单
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public void saveRoleMenu(long roleId, List<Long> menuIdList) {
        // 删除旧的
        super.remove(new LambdaQueryWrapper<>(new RoleMenu()).eq(RoleMenu::getRoleId, roleId));
        List<RoleMenu> roleMenuList = menuIdList.stream()
                .map(item -> RoleMenu.builder().menuId(item).roleId(roleId).build())
                .collect(Collectors.toList());
        super.saveBatch(roleMenuList);
    }

    @Transactional
    @Cacheable
    public RoleMenu getRoleMenuById(long id) {
        return roleMenuMapper.selectById(id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteRoleMenu(Long id) {
        super.removeById(id);
    }
}
