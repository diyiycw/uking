package com.uwbSystem.room_user.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uwbSystem.common.annotation.Excel;
import com.uwbSystem.common.core.domain.BaseEntity;

/**
 * 房间用户对象 room_user
 * 
 * @author zyt
 * @date 2025-02-08
 */
public class RoomUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 房间id */
    private Integer roomId;

    /** 用户id */
    private Integer userId;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    public void setRoomId(Integer roomId) 
    {
        this.roomId = roomId;
    }

    public Integer getRoomId() 
    {
        return roomId;
    }
    public void setUserId(Integer userId) 
    {
        this.userId = userId;
    }

    public Integer getUserId() 
    {
        return userId;
    }
    public void setRegisterTime(Date registerTime) 
    {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() 
    {
        return registerTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roomId", getRoomId())
            .append("userId", getUserId())
            .append("registerTime", getRegisterTime())
            .toString();
    }
}
