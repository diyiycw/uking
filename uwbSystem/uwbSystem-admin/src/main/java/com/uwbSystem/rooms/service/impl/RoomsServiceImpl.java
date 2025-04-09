package com.uwbSystem.rooms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uwbSystem.rooms.mapper.RoomsMapper;
import com.uwbSystem.rooms.domain.Rooms;
import com.uwbSystem.rooms.service.IRoomsService;

/**
 * 房间Service业务层处理
 * 
 * @author zyt
 * @date 2025-02-08
 */
@Service
public class RoomsServiceImpl implements IRoomsService 
{
    @Autowired
    private RoomsMapper roomsMapper;

    /**
     * 查询房间
     * 
     * @param roomId 房间主键
     * @return 房间
     */
    @Override
    public Rooms selectRoomsByRoomId(Integer roomId)
    {
        return roomsMapper.selectRoomsByRoomId(roomId);
    }

    /**
     * 查询房间列表
     * 
     * @param rooms 房间
     * @return 房间
     */
    @Override
    public List<Rooms> selectRoomsList(Rooms rooms)
    {
        return roomsMapper.selectRoomsList(rooms);
    }

    /**
     * 新增房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    @Override
    public int insertRooms(Rooms rooms)
    {
        return roomsMapper.insertRooms(rooms);
    }

    /**
     * 修改房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    @Override
    public int updateRooms(Rooms rooms)
    {
        return roomsMapper.updateRooms(rooms);
    }

    /**
     * 批量删除房间
     * 
     * @param roomIds 需要删除的房间主键
     * @return 结果
     */
    @Override
    public int deleteRoomsByRoomIds(Integer[] roomIds)
    {
        return roomsMapper.deleteRoomsByRoomIds(roomIds);
    }

    /**
     * 删除房间信息
     * 
     * @param roomId 房间主键
     * @return 结果
     */
    @Override
    public int deleteRoomsByRoomId(Integer roomId)
    {
        return roomsMapper.deleteRoomsByRoomId(roomId);
    }
}
