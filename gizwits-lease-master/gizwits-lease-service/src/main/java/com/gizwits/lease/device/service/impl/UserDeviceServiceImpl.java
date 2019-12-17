package com.gizwits.lease.device.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.constant.DeviceOnlineStatus;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.UserBindDevice;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.entity.UserDevice;
import com.gizwits.lease.device.dao.UserDeviceDao;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.UserDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserRoom;
import com.gizwits.lease.user.service.UserRoomService;
import com.gizwits.lease.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户绑定设备表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
@Service
public class UserDeviceServiceImpl extends ServiceImpl<UserDeviceDao, UserDevice> implements UserDeviceService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private UserDeviceDao userDeviceDao;

    /**
     * 绑定设备与用户的关系
     *
     * @param deviceDto
     * @return
     */
    @Override
    public Boolean add(UserBindDeviceDto deviceDto) {
        User user=userService.getCurrentUser();
        Device device=deviceService.getDeviceByMac(deviceDto.getMac());
        if(ParamUtil.isNullOrEmptyOrZero(device)){
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
        }
        UserRoom userRoom=new UserRoom();
        String roomName=deviceDto.getRoomName()==null?"默认房间":deviceDto.getRoomName();
        if(!roomName.equals("默认房间")){
             userRoom = userRoomService.selectOne(new EntityWrapper<UserRoom>().eq("name", roomName).eq("is_deleted", 0));
            if (ParamUtil.isNullOrEmptyOrZero(userRoom)) {
                userRoom = new UserRoom();
                userRoom.setCtime(new Date());
                userRoom.setName(roomName);
                userRoom.setUserId(user.getId());
                userRoomService.insert(userRoom);
            }
        }
        UserDevice userDevice = selectOne(new EntityWrapper<UserDevice>().eq("mac", deviceDto.getMac()).eq("user_id", user.getId()));
        if (ParamUtil.isNullOrEmptyOrZero(userDevice)) {
            userDevice = new UserDevice();
            userDevice.setCtime(new Date());
        }else {
            return true;
        }
        userDevice.setUtime(new Date());
        userDevice.setIsBind(1);
        userDevice.setUserId(user.getId());
        userDevice.setSno(device.getSno());
        userDevice.setMac(device.getMac());
        userDevice.setMobile(user.getMobile());
        userDevice.setRoomId(roomName.equals("默认房间")?null:userRoom.getId());
        return insert(userDevice);
    }

    /**
     * 解绑设备与用户的关系
     * @param macs
     * @return
     */
    @Override
    public Boolean deleteBind(List<String> macs) {
        User user = userService.getCurrentUser();
        if(ParamUtil.isNullOrEmptyOrZero(macs)){
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }

        UserDevice userDevice=new UserDevice();
        userDevice.setUtime(new Date());
        userDevice.setIsDeleted(1);
        userDevice.setIsBind(0);
        return update(userDevice,new EntityWrapper<UserDevice>().in("mac",macs).eq("is_deleted",0));
    }

    /**
     * 绑定设备的用户列表
     * @param pageable
     * @return
     */
    @Override
    public Page<UserDevice> userList(Pageable<DeviceSnoDto> pageable){
        Page<UserDevice> page = new Page<>();
        BeanUtils.copyProperties(pageable,page);
        return selectPage(page, QueryResolverUtils.parse(pageable.getQuery(),new EntityWrapper<>()));
    }

    /**
     * 用户绑定的设备列表
     * @param pageable
     * @return
     */
    @Override
    public Page<UserDeviceDto> deviceList(Pageable<UserDeviceQueryDto> pageable) {
        Page<UserDevice> page = new Page<>();
        BeanUtils.copyProperties(pageable,page);
        page = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), new EntityWrapper<>()));

        Page<UserDeviceDto> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        result.setRecords(page.getRecords().stream().map(userBindDevice -> {
            Device device = deviceService.selectById(userBindDevice.getSno());
            if (device == null) {
                return null;
            }
            UserDeviceDto userDeviceDto = new UserDeviceDto();
            String workStatus = DeviceStatus.getShowName(device.getWorkStatus(), device.getFaultStatus(), device.getLock());
            userDeviceDto.setDeviceStatus(workStatus);
            String onLineStatus = DeviceOnlineStatus.getName(device.getOnlineStatus());
            userDeviceDto.setOnlineStatus(onLineStatus);
            return userDeviceDto;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Page getUserDeviceList(Pageable<UserDeviceQueryDto> pageable) {
        User user=userService.getCurrentUser();
        UserDeviceQueryDto queryDto=pageable.getQuery()==null?new UserDeviceQueryDto():pageable.getQuery();
        queryDto.setCurrent((pageable.getCurrent()-1)*pageable.getSize());
        queryDto.setSize(pageable.getSize());
        queryDto.setUserId(user.getId());
        Page<Device> page=new Page<>();
        List<Device> list=new ArrayList<>();
         list=userDeviceDao.selectDeviceByUserId(queryDto);
         if(ParamUtil.isNullOrEmptyOrZero(list)){
             return page;
         }
         Integer total=userDeviceDao.selectDeviceByUserIdCount(queryDto);
         page.setRecords(list);
         page.setTotal(total);
        return page;
    }

    @Override
    public Boolean deviceMove(AppDeviceMoveDto dto) {
        User user=userService.getCurrentUser();
        List<String> macs=dto.getMacs();
        if(ParamUtil.isNullOrEmptyOrZero(macs)){
            return false;
        }
        Date date=new Date();
       macs.stream().forEach(mac->{
           UserDevice userDevice=selectOne(new EntityWrapper<UserDevice>().eq("user_id",user.getId()).eq("mac",mac).eq("is_deleted",0));
           if(!ParamUtil.isNullOrEmptyOrZero(userDevice)) {
               userDevice.setUtime(date);
               userDevice.setRoomId(dto.getRoomId() == 0 ? null : dto.getRoomId());
               updateAllColumnById(userDevice);
           }
       });
       return true;
    }
}
