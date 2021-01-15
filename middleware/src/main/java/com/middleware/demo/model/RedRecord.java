package com.middleware.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedRecord {

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 红包全局唯一标识串
     */
    private String redPacket;
    /**
     * 人数
     */
    private Integer total;
    /**
     *
     */
    private Date createTime;

    private BigDecimal amount;

    private Byte isActive;

}