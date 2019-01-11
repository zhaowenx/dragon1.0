package com.zhaowenx.resource.mapper;

import com.zhaowenx.resource.domain.dto.UserDto;
import com.zhaowenx.resource.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhaowenx on 2019/1/11.
 */
@Mapper
public interface UserMapper {
    Integer insertUser(UserDto user);
    Integer updateUser(UserDto userDto);
    UserVo selectUserByName(String userName);
    UserVo selectUserById(Integer id);
    UserVo checkUserNameAndPassWord(UserDto userDto);
    void updateUserAfterLogin(UserDto userDto);
    Integer updatePassWord(@Param("id") Integer id, @Param("password") String password);

    String selectUserNameByUserId(Integer userId);
    Integer selectUserVoByPhoneAndUserName(@Param("mobile") String mobile,@Param("userName") String userName);
//    Integer addUser(UserDto userDto);
//    List<UserVo> selectAllNotSuper(@Param("pageModel") PageModel pageModel);
    Integer countUserNotSuper();
    void deleteUserById(@Param("id") Integer id);

    List<UserVo> selectReceiveUser(@Param("userId") Integer userId);
}
