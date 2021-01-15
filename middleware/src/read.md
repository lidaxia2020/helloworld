# Redis 使用
### 常用命令
```
1、查看所有key : key *
2、查看缓存中指定key的值： get keyName
3、删除指定的key: del keyName
4、

        // 实体必须带上Serializable
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

```

### java 序列化策略
```

   /**
     * 缓存操作组件redisTemplate
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        /**
         * 指定key 序列化策略为String序列化，Value 为JDK序列化
         */
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        // 实体必须带上Serializable
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
```

### Redis 缓存穿透
- 由于null的数据没有存入redis，导致一直请求到数据库造成的压力。
- 解决：将null的数据也设置过期时间存到reids中

### Redis 缓存雪崩
- 指的是某个时间点，缓存中的key集体发生过期失效，导致大量查询落在数据库上，导致数据库过载过高，压力暴增，
- 解决：将key过期时间设置不同的、随机的TTL，

### Redis 缓存击穿
- 值缓存中的某个频繁被访问的key(热点key)失效，
- 解决：热点数据设置永久生效


### 抢红包模拟 RedPacketController