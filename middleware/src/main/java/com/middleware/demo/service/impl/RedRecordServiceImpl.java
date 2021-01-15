package com.middleware.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.middleware.demo.model.RedRecord;
import com.middleware.demo.mapper.RedRecordMapper;
import com.middleware.demo.service.RedRecordService;
import org.springframework.stereotype.Service;

@Service
public class RedRecordServiceImpl extends ServiceImpl<RedRecordMapper, RedRecord> implements RedRecordService{

}