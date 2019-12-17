package com.gizwits.lease.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.user.entity.UserRoom;

import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/16 11:52
 */
public interface UserRoomService extends IService<UserRoom> {

    List<UserRoom> getUserRoom();

}
