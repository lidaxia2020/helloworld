package com.middleware.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.middleware.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lijiannan
 * @version 1.0
 * @date 2021/1/11 14:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisOperate {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void keyValue() throws JsonProcessingException {
        User user = new User(1, "test", "testName");
        final String content = objectMapper.writeValueAsString(user);
        final String key = "redis:template:key:string";

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, content);
        System.out.println(valueOperations.get(key));
    }

    /**
     * 列表处理
     * 适用于 排名 排行榜 近期访问数据列表
     */
    @Test
    public void list(){
        List<User> list= new ArrayList<>();
        list.add(new User(1, "test", "testName"));
        list.add(new User(2, "test", "testName"));
        list.add(new User(3, "test", "testName"));
        list.add(new User(4, "test", "testName"));
        list.add(new User(5, "test", "testName"));
        final String key = "redis:template:list";

        ListOperations listOperations = redisTemplate.opsForList();
        for (User u : list){
            listOperations.leftPush(key, u);
        }

        Object o = listOperations.rightPop(key);
        User user;
        while (o != null){
            user = (User)o;
            System.out.println(user);
            o = listOperations.rightPop(key);
        }
    }

    /**
     * 集合
     * 常用于解决 重复提交，剔除重复id
     */
    @Test
    public void set(){
        List<User> list= new ArrayList<>();
        list.add(new User(1, "test", "testName"));
        list.add(new User(2, "test", "testName"));
        list.add(new User(3, "test", "testName"));
        list.add(new User(4, "test", "testName"));
        list.add(new User(5, "test", "testName"));
        list.add(new User(3, "test", "testName"));
        final String key = "redis:template:set";

        
        SetOperations setOperations = redisTemplate.opsForSet();
        for (User u : list){
            setOperations.add(key, u);
        }
        Object o = setOperations.pop(key);
        User user;
        while (o != null){
            user = (User)o;
            System.out.println(user);
            o = setOperations.pop(key);
        }
    }

    /**
     * 有序集合
     *  场景： 充值排行榜， 积分排行榜，成绩排行榜等
     */
    @Test
    public void sortSet(){
        List<User> list= new ArrayList<>();
        list.add(new User(60, "60", "testName"));
        list.add(new User(20, "20", "testName"));
        list.add(new User(10, "10", "testName"));
        list.add(new User(30, "30", "testName"));
        list.add(new User(60, "60", "testName"));
        list.add(new User(90, "90", "testName"));
        final String key = "redis:template:sortset";

        
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        for (User u : list){
            zSetOperations.add(key, u, u.getId());
        }
//        Set range = zSetOperations.range(key, 0, list.size());
//        Set<Map> set = zSetOperations.rangeByScore(key, 0, 100);
        Set<User> set = zSetOperations.rangeByScore(key, 0, 100);
//        for (User user : range){
//            System.out.println(user);
//        }
        for (User user : set){
//            System.out.println(objectMapper.convertValue(user, User.class));
            System.out.println(user);
        }
    }


    /**
     * hash 存储
     *  场景： 为了降低缓存中Key的数量
     */
    @Test
    public void hash(){
        List<User> list= new ArrayList<>();
        list.add(new User(60, "60", "testName"));
        list.add(new User(20, "20", "testName"));
        list.add(new User(10, "10", "testName"));
        list.add(new User(30, "30", "testName"));
        list.add(new User(60, "60", "testName"));
        list.add(new User(90, "90", "testName"));
        final String key = "redis:template:hash";

        
        HashOperations hashOperations = redisTemplate.opsForHash();
        for (User u : list){
            hashOperations.put(key, u.getName(), u);
        }

        Map entries = hashOperations.entries(key);
        System.out.println(entries);
    }

    /**
     * key 失效
     */
    @Test
    public void expire(){

        final String key = "redis:template:expire";
        ValueOperations valueOperations = redisTemplate.opsForValue();

        valueOperations.set(key, "expire", 10, TimeUnit.SECONDS);

        valueOperations.set(key, "expire");
        redisTemplate.expire(key, 10, TimeUnit.SECONDS);

        // 判断key值
        Boolean aBoolean = redisTemplate.hasKey(key);

    }

}
