package io.github.wzk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName alarmlist
 */
@TableName(value ="alarmlist")
@Data
public class Alarmlist implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "time")
    private Date time;

    /**
     * 
     */
    @TableField(value = "level")
    private String level;

    /**
     * 
     */
    @TableField(value = "state")
    private String state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}