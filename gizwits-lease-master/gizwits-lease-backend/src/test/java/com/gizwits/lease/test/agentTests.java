package com.gizwits.lease.test;

import com.gizwits.lease.demo.service.OrderDemoService;
import com.gizwits.lease.manager.dto.AgentForAddDto;
import com.gizwits.lease.manager.service.AgentService;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class agentTests {

    private static final Logger logger = LoggerFactory.getLogger(agentTests.class);

    @Autowired
    private AgentService agentService;


    /**
     * 测试添加经销商
     */
    @Test
    @Commit
    public void testEndOrders() {
        AgentForAddDto agentForAddDto = new AgentForAddDto();
        agentForAddDto.setStatus(1);
        agentForAddDto.setAddress("创意产业园");
        agentForAddDto.setProvince("江苏省");
        agentForAddDto.setCity("苏州市");
        agentForAddDto.setArea("吴中区");
        agentForAddDto.setName("苏州经销商");
        agentForAddDto.setRemarks("测试");
        agentService.add(agentForAddDto);
    }


}
