package com.gizwits.lease.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.user.dto.UserRoomDto;
import com.gizwits.lease.user.dto.UserRoomUpdateDto;
import com.gizwits.lease.user.entity.UserRoom;

import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/16 11:52
 */
public interface UserRoomService extends IService<UserRoom> {

    /**
    *
     * @author Sunny
     * @description 获取用户房间列表
     * @date 2019/12/24 15:36
     * @param  * @param null
     * @return
     * @version 1.0
    */
   List<UserRoomDto> getUserRooms();
   /**
   *
    * @author Sunny
    * @description  修改用户房间
    * @date 2019/12/24 15:36
    * @param  * @param dto
    * @return
    * @version 1.0
   */
   Boolean updateUserRoom(UserRoomUpdateDto dto);

}
