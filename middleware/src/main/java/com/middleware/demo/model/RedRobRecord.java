package com.middleware.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedRobRecord {

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 用户账号
     */
    private Integer userId;
    /**
     * 红包标识串
     */
    private String redPacket;
    /**
     * 时间
     */
    private Date robTime;

    private BigDecimal amount;

    private Byte isActive;

}