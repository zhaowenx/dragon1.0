package com.zhaowenx.manage.channel.userchannel;

import com.zhaowenx.manage.channel.userchannel.impl.UserChannelImpl;
import com.zhaowenx.manage.domain.dto.UserDto;
import com.zhaowenx.manage.model.ResponseVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhaowenx on 2019/1/11.
 */
@FeignClient(value = "user", fallback = UserChannelImpl.class)
public interface UserChannel {

    @RequestMapping(value = "/user/select", method = RequestMethod.POST, consumes = "application/json")
    ResponseVo selectUserByName(@RequestBody UserDto userDto);
}
