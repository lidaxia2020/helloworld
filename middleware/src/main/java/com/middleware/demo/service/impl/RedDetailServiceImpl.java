package com.middleware.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.middleware.demo.dto.RedPacketDto;
import com.middleware.demo.mapper.RedRecordMapper;
import com.middleware.demo.mapper.RedRobRecordMapper;
import com.middleware.demo.model.RedDetail;
import com.middleware.demo.mapper.RedDetailMapper;
import com.middleware.demo.model.RedRecord;
import com.middleware.demo.model.RedRobRecord;
import com.middleware.demo.service.RedDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class RedDetailServiceImpl extends ServiceImpl<RedDetailMapper, RedDetail> implements RedDetailService {

    private static final Logger log= LoggerFactory.getLogger(RedDetailServiceImpl.class);

    @Resource
    private RedRecordMapper redRecordMapper;

    @Resource
    private RedDetailMapper redDetailMapper;

    @Resource
    private RedRobRecordMapper redRobRecordMapper;

    /**
     * 发红包记录
     * @param dto
     * @param redId
     * @param list
     * @throws Exception
     */
    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception {
        RedRecord redRecord=new RedRecord();
        redRecord.setUserId(dto.getUserId());
        redRecord.setRedPacket(redId);
        redRecord.setTotal(dto.getTotal());
        redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
        redRecordMapper.insert(redRecord);

        RedDetail detail;
        for (Integer i:list){
            detail=new RedDetail();
            detail.setRecordId(redRecord.getId());
            detail.setAmount(BigDecimal.valueOf(i));
            redDetailMapper.insert(detail);
        }
    }


    /**
     * 抢红包记录
     * @param userId
     * @param redId
     * @param amount
     * @throws Exception
     */
    @Override
    @Async
    public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {
        RedRobRecord redRobRecord=new RedRobRecord();
        redRobRecord.setUserId(userId);
        redRobRecord.setRedPacket(redId);
        redRobRecord.setAmount(amount);
        redRobRecord.setRobTime(new Date());
        redRobRecordMapper.insert(redRobRecord);
    }
}