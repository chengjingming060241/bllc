package com.gizwits.lease.device.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.constant.DeviceOnlineStatus;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.constant.DeviceWorkStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.UserBindDevice;
import com.gizwits.lease.device.dao.UserBindDeviceDao;
import com.gizwits.lease.device.entity.dto.DeviceSnoDto;
import com.gizwits.lease.device.entity.dto.UserBindDeviceDto;
import com.gizwits.lease.device.entity.dto.UserDeviceDto;
import com.gizwits.lease.device.entity.dto.UserDeviceQueryDto;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.UserBindDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.query.QueryUtils;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户绑定设备表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-22
 */
@Service
public class UserBindDeviceServiceImpl extends ServiceImpl<UserBindDeviceDao, UserBindDevice> implements UserBindDeviceService {

}
