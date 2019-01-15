package com.zhaowenx.manage.controller;

import com.zhaowenx.manage.channel.userchannel.UserChannel;
import com.zhaowenx.manage.constant.ResponseCode;
import com.zhaowenx.manage.domain.User;
import com.zhaowenx.manage.domain.dto.UserDto;
import com.zhaowenx.manage.model.ResponseVo;
import com.zhaowenx.manage.util.BeanCopierUtil;
import com.zhaowenx.manage.util.BeanIsEmptyUtil;
import com.zhaowenx.manage.util.ResponseUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhaowenx on 2019/1/11.
 */
@RestController
@RequestMapping("/api/user")
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserChannel userChannel;

    @Value("${author}")
    private String authorName;

    /**
     * 用户信息查询
     * @Param
     */
    @PostMapping(value = "/select")
    @ResponseBody
    public ResponseVo selectUserByName(String userName) throws  Exception{
        logger.info("UserController selectUserByName userName = "+userName);
        if(StringUtils.isBlank(userName)){
            return ResponseUtil.buildVo(false, ResponseCode.PARAMETER_ERROR.getCode(),"用户名不能为空",null);
        }
        UserDto userDto = new UserDto();
        userDto.setUserName(userName);
        return userChannel.selectUserByName(userDto);
    }

    @PostMapping(value = "/test")
    @ResponseBody
    public ResponseVo test(){
        return ResponseUtil.buildVo(true,000000,"success",this.authorName);
    }




}
