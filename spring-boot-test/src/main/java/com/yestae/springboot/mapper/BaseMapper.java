package com.yestae.springboot.mapper;

import java.util.List;

import com.yestae.springboot.entity.User;

public interface BaseMapper {
	
	List<User> getAll();

	User getOne(Long id);

	void insert(User user);

	void update(User user);

	void delete(Long id);
}
