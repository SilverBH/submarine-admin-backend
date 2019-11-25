package com.htnova.system.entity;

import com.htnova.common.base.BaseTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Dept extends BaseTree<Dept> {

    /**
     * 名称（中文）
     */
    private String name;


    /**
     * 编码
     */
    private String code;

    /**
     * 发布修改部门名称的事件
     */
    public UpdateName updateName() {
        return new UpdateName();
    }

    public class UpdateName {
        public String getName() {
            return name;
        }
        public Long getId() {
            return id;
        }
    }

}