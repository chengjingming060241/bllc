package com.gizwits.lease.manager.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.dto.SysUserExtForAddDto;
import com.gizwits.boot.dto.SysUserForAddDto;
import com.gizwits.boot.dto.SysUserForPullDto;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.constant.AgentExcelTemplate;
import com.gizwits.lease.constant.BooleanEnum;
import com.gizwits.lease.constant.DeviceLaunchAreaExcelTemplate;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaExportResultDto;
import com.gizwits.lease.device.entity.dto.DeviceQueryDto;
import com.gizwits.lease.device.entity.dto.OperatorStatusChangeDto;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.enums.DeviceExPortFailType;
import com.gizwits.lease.enums.ExistEnum;
import com.gizwits.lease.enums.StatusType;
import com.gizwits.lease.event.NameModifyEvent;
import com.gizwits.lease.event.OperatorStatusChangeEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.dao.AgentDao;
import com.gizwits.lease.manager.dto.*;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.entity.Operator;
import com.gizwits.lease.manager.service.AgentService;
import com.gizwits.lease.manager.service.ManufacturerService;

import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 代理商表 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class AgentServiceImpl extends ServiceImpl<AgentDao, Agent> implements AgentService, InitializingBean {


    private static final Logger LOGGER = LoggerFactory.getLogger(AgentServiceImpl.class);
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;



    @Override
    @Transactional
    public boolean add(AgentForAddDto dto) {
//        //1.必要时插入系统账号
//        Integer accountId = acquireBindingAccount(dto.getBindingExistAccount(),
//                dto.getBindingAccountId(), dto.getUser());
        //2.插入代理商
        return addAgent(dto);
    }

    @Override
    @Transactional
    public void bind(AgentForBindDto dto) {
        SysUser parent = null;
        String tree = null;
        if (ParamUtil.isNullOrEmptyOrZero(dto.getParentAgentId())) {
            parent = sysUserService.getCurrentUserOwner();
            Agent parentAgent = selectOne(new EntityWrapper<Agent>().eq("sys_account_id", parent.getId()));
            if (parentAgent != null) {
                dto.setParentAgentId(parentAgent.getId());
            }
        } else {
            Agent parentAgent = selectById(dto.getParentAgentId());
            parent = sysUserService.selectById(parentAgent.getSysAccountId());
            //如果为代理商指定了上级则需修改系统用户的tree_path
            String treePath = parent.getTreePath() + parent.getId() + ",";

        }
        Agent agent = selectById(dto.getId());
        if (Objects.isNull(agent)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        //1.必要时插入系统账号
        Integer accountId = acquireBindingAccount(dto.getBindingExistAccount(), dto.getBindingAccountId(), dto.getUser());
        //2.更新代理商
        Agent forUpdate = new Agent();
        forUpdate.setId(agent.getId());
        SysUser sysUser = sysUserService.selectById(accountId);
        forUpdate.setSysAccountId(accountId);
        forUpdate.setParentAgentId(parent.getId());
        // 更新 SysUser 的 isAdmin 和 parentAdminId 字段
        boolean result = sysUserService.updateSysUserAdmin(accountId, SysUserType.AGENT, sysUser.getParentAdminId());
        //更新tree_path
        //如果为代理商指定了上级则需修改系统用户的tree_path
        if (!ParamUtil.isNullOrEmptyOrZero(tree)) {
            sysUser.setTreePath(tree);
            sysUserService.updateById(sysUser);
        }

        if (!result) {
            LOGGER.error("updateSysUserAdmin id {} fail", accountId);
            LeaseException.throwSystemException(LeaseExceEnums.OPERATION_FAIL);
        }

        forUpdate.setUtime(new Date());
        updateById(forUpdate);
    }

    @Override
    public Agent getParentAgentBySysAccountId(Integer sysAccountId) {
        if (ParamUtil.isNullOrEmptyOrZero(sysAccountId)) {
            return null;
        }
        SysUser relAccount = sysUserService.selectById(sysAccountId);
        return resolveParentAgent(relAccount);
    }

    private Agent resolveParentAgent(SysUser child) {
        //查到管理员时，直接返回null
        if (Objects.isNull(child) || Objects.equals(child.getId(), child.getSysUserId())) {
            return null;
        } else {
            SysUser parent = sysUserService.selectById(child.getSysUserId());
            Agent agent = selectOne(new EntityWrapper<Agent>().eq("sys_account_id", parent.getId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            if (Objects.nonNull(agent)) {
                return agent;
            } else {
                return resolveParentAgent(parent);
            }
        }
    }


    @Override
    public Page<AgentForListDto> page(Pageable<AgentForQueryDto> pageable) {

        Page<Agent> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        EntityWrapper<Agent> wrapper = new EntityWrapper<>();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());

        //出库量
        if (pageable.getQuery().getStockType()!=null && pageable.getQuery().getStockNumber()!= null && pageable.getQuery().getStockType()>0 && pageable.getQuery().getStockNumber()>0){
            if (pageable.getQuery().getStockType() == 1){
                wrapper.eq("stock_number",pageable.getQuery().getStockNumber());
            }if (pageable.getQuery().getStockType() == 2){
                wrapper.ge("stock_number",pageable.getQuery().getStockNumber());
            }if (pageable.getQuery().getStockType() == 3){
                wrapper.le("stock_number",pageable.getQuery().getStockNumber());
            }
        }
        //修改时间
        if (pageable.getQuery().getUpTimeStart()!=null && pageable.getQuery().getUpTimeEnd()!=null){
            wrapper.between("utime",pageable.getQuery().getUpTimeStart(),pageable.getQuery().getUpTimeEnd());
        }
        Page<Agent> selectPage =  selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), wrapper));


        Page<AgentForListDto> result = new Page<>();
        BeanUtils.copyProperties(selectPage, result);
        if (CollectionUtils.isNotEmpty(selectPage.getRecords())) {
            result.setRecords(new ArrayList<>(selectPage.getRecords().size()));
            selectPage.getRecords().forEach(item -> {
//                Integer deviceCount = resolveDeviceCount(item);
                //如果有设备但是还是待分配状态，证明状态有误，update
//                if (deviceCount > 0 && item.getStatus().equals(StatusType.NEED_TO_ASSIGN.getCode())) {
//                    Agent forUpdate = new Agent();
//                    forUpdate.setId(item.getId());
//                    forUpdate.setStatus(StatusType.OPERATING.getCode());
//                    updateById(forUpdate);
//                    item.setStatus(forUpdate.getStatus());
//                }
                AgentForListDto dto = new AgentForListDto(item);
//                dto.setDeviceCount(deviceCount);

//                setAgentInfo(item, dto);
                result.getRecords().add(dto);
                result.setTotal(selectPage.getTotal());
                result.setCurrent(pageable.getCurrent());
            });
        }
        return result;
    }

    private void setAgentInfo(Agent agent, AgentForListDto dto) {
        if (agent.getSysAccountId() == null) {
            return;
        }
        SysUser sysUser = sysUserService.selectById(agent.getSysAccountId());
        if (sysUser == null) {
            return;
        }
        SysUser parent = sysUserService.selectById(sysUser.getParentAdminId());
        if (parent == null) {
            return;
        }
        Agent parentAgent = selectOne(new EntityWrapper<Agent>().eq("sys_account_id", parent.getId()));
        if (parentAgent != null) {
            // 上级代理商昵称
            dto.setParentAgent(parentAgent.getName());
        } else {
            // 厂商昵称
            dto.setParentAgent(parent.getNickName());
        }
        // 代理商等级
        String[] parentUserIds = sysUser.getTreePath().split(",");
        int agentLevel = sysUserService.selectCount(
                new EntityWrapper<SysUser>().ne("is_admin", SysUserType.NORMAL).in("id", parentUserIds));
        dto.setAgentLevel(agentLevel - 1);
    }

    @Override
    public AgentForDetailDto detail(Integer id) {
        Agent agent = selectById(id);
        if (Objects.isNull(agent)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
//        result.setDeviceCount(resolveDeviceCount(agent));
//        result.setAccount(sysUserService.basic(agent.getSysAccountId()));

        return new AgentForDetailDto(agent);
    }

    @Override
    public AgentForDetailDto update(AgentForUpdateDto dto) {
        Agent agent = selectById(dto.getId());
        if (Objects.isNull(agent)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        BeanUtils.copyProperties(dto, agent);
        agent.setUtime(new Date());
        updateById(agent);
        return detail(dto.getId());
    }

    @Override
    public boolean change(AgentForUpdateDto dto) {
        assertNotNull(dto.getAgentIds());
        boolean result = false;
        for (Integer agentId : dto.getAgentIds()) {
            Agent agent = selectById(agentId);
            if (Objects.isNull(agent)) {
                LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
            }
            OperatorStatusChangeDto changeDto = new OperatorStatusChangeDto(agent);
            agent.setStatus(dto.getStatus());
            agent.setUtime(new Date());
            result = updateById(agent);
            changeDto.setTo(agent.getStatus());  //修改后的状态
            changeDto.setCurrent(sysUserService.getCurrentUser());
            if (result) {
                CommonEventPublisherUtils.publishEvent(new OperatorStatusChangeEvent(changeDto));
            }
        }

        return result;
    }

    private Integer resolveDeviceCount(Agent item) {
        //直接分配给代理商的设备数量
        return deviceService.selectCount(new EntityWrapper<Device>()
                .eq("owner_id", item.getSysAccountId())
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }


    private boolean addAgent(AgentForAddDto dto) {
        SysUser currentUserOwner = sysUserService.getCurrentUserOwner();
        //判断名称是否相同
        checkAgentNameDup(dto.getName(), null);
        Agent agent = new Agent();
        BeanUtils.copyProperties(dto, agent);
        agent.setSysUserId(currentUserOwner.getId());
        agent.setSysUserName(currentUserOwner.getNickName());
        agent.setCtime(new Date());
        agent.setUtime(agent.getCtime());
        agent.setStatus(dto.getStatus());
        LOGGER.info("添加代理商【{}】", dto.getName());
        return insert(agent);
    }

    private void checkAgentNameDup(String operatorName, Integer operatorId) {
        if (Objects.isNull(operatorId)) {//添加
            if (selectCount(new EntityWrapper<Agent>().eq("name", operatorName)
                    .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())) > 0) {
                LeaseException.throwSystemException(LeaseExceEnums.AGENT_NAME_DUP);
            }
        } else {//更新
            if (selectCount(new EntityWrapper<Agent>().eq("name", operatorName)
                    .ne("id", operatorId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())) > 0) {
                LeaseException.throwSystemException(LeaseExceEnums.AGENT_NAME_DUP);
            }
        }
    }

    private Integer acquireBindingAccount(Integer bindingExistAccount, Integer bindingAccountId, SysUserForAddDto sysUserForAddDto) {

        Integer bindingAccount;
        //绑定已有系统账号
        if (Objects.equals(ExistEnum.EXIST.getCode(), bindingExistAccount)) {
            checkIfBinded(bindingAccountId);
            bindingAccount = bindingAccountId;
        } else {
            //未绑定账号
            assertNotNull(sysUserForAddDto);
            bindingAccount = sysUserService.add(sysUserForAddDto);
            LOGGER.info("add sysuser id {} for agent", bindingAccount);
        }

        assertNotNull(bindingAccount);

        return bindingAccount;
    }

    private void checkIfBinded(Integer bindingAccountId) {
        if (selectCount(new EntityWrapper<Agent>().eq("sys_account_id", bindingAccountId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())) > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.ALREADY_BINDED_OPERATOR);
        }
    }

    @Override
    public List<Integer> resolveBindAccount() {
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Agent> agents = selectList(new EntityWrapper<Agent>().in("sys_user_id", sysUserService.resolveSysUserAllSubAdminIds(current))
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (CollectionUtils.isNotEmpty(agents)) {
            return agents.stream().map(Agent::getSysAccountId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void assertNotNull(Object obj) {
        if (Objects.isNull(obj)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
    }

    private Map<Integer, Resolver> statusMap = new HashMap<>();

    interface Resolver {
        Integer resolve(Integer agentAccountId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //待分配 --> 暂停
        statusMap.put(StatusType.NEED_TO_ASSIGN.getCode(), agentAccountId -> StatusType.SUSPENDED.getCode());

        //正常 --> 暂停
        statusMap.put(StatusType.OPERATING.getCode(), agentAccountId -> StatusType.SUSPENDED.getCode());

        //暂停 --> 正常, 待分配
        statusMap.put(StatusType.SUSPENDED.getCode(), agentAccountId -> {
            SysUser agentAccount = sysUserService.selectById(agentAccountId);
            if (Objects.nonNull(agentAccount)) {
                //判断该代理商下是否有设备
                List<Integer> ownerIds = sysUserService.resolveSysUserAllSubIds(sysUserService.getSysUserOwner(agentAccount));
                DeviceQueryDto query = new DeviceQueryDto();
                query.setAccessableOwnerIds(ownerIds);
                int count = deviceService.selectCount(QueryResolverUtils.parse(query, new EntityWrapper<>()));
                return count > 0 ? StatusType.OPERATING.getCode() : StatusType.NEED_TO_ASSIGN.getCode();
            }
            return null;
        });
    }

    @Override
    public Agent getAgentBySysAccountId(Integer sysAccountId) {
        EntityWrapper<Agent> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sys_account_id", sysAccountId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        return selectOne(entityWrapper);
    }

    @Override
    public List<Agent> getAgentByCreateId(Integer sysUserId) {
        if (ParamUtil.isNullOrEmptyOrZero(sysUserId)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        EntityWrapper<Agent> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sys_user_id", sysUserId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        return selectList(entityWrapper);
    }

    @Override
    public String delete(List<Integer> ids) {
        SysUser current = sysUserService.getCurrentUser();
        List<Integer> userIds = sysUserService.resolveOwnerAccessableUserIds(current);
        List<String> fails = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        List<Agent> agents = selectList(new EntityWrapper<Agent>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).in("id", ids));
        Integer sysAccountId;
        for (Agent agent : agents) {
            sysAccountId = agent.getSysAccountId();
            //判断用户是否拥有删除收费模式的权限，能删除在列表看到的所有数据，防止调用接口乱删
            if (userIds.contains(agent.getSysUserId())) {
                int num = deviceService.selectCount(new EntityWrapper<Device>().eq("owner_id", sysAccountId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
                EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("sys_user_id", sysAccountId).or().eq("parent_admin_id", sysAccountId);
                entityWrapper.andNew("is_deleted = 0");
                int sonNum = sysUserService.selectCount(entityWrapper);
                if (num <= 0 && sonNum <= 0) {
                    agent.setUtime(new Date());
                    agent.setIsDeleted(DeleteStatus.DELETED.getCode());
                    updateById(agent);
                    //删除代理商的系统账号
                    sysUserService.delete(Collections.singletonList(agent.getSysAccountId()));
                } else {
                    fails.add(agent.getName());
                }
            } else {
                fails.add(agent.getName());
            }

        }
        switch (fails.size()) {
            case 0:
                sb.append("删除成功");
                break;
            case 1:
                sb.append("您欲删除的代理商[" + fails.get(0) + "]已关联设备或下级，请先解绑设备或转移下级。");
                break;
            case 2:
                sb.append("您欲删除的代理商[" + fails.get(0) + "],[" + fails.get(1) + "]已关联设备或下级，请先解绑设备或转移下级。");
                break;
            default:
                sb.append("您欲删除的代理商[" + fails.get(0) + "],[" + fails.get(1) + "]等已关联设备或下级，请先解绑设备或转移下级。");
                break;
        }
        return sb.toString();
    }

    @Override
    public Agent selectById(Integer id) {
        return selectOne(new EntityWrapper<Agent>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }


    @Override
    public boolean isAgent(Integer sysUserId) {
        Wrapper<Agent> wrapper = new EntityWrapper();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        wrapper.eq("sys_account_id", sysUserId);
        return selectCount(wrapper) > 0;
    }


    @Override
    public List<AgentExportResultDto> importExcel(List<AgentExcelTemplate> validData) {
        if (CollectionUtils.isEmpty(validData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        LOGGER.info("待导入的数据共:{}条", validData.size());
        List<AgentExportResultDto> resultDtoList = new ArrayList<>();
        List<String> failMacList = new LinkedList<>();
        List<Agent> forUpdateList = new LinkedList<>();
        Date now = new Date();
        for (AgentExcelTemplate data : validData) {
            // 防止导入时文件格式为数值，将科学计数法转为数值
            String name = data.getName().trim();

            // 查看导入文件是否有重复数据
            if (!resolveDate(validData, data)) {
                AgentExportResultDto dto = new AgentExportResultDto();
                dto.setName(name);
                failMacList.add(data.getName());
                dto.setReason(DeviceExPortFailType.FILA_DATA_DUPLICATION.getDesc());
                resultDtoList.add(dto);
                continue;
            }
            Agent agent = resolveOne(resultDtoList, data, now);
            if (agent == null) {
                failMacList.add(data.getName());
            } else {
                forUpdateList.add(agent);
            }
        }

        if (forUpdateList.size() > 0) {
            insertBatch(forUpdateList);
        }
        LOGGER.info("已导入{}条设备数据", forUpdateList.size());
        if (failMacList.size() > 0) {
            throwImportFailException(failMacList);
        }
        return resultDtoList;
    }

    private void throwImportFailException(List<String> failMacList) {
        LOGGER.info("======>>>>导入失败的产品名称：" + failMacList.toString());
    }

    private boolean resolveDate(List<AgentExcelTemplate> validData, AgentExcelTemplate data) {
        List<AgentExcelTemplate> origin = new ArrayList<>();
        origin.addAll(validData);
        origin.remove(data);
        for (AgentExcelTemplate template : origin) {
            template.setName(template.getName());
            if (template.getName().equalsIgnoreCase(data.getName())) {
                return false;
            }

        }
        return true;
    }

    private Agent resolveOne(List<AgentExportResultDto> resultDto, AgentExcelTemplate data, Date now) {
        String name = data.getName().trim();
        Agent agents = getDeviceLaunchAreaByName(name);
        AgentExportResultDto dto = new AgentExportResultDto();
        dto.setName(data.getName());

        if (null != agents) {
            dto.setReason(LeaseExceEnums.PRODUCT_IS_EXISTS.getMessage());
            resultDto.add(dto);
            return null;
        }
        else {

            SysUser current = sysUserService.getCurrentUserOwner();
            Agent agent = new Agent();
            agent.setCtime(now);
            agent.setUtime(now);
            agent.setSysUserId(current.getId());
            agent.setSysUserName(current.getUsername());
            agent.setStatus(0);

            agent.setProvince(data.getProvince());
            agent.setCity(data.getCity());
            agent.setArea(data.getArea());
            agent.setAddress(data.getAddress());
            agent.setName(name);

            agent.setIsDeleted(BooleanEnum.FALSE.getCode());
            return agent;
        }
    }

    private Agent getDeviceLaunchAreaByName(String name){
        return selectOne(new EntityWrapper<Agent>().eq("name", name));
    }

}
