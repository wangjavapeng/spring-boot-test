package com.yestae.springboot.entity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置文件属性注入
 * @author wangpeng
 *
 */
@Configuration
@PropertySource(value = {"classpath:properties/system.properties"})
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4756548083755402967L;
	
	@Value("${user.id}")
	private String id;
	@Value("${user.age}")
	private Integer age;
	@Value("${user.name}")
	private String name;
	
	public void init(){
		System.err.println("user.init()");
		System.err.println(this.id);
	}
	
	public void destory(){
		System.err.println("user.destory()");
	}
	
	public String getId() {
		return id;
	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", age=").append(age).append(", name=").append(name).append("]");
		return builder.toString();
	}
}
