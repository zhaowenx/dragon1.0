package com.zhaowenx.manage.channel.userchannel.impl;

import com.zhaowenx.manage.channel.userchannel.UserChannel;
import com.zhaowenx.manage.constant.ResponseCode;
import com.zhaowenx.manage.domain.dto.UserDto;
import com.zhaowenx.manage.model.ResponseVo;
import com.zhaowenx.manage.util.ResponseUtil;
import org.springframework.stereotype.Component;

/**
 * Created by zhaowenx on 2019/1/11.
 */
@Component
public class UserChannelImpl implements UserChannel{

    @Override
    public ResponseVo selectUserByName(UserDto userDto) {
        return ResponseUtil.buildVo(false, ResponseCode.GET_INFORMATION_ERROR.getCode(),ResponseCode.GET_INFORMATION_ERROR.getMsg(),null);
    }
}
