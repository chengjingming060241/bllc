package com.gizwits.boot.event;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.ApplicationEvent;

/**
 * Description:
 * User: yinhui
 * Date: 2018-03-15
 */
public class SysUserNameModifyEvent<E> extends ApplicationEvent {
    private static final long serialVersionUID = 3886029444529356205L;

    private E id;
    private String oldName;
    private String newName;
    private String newOpenId;
    @ApiModelProperty("支付宝收款账号")
    private String alipayAccount;

    @ApiModelProperty("支付宝收款姓名")
    private String alipayAccountName;

    private String shareBenefitType;

    public SysUserNameModifyEvent(Object source) {
        super(source);
    }

    public SysUserNameModifyEvent(Object source, E id,String newName, String newOpenId, String alipayAccount, String alipayAccountName, String shareBenefitType) {
        super(source);
        this.id = id;
        this.newName = newName;
        this.newOpenId = newOpenId;
        this.alipayAccount = alipayAccount;
        this.alipayAccountName = alipayAccountName;
        this.shareBenefitType = shareBenefitType;
    }

    public SysUserNameModifyEvent(Object source, E id, String oldName, String newName, String newOpenId) {
        super(source);
        this.id = id;
        this.oldName = oldName;
        this.newName = newName;
        this.newOpenId = newOpenId;
    }

    public SysUserNameModifyEvent(Object source, E id, String oldName, String newName) {
        super(source);
        this.id = id;
        this.oldName = oldName;
        this.newName = newName;
    }

    public E getId() {
        return id;
    }

    public void setId(E id) {
        this.id = id;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewOpenId() {
        return newOpenId;
    }

    public void setNewOpenId(String newOpenId) {
        this.newOpenId = newOpenId;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getAlipayAccountName() {
        return alipayAccountName;
    }

    public void setAlipayAccountName(String alipayAccountName) {
        this.alipayAccountName = alipayAccountName;
    }

    public String getShareBenefitType() {
        return shareBenefitType;
    }

    public void setShareBenefitType(String shareBenefitType) {
        this.shareBenefitType = shareBenefitType;
    }
}

