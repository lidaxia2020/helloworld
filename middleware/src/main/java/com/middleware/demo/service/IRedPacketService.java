package com.middleware.demo.service;

import com.middleware.demo.dto.RedPacketDto;

import java.math.BigDecimal;

/**
 * 红包业务逻辑处理接口
 **/
public interface IRedPacketService {
    //发红包
    String handOut(RedPacketDto dto) throws Exception;

    BigDecimal rob(Integer userId, String redId) throws Exception;
}