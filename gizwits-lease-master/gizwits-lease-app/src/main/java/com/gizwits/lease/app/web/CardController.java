package com.gizwits.lease.app.web;

import com.gizwits.boot.base.BaseController;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.RequestObject;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.lease.card.dto.*;
import com.gizwits.lease.card.service.CardService;
import com.gizwits.lease.enums.UserCardStatus;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api("卡券")
@RestController
@RequestMapping("/app/card")
public class CardController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @ApiOperation(value = "查询已投放的卡券列表接口")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/list/store")
    public ResponseObject<List<UserCardDto>> getDispatchedCardList(@RequestBody @Valid RequestObject<DispatchedCardQueryDto> requestObject) {
        User user = userService.getCurrentUser();
        return success(cardService.getDispatchedCardList(user, requestObject.getData()));
    }

    @ApiOperation(value = "APP领取卡券接口")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/receive")
    public ResponseObject receiveCard(@RequestBody @Valid RequestObject<ReceiveCardDto> requestObject) {
        User user = userService.getCurrentUser();
        cardService.receiveCardInApp(user, requestObject.getData());
        return success();
    }

    @ApiOperation(value = "查询用户选择用于订单的卡券列表接口")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/list/choose")
    public ResponseObject<UserCardListDto> getChooseCardList(@RequestBody @Valid RequestObject<UserCardQueryDto> requestObject) {
        User user = userService.getCurrentUser();
        return success(cardService.getUserCardListForConsume(user, requestObject.getData()));
    }

    @ApiOperation(value = "查询用户未使用的卡券接口")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/list/user")
    public ResponseObject<List<UserCardDto>> getUserCardList(@RequestBody @Valid RequestObject requestObject) {
        User user = userService.getCurrentUser();
        return success(cardService.getUserCardList(user, UserCardStatus.NORMAL));
    }

    @ApiOperation(value = "查询用户已使用的卡券接口")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/list/user/consumed")
    public ResponseObject<List<UserCardDto>> getUserConsumedCardList(@RequestBody @Valid RequestObject requestObject) {
        User user = userService.getCurrentUser();
        return success(cardService.getUserCardList(user, UserCardStatus.CONSUMED));
    }

    @ApiOperation(value = "查询用户无效的卡券接口")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/list/user/invalid")
    public ResponseObject<List<UserCardDto>> getUserInvalidCardList(@RequestBody @Valid RequestObject requestObject) {
        User user = userService.getCurrentUser();
        return success(cardService.getUserCardList(user, UserCardStatus.INVALID));
    }

    @ApiOperation(value = "获取微信卡券接口签名")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/sign")
    public ResponseObject<CardSignDto> getCardSign(@RequestBody @Valid RequestObject<CardIdDto> requestObject) {
        User user = userService.getCurrentUser();
        return success(cardService.getCardSign(user, requestObject.getData()));
    }

    /* 在下单接口(/app/order/consume/order)处理
    @ApiOperation(value = "订单使用卡券接口")
    @ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
    @PostMapping(value = "/consume")
    public ResponseObject<AppOrderVo> consumeCard(@RequestBody @Valid RequestObject<CardConsumeDto> requestObject) {
        User user = userService.getCurrentUser();
        cardService.consumeCard(user, requestObject.getData());
        return success();
    }
    */

}
