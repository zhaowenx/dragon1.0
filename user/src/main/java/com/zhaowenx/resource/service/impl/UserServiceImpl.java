package com.zhaowenx.resource.service.impl;

import com.zhaowenx.resource.domain.dto.UserDto;
import com.zhaowenx.resource.domain.vo.UserVo;
import com.zhaowenx.resource.mapper.UserMapper;
import com.zhaowenx.resource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaowenx on 2019/1/11.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer insertUser(UserDto user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Integer updateUser(UserDto userDto) {
        return userMapper.updateUser(userDto);
    }

    @Override
    public UserVo selectUserByName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    @Override
    public UserVo selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public void deleteUserById(Integer id) {
        userMapper.deleteUserById(id);
    }
}
