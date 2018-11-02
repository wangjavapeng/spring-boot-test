package com.yestae.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.yestae.springboot.service.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MyTest {
	@Autowired
    private RedisService redisService;
    @Test
    public void testGetEntFileById(){
    	Object object = redisService.get("haha");
    	System.err.println(object);
    }
}
