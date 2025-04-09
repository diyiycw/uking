package com.uwbSystem.esp_room.service;

import java.util.List;
import com.uwbSystem.esp_room.domain.EspRoom;

/**
 * 设备房间Service接口
 * 
 * @author zyt
 * @date 2025-02-08
 */
public interface IEspRoomService 
{
    /**
     * 查询设备房间
     * 
     * @param espId 设备房间主键
     * @return 设备房间
     */
    public EspRoom selectEspRoomByEspId(Integer espId);

    /**
     * 查询设备房间列表
     * 
     * @param espRoom 设备房间
     * @return 设备房间集合
     */
    public List<EspRoom> selectEspRoomList(EspRoom espRoom);

    /**
     * 新增设备房间
     * 
     * @param espRoom 设备房间
     * @return 结果
     */
    public int insertEspRoom(EspRoom espRoom);

    /**
     * 修改设备房间
     * 
     * @param espRoom 设备房间
     * @return 结果
     */
    public int updateEspRoom(EspRoom espRoom);

    /**
     * 批量删除设备房间
     * 
     * @param espIds 需要删除的设备房间主键集合
     * @return 结果
     */
    public int deleteEspRoomByEspIds(Integer[] espIds);

    /**
     * 删除设备房间信息
     * 
     * @param espId 设备房间主键
     * @return 结果
     */
    public int deleteEspRoomByEspId(Integer espId);
}
