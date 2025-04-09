package com.uwbSystem.esp_room.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uwbSystem.common.annotation.Excel;
import com.uwbSystem.common.core.domain.BaseEntity;

/**
 * 设备房间对象 esp_room
 * 
 * @author zyt
 * @date 2025-02-08
 */
public class EspRoom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ESP号 */
    @Excel(name = "ESP号")
    private Integer espId;

    /** 房间id */
    @Excel(name = "房间id")
    private Integer roomId;

    public void setEspId(Integer espId) 
    {
        this.espId = espId;
    }

    public Integer getEspId() 
    {
        return espId;
    }
    public void setRoomId(Integer roomId) 
    {
        this.roomId = roomId;
    }

    public Integer getRoomId() 
    {
        return roomId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("espId", getEspId())
            .append("roomId", getRoomId())
            .toString();
    }
}
