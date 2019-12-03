package com.gizwits.lease.user.service;

import com.gizwits.lease.user.entity.User;

import java.util.Map;

public interface UserPayService {

	Map<String, Object> prePayForOrder(String orderNo, Integer orderType, User user, String sno) ;
}
