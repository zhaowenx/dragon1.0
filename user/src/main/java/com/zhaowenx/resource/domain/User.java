package com.zhaowenx.resource.domain;

/**
 * Created by zhaowenx on 2019/1/11.
 */
public class User {

    /**
     * USER
     * 用户表
     */
    private Integer id;//用户ID
    private String userName;//用户名
    private String passWord;//登录密码(经过3Des加密)
    private String realName;//真实姓名
    private String phone;//手机
    private String email;//邮箱
    private String weixin;//微信
    private String qqNumber;//qq
    private Integer role;//是否超级用户，0：非超级用户，1：超级用户
    private Integer loginTimes;//登录次数
    private String lastLoginDate;//最后一次登录时间
    private Integer sex;//性别 1:男，0:女
    private String createTime;//创建时间
    private String updateTime;//更新时间
    private String validateCode;//验证码
    private String idCard;//身份证号码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", weixin='" + weixin + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                ", role=" + role +
                ", loginTimes=" + loginTimes +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", sex=" + sex +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", validateCode='" + validateCode + '\'' +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
