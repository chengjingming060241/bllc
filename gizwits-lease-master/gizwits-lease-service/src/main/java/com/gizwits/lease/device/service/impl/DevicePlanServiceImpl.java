package com.gizwits.lease.device.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.dao.DevicePlanDao;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DevicePlan;
import com.gizwits.lease.device.entity.dto.PlanAddDto;
import com.gizwits.lease.device.entity.dto.PlanUpdateDto;
import com.gizwits.lease.device.service.DevicePlanService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/12/24 9:33
 */
@Slf4j
@Service
public class DevicePlanServiceImpl extends ServiceImpl<DevicePlanDao, DevicePlan> implements DevicePlanService {


    @Autowired
    private UserService userService;
    @Autowired
    private DeviceService deviceService;
    @Override
    public List<DevicePlan> list(String mac) {
        User user=userService.getCurrentUser();
         if(mac==null){
             LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
         }
         Device device=deviceService.getDeviceByMac(mac);
         if(ParamUtil.isNullOrEmptyOrZero(device)){
             LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
         }
         List<DevicePlan> list=selectList(new EntityWrapper<DevicePlan>().eq("mac",mac).eq("user_id",user.getId()).eq("is_deleted",0));
        return list;
    }

    @Override
    public Boolean addPlan(PlanAddDto planAddDto) {
        String mac=planAddDto.getMac();
        String content=planAddDto.getContent();
          if(mac==null||content==null){
              LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
          }
        User user=userService.getCurrentUser();
          Device device=deviceService.getDeviceByMac(mac);
          if(ParamUtil.isNullOrEmptyOrZero(device)){
              LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
          }
          String time=null;
          String repeat=null;
          Boolean status=null;
          //每次添加需要验证参数是否合法，不合法打回
          try {
              JSONObject jsonObject= JSON.parseObject(content);
              time=jsonObject.getString("time");
              repeat=jsonObject.getString("repeat");
              status=jsonObject.getBoolean("status");

          }catch (Exception e){
              log.error("定时内容解析失败,原因：{},time:{},repeat：{}，status：{}",e.getMessage(),time,repeat,status);
             LeaseException.throwSystemException(LeaseExceEnums.DEVICE_PLAN_PARAM_ERROR);
          }
          //添加前需要验证是否重复
        List<DevicePlan> plans=selectList(new EntityWrapper<DevicePlan>().eq("mac",mac).eq("user_id",user.getId()).eq("is_deleted",0));
        checkPlan(plans,time,repeat,status);

          DevicePlan devicePlan=new DevicePlan();
          devicePlan.setCtime(new Date());
          devicePlan.setUtime(new Date());
          devicePlan.setUserId(user.getId());
          devicePlan.setMac(mac);
          devicePlan.setContent(content);
          devicePlan.setIsUsed(1);
        return insert(devicePlan);
    }

    @Override
    public Boolean delete(Integer id) {
        DevicePlan devicePlan=selectById(id);
        if(ParamUtil.isNullOrEmptyOrZero(devicePlan)){
            LeaseException.throwSystemException(LeaseExceEnums.PLAN_NOT_EXIST);
        }
        devicePlan.setUtime(new Date());
        devicePlan.setIsDeleted(1);
        return updateById(devicePlan);
    }

    @Override
    public Boolean update(PlanUpdateDto updateDto) {
        User user=userService.getCurrentUser();
         Integer planId=updateDto.getPlanId();
         String content=updateDto.getContent();
         if(planId==null||content==null){
             LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
         }
         DevicePlan devicePlan=selectById(planId);
         if(ParamUtil.isNullOrEmptyOrZero(devicePlan)){
             LeaseException.throwSystemException(LeaseExceEnums.PLAN_NOT_EXIST);
         }
        String time=null;
        String repeat=null;
        Boolean status=null;
        //每次添加需要验证参数是否合法，不合法打回
        try {
            JSONObject json= JSON.parseObject(content);
            time=json.getString("time");
            repeat=json.getString("repeat");
            status=json.getBoolean("status");
        }catch (Exception e){
            log.error("定时内容解析失败,原因：{},time:{},repeat：{}，status：{}",e.getMessage(),time,repeat,status);
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_PLAN_PARAM_ERROR);
        }
        //修改前需要验证是否重复，去掉被修改的
        List<DevicePlan> plans=selectList(new EntityWrapper<DevicePlan>().eq("mac",devicePlan.getMac()).ne("id",planId).eq("user_id",user.getId()).eq("is_deleted",0));
        checkPlan(plans,time,repeat,status);

        devicePlan.setContent(content);
        return updateById(devicePlan);
    }

    private void checkPlan(List<DevicePlan> plans,String time,String repeat,Boolean status){
        Boolean flag=false;
        if(!ParamUtil.isNullOrEmptyOrZero(plans)) {
            for (DevicePlan plan : plans) {
                JSONObject planJson = JSONObject.parseObject(plan.getContent());
                if (time.equals(planJson.getString("time"))
                        || repeat.equals(planJson.getString("repeat"))
                        || status == planJson.getBoolean("status")) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                LeaseException.throwSystemException(LeaseExceEnums.PLAN_REPEAT_ERROR);
            }
        }
    }

    @Override
    public Boolean openOrClosePlan(Integer planId) {
        User user=userService.getCurrentUser();
        DevicePlan devicePlan=selectById(planId);
        if(ParamUtil.isNullOrEmptyOrZero(devicePlan)){
            LeaseException.throwSystemException(LeaseExceEnums.PLAN_NOT_EXIST);
        }
        devicePlan.setUtime(new Date());
        if(devicePlan.getIsUsed()==0){
            devicePlan.setUserId(1);
        }else{
            devicePlan.setUserId(0);
        }
        return updateById(devicePlan);
    }
}
