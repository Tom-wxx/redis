package com.example.redisspringboot;

import com.example.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class RedisSpringbootApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;  //操作不同的数据类型，api

    @Test
    void contextLoads() {

        redisTemplate.opsForValue().set("key1","value1");
        System.out.println(redisTemplate.opsForValue().get("key1"));
    }

    @Test
    public  void testNo() throws JsonProcessingException {

        //一般都是用json来传递对象
        User user = new User("王祥祥","3");
   //  String jsonUser = new ObjectMapper().writeValueAsString(user); //转为json
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
