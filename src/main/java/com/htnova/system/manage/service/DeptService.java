package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.base.BaseEntity;
import com.htnova.system.manage.dto.DeptDto;
import com.htnova.system.manage.entity.Dept;
import com.htnova.system.manage.mapper.DeptMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept> {
    @Resource private DeptMapper deptMapper;

    @Resource private ApplicationContext applicationContext;

    @Transactional(readOnly = true)
    public IPage<Dept> findDeptList(DeptDto deptDto, IPage<Void> xPage) {
        return deptMapper.findPage(xPage, deptDto);
    }

    @Transactional(readOnly = true)
    public List<Dept> findDeptList(DeptDto deptDto) {
        return deptMapper.findList(deptDto);
    }

    @Transactional
    public void saveDept(Dept dept) {
        Dept originalDept = null;
        if (Objects.nonNull(dept.getId())) {
            originalDept = this.getDeptById(dept.getId());
        }
        Dept parent = super.getById(dept.getPid());
        if (parent != null) {
            dept.setPids(
                    String.format(
                            "%s%s,",
                            Optional.ofNullable(parent.getPids()).orElse(""), parent.getId()));
        }
        if (Objects.nonNull(dept.getId())
                && !StringUtils.equals(
                        dept.getName(),
                        Optional.ofNullable(originalDept).map(Dept::getName).orElse(null))) {
            applicationContext.publishEvent(dept.updateName());
        }
        super.saveOrUpdate(dept);
    }

    @Transactional
    public Dept getDeptById(Long id) {
        return deptMapper.selectById(id);
    }

    @Transactional
    public void deleteDept(Long id) {
        // 删除子级和自身
        List<Dept> deptList = super.lambdaQuery().like(id != null, Dept::getPids, id).list();
        List<Long> ids = deptList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        ids.add(id);
        super.removeByIds(ids);
    }
}
