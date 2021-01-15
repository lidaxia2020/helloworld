package com.middleware.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.middleware.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lijiannan
 * @version 1.0
 * @date 2021/1/11 11:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void one() {
        final String content = "Redis 实战";
        final String key = "redis:template:one:string";
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, content);

        System.out.println(valueOperations.get(key));
    }

    @Test
    public void two() throws JsonProcessingException {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        User user = new User(1, "test", "testName");
        final String content = objectMapper.writeValueAsString(user);
        final String key = "redis:template:two:string";

        valueOperations.set(key, content);

        Object result = valueOperations.get(key);

        User user1 = objectMapper.readValue(result.toString(), User.class);
        System.out.println(user1.toString());
    }


    @Test
    public void three() {
        final String content = "Redis 实战";
        final String key = "redis:template:three:string";
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, content);

        System.out.println(valueOperations.get(key));
    }

    @Test
    public void four() throws JsonProcessingException {
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        User user = new User(1, "test", "testName");
        final String content = objectMapper.writeValueAsString(user);
        final String key = "redis:template:four:string";

        valueOperations.set(key, content);

        Object result = valueOperations.get(key);

        User user1 = objectMapper.readValue(result.toString(), User.class);
        System.out.println(user1.toString());
    }


}
