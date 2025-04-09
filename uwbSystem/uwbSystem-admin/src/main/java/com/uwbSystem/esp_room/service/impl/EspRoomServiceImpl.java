package com.uwbSystem.esp_room.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uwbSystem.esp_room.mapper.EspRoomMapper;
import com.uwbSystem.esp_room.domain.EspRoom;
import com.uwbSystem.esp_room.service.IEspRoomService;

/**
 * 设备房间Service业务层处理
 * 
 * @author zyt
 * @date 2025-02-08
 */
@Service
public class EspRoomServiceImpl implements IEspRoomService 
{
    @Autowired
    private EspRoomMapper espRoomMapper;

    /**
     * 查询设备房间
     * 
     * @param espId 设备房间主键
     * @return 设备房间
     */
    @Override
    public EspRoom selectEspRoomByEspId(Integer espId)
    {
        return espRoomMapper.selectEspRoomByEspId(espId);
    }

    /**
     * 查询设备房间列表
     * 
     * @param espRoom 设备房间
     * @return 设备房间
     */
    @Override
    public List<EspRoom> selectEspRoomList(EspRoom espRoom)
    {
        return espRoomMapper.selectEspRoomList(espRoom);
    }

    /**
     * 新增设备房间
     * 
     * @param espRoom 设备房间
     * @return 结果
     */
    @Override
    public int insertEspRoom(EspRoom espRoom)
    {
        return espRoomMapper.insertEspRoom(espRoom);
    }

    /**
     * 修改设备房间
     * 
     * @param espRoom 设备房间
     * @return 结果
     */
    @Override
    public int updateEspRoom(EspRoom espRoom)
    {
        return espRoomMapper.updateEspRoom(espRoom);
    }

    /**
     * 批量删除设备房间
     * 
     * @param espIds 需要删除的设备房间主键
     * @return 结果
     */
    @Override
    public int deleteEspRoomByEspIds(Integer[] espIds)
    {
        return espRoomMapper.deleteEspRoomByEspIds(espIds);
    }

    /**
     * 删除设备房间信息
     * 
     * @param espId 设备房间主键
     * @return 结果
     */
    @Override
    public int deleteEspRoomByEspId(Integer espId)
    {
        return espRoomMapper.deleteEspRoomByEspId(espId);
    }
}
