package com.gizwits.lease.manager.web;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.dto.SysUserExtForAddDto;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.common.version.DefaultVersion;
import com.gizwits.lease.common.version.Version;
import com.gizwits.lease.device.entity.dto.QueryForCreatorDto;
import com.gizwits.lease.manager.dto.*;
import com.gizwits.lease.manager.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * 代理商表 前端控制器
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@RestController
@EnableSwagger2
@Api(description = "经销商接口")
@RequestMapping("/agent")
public class AgentController extends BaseController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private SysUserService sysUserService;

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @PostMapping(value = "/add")
    public ResponseObject<Boolean> add(@RequestBody @Valid RequestObject<AgentForAddDto> requestObject) {
        return success(agentService.add(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "绑定系统帐号", notes = "绑定系统帐号", consumes = "application/json")
    @PostMapping(value = "/bind")
    public ResponseObject bind(@RequestBody @Valid RequestObject<AgentForBindDto> requestObject) {
        agentService.bind(requestObject.getData());
        return success();
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
    @PostMapping(value = "/list")
    @DefaultVersion(display = {"name","area", "statusDesc", "launchAreaCount", "deviceCount"})
    public ResponseObject<Page<AgentForListDto>> list(@RequestBody RequestObject<Pageable<AgentForQueryDto>> requestObject) {
        Pageable<AgentForQueryDto> pageable = requestObject.getData();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new AgentForQueryDto());
        }
        pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner()));
        return success(agentService.page(requestObject.getData()));
    }

    @Version(uri = "/list", version = "1.1", display = {"name", "agentLevel", "parentAgent", "statusDesc"})
    public ResponseObject<Page<AgentForListDto>> list2(@RequestBody RequestObject<Pageable<AgentForQueryDto>> requestObject) {
        Pageable<AgentForQueryDto> pageable = requestObject.getData();
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new AgentForQueryDto());
        }
        pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner()));
        return success(agentService.page(requestObject.getData()));
    }


    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "直接下级代理商", consumes = "application/json")
    @PostMapping("/children")
    public ResponseObject<Page<AgentForListDto>> children(@RequestBody RequestObject<Pageable<QueryForCreatorDto>> requestObject) {
        Pageable<QueryForCreatorDto> data = requestObject.getData();
        Pageable<AgentForQueryDto> pageable = new Pageable<>();
        BeanUtils.copyProperties(data, pageable, "query");
        pageable.setQuery(new AgentForQueryDto());
        if (Objects.isNull(data.getQuery())) {
            data.setQuery(new QueryForCreatorDto());
        }
        Integer creatorId = data.getQuery().getCreatorId();
        if (Objects.isNull(creatorId)) {
            //若前台没有传递代理商绑定的系统账号，则查询当前登录人直接创建的代理商
            pageable.getQuery().setAccessableUserIds(sysUserService.resolveOwnerAccessableUserIds(sysUserService.getCurrentUserOwner()));
        } else {
            SysUser sysUser = sysUserService.selectById(creatorId);
            if (sysUser.getIsAdmin().equals(SysUserType.NORMAL.getCode())){
                SysUser parent = sysUserService.selectById(sysUser.getParentAdminId());
                pageable.getQuery().setAccessableUserIds(sysUserService.resolveOwnerAccessableUserIds(sysUserService.getSysUserOwner(parent)));
            }else {
                pageable.getQuery().setAccessableUserIds(sysUserService.resolveOwnerAccessableUserIds(sysUserService.getSysUserOwner(sysUser)));
            }
        }
        return success(agentService.page(pageable));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "详情", consumes = "application/json")
    @PostMapping("/detail")
    public ResponseObject<AgentForDetailDto> detail(@RequestBody RequestObject<Integer> requestObject) {
        return success(agentService.detail(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
    @PostMapping(value = "/update")
    public ResponseObject<AgentForDetailDto> update(@RequestBody @Valid RequestObject<AgentForUpdateDto> requestObject) {
        AgentForUpdateDto dto = requestObject.getData();
        if (Objects.isNull(dto.getAccount())) {
            dto.setAccount(new AgentForUpdateDto.Account());
        }
        if (Objects.isNull(dto.getAccount().getExt())) {
            dto.getAccount().setExt(new SysUserExtForAddDto());
        }
        return success(agentService.update(dto));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "状态切换", consumes = "application/json")
    @PostMapping("/switch")
    public ResponseObject change(@RequestBody RequestObject<AgentForUpdateDto> requestObject) {
        return success(agentService.change(requestObject.getData()));
    }

    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @PostMapping(value = "/delete")
    public ResponseObject<String> delete(@RequestBody RequestObject<List<Integer>> requestObject) {

        return success(agentService.delete(requestObject.getData()));
    }

}
