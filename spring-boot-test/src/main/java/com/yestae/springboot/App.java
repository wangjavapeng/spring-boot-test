package com.yestae.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
	
    public static void main( String[] args ){
    	SpringApplication.run(App.class);
    	//测试bean被销毁
//    	context.close();
    }
    
}
