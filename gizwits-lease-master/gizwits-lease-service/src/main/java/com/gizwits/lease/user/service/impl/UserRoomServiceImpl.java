package com.gizwits.lease.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.user.dao.UserRoomDao;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserRoom;
import com.gizwits.lease.user.service.UserRoomService;
import com.gizwits.lease.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Override
    public List<UserRoom> getUserRoom() {
        User user=userService.getCurrentUser();
        List<UserRoom> list=new ArrayList<>();
        list=selectList(new EntityWrapper<UserRoom>().eq("user_id",user.getId()).eq("is_deleted",0));
        return list;
    }
}
