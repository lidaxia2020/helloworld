package com.middleware.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.middleware.demo.dto.RedPacketDto;
import com.middleware.demo.model.RedDetail;

import java.math.BigDecimal;
import java.util.List;

public interface RedDetailService extends IService<RedDetail> {

    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception;

    void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception;
}