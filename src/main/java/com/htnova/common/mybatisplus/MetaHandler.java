package com.htnova.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.util.UserUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MetaHandler implements MetaObjectHandler {

    /** 新增的时候自动填充 */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId =
                Optional.ofNullable(UserUtil.getAuthUser()).map(BaseEntity::getId).orElse(null);
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    /** 更新的时候自动填充 */
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId =
                Optional.ofNullable(UserUtil.getAuthUser()).map(BaseEntity::getId).orElse(null);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }
}
