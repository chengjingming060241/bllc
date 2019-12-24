package com.gizwits.lease.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.entity.UserDevice;
import com.gizwits.lease.device.service.UserBindDeviceService;
import com.gizwits.lease.device.service.UserDeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.user.dao.UserRoomDao;
import com.gizwits.lease.user.dto.UserRoomDto;
import com.gizwits.lease.user.dto.UserRoomUpdateDto;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserRoom;
import com.gizwits.lease.user.service.UserRoomService;
import com.gizwits.lease.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/17 14:40
 */
@Slf4j
@Service
public class UserRoomServiceImpl extends ServiceImpl<UserRoomDao, UserRoom> implements UserRoomService {


    @Autowired
    private UserService userService;

    @Autowired
    private UserDeviceService userDeviceService;

    @Override
    public List<UserRoomDto> getUserRooms() {
        User user=userService.getCurrentUser();
        List<UserRoom> list=selectList(new EntityWrapper<UserRoom>().eq("user_id",user.getId()).eq("is_deleted",0));
        List<UserRoomDto> result=new ArrayList<>();
        if(!ParamUtil.isNullOrEmptyOrZero(list)){
             list.stream().forEach(item->{
                 UserRoomDto dto=new UserRoomDto();
                 BeanUtils.copyProperties(item,dto);
                 Integer count=userDeviceService.selectCount(new EntityWrapper<UserDevice>().eq("room_id",item.getId()).eq("is_deleted",0));
              dto.setDeviceCount(count);
             });
        }
        return result;
    }

    @Override
    public Boolean updateUserRoom(UserRoomUpdateDto dto) {

        UserRoom userRoom=selectById(dto.getId());
        if(ParamUtil.isNullOrEmptyOrZero(userRoom)){
            LeaseException.throwSystemException(LeaseExceEnums.USER_ROOM_NOT_EXIST);
        }
        userRoom.setName(dto.getName());
        userRoom.setUtime(new Date());
        return updateById(userRoom);
    }
}
