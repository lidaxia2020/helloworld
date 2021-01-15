package com.middleware.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedDetail {

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 红包记录id
     */
    private Integer recordId;
    /**
     *
     */
    private Date createTime;

    private BigDecimal amount;

    private Byte isActive;

}