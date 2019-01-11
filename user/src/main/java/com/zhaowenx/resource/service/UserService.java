package com.zhaowenx.resource.service;

import com.zhaowenx.resource.domain.dto.UserDto;
import com.zhaowenx.resource.domain.vo.UserVo;

/**
 * Created by zhaowenx on 2019/1/11.
 */
public interface UserService {

    Integer insertUser(UserDto user);
    Integer updateUser(UserDto userDto);
    UserVo selectUserByName(String userName);
    UserVo selectUserById(Integer id);
    void deleteUserById(Integer id);
}
