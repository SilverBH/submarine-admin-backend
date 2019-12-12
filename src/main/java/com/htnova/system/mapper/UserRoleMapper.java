package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.entity.Role;
import com.htnova.system.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    IPage<UserRole> findPage(IPage<Void> xPage, @Param("userRole") UserRole userRole);

    List<UserRole> findList(@Param("userRole") UserRole userRole);

    List<Role> getRoleByUserId(@Param("userId") Long userId);

}
