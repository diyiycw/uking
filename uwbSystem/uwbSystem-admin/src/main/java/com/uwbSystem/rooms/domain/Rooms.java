package com.uwbSystem.rooms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uwbSystem.common.annotation.Excel;
import com.uwbSystem.common.core.domain.BaseEntity;

/**
 * 房间对象 rooms
 * 
 * @author zyt
 * @date 2025-02-08
 */
public class Rooms extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 房间id */
    private Integer roomId;

    /** 房间名 */
    @Excel(name = "房间名")
    private String roomName;

    public void setRoomId(Integer roomId) 
    {
        this.roomId = roomId;
    }

    public Integer getRoomId() 
    {
        return roomId;
    }
    public void setRoomName(String roomName) 
    {
        this.roomName = roomName;
    }

    public String getRoomName() 
    {
        return roomName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roomId", getRoomId())
            .append("roomName", getRoomName())
            .toString();
    }
}
