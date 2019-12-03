package com.gizwits.lease.message.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.message.entity.MessageTemplate;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.message.entity.SysMessage;
import com.gizwits.lease.message.entity.dto.MessageTemplateAddDto;
import com.gizwits.lease.message.entity.dto.MessageTemplateDetailDto;
import com.gizwits.lease.order.entity.OrderBase;

import java.util.List;

/**
 * <p>
 * 消息模版表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-03
 */
public interface MessageTemplateService extends IService<MessageTemplate> {

    boolean add(MessageTemplateAddDto dto);

    Page<MessageTemplateDetailDto> list(Pageable pageable);

    boolean update(MessageTemplateAddDto dto);

    String delete(List<Integer> ids);

    MessageTemplateDetailDto detail(Integer id);

    /**
     * 查询依靠数据点上报的消息模版
     * @param productId
     * @return
     */
    List<String> getTriggerByProduct(Integer productId);

    MessageTemplate getByProductIdAndServiceId(Integer productId, Integer serviceId);

    SysMessage sendSysMessage(Device device, MessageTemplate messageTemplate);

    SysMessage sendSysMessage(OrderBase orderBase, MessageTemplate messageTemplate);
}
