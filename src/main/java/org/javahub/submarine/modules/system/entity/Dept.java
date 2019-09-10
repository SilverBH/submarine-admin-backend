package org.javahub.submarine.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.modules.system.dto.DeptDto;
import org.javahub.submarine.modules.system.mapstruct.DeptMapStruct;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Dept extends BaseEntity {

    /**
     * 名称（中文）
     */
    private String name;


    /**
     * 编码
     */
    private String code;

    /**
     * 父级id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;

    /**
     * 父级ids
     */
    private String pids;

    /**
     * 子节点
     */
    @TableField(exist=false)
    private List<Dept> children;

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

    public DeptDto toDto() {
        DeptMapStruct mapStruct = Mappers.getMapper( DeptMapStruct.class );
        return mapStruct.toDto(this);
    }

}