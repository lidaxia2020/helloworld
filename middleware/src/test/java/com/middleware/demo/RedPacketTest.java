package com.middleware.demo;

import com.middleware.demo.util.RedPacketUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author daxia li
 * @version 1.0
 * @date 2021/1/12 16:51
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedPacketTest {

    private static final Logger log = LoggerFactory.getLogger(RedPacketTest.class);

    //二倍均值法自测
    @Test
    public void test() throws Exception {
        //总金额单位为分
        Integer amout = 1000;
        //总人数-红包个数
        Integer total = 10;
        //得到随机金额列表
        List<Integer> list = RedPacketUtil.divideRedPackage(amout, total);
        log.info("总金额={}分，总个数={}个", amout, total);

        //用于统计生成的随机金额之和是否等于总金额
        Integer sum = 0;
        //遍历输出每个随机金额
        for (Integer i : list) {
            log.info("随机金额为：{}分，即 {}元", i, new BigDecimal(i.toString()).divide(new BigDecimal(100)));
            sum += i;
        }
        log.info("所有随机金额叠加之和={}分", sum);
    }
}