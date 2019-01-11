package com.zhaowenx.resource.controller;

import com.zhaowenx.resource.constant.ResponseCode;
import com.zhaowenx.resource.domain.dto.UserDto;
import com.zhaowenx.resource.domain.vo.UserVo;
import com.zhaowenx.resource.model.ResponseVo;
import com.zhaowenx.resource.service.UserService;
import com.zhaowenx.resource.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Created by zhaowenx on 2019/1/11.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/select",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public ResponseVo selectUserByName(@RequestBody UserDto userDto){
        logger.info("UserController|selectUserByName|userDto:"+userDto.toString());
        if(Objects.isNull(userDto)){
            return ResponseUtil.buildVo(false, ResponseCode.PARAMETER_ERROR.getCode(),ResponseCode.PARAMETER_ERROR.getMsg(),null);
        }
        if(StringUtils.isBlank(userDto.getUserName())){
            return ResponseUtil.buildVo(false, ResponseCode.PARAMETER_NULL.getCode(),ResponseCode.PARAMETER_NULL.getMsg(),null);
        }
        UserVo userVo= userService.selectUserByName(userDto.getUserName());
        if(Objects.isNull(userVo) || userVo.getId() == null){
            return ResponseUtil.buildVo(false, ResponseCode.GET_INFORMATION_NULL.getCode(),ResponseCode.GET_INFORMATION_NULL.getMsg(),null);
        }
        logger.info("UserController|selectUserByName|userVo:"+userVo.toString());
        return ResponseUtil.buildVo(true,ResponseCode.CODE_SUCCESS.getCode(),ResponseCode.CODE_SUCCESS.getMsg(),userVo);
    }


}
