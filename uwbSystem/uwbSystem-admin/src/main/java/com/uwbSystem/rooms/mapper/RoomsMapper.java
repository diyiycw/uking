package com.uwbSystem.rooms.mapper;

import java.util.List;
import com.uwbSystem.rooms.domain.Rooms;

/**
 * 房间Mapper接口
 * 
 * @author zyt
 * @date 2025-02-08
 */
public interface RoomsMapper 
{
    /**
     * 查询房间
     * 
     * @param roomId 房间主键
     * @return 房间
     */
    public Rooms selectRoomsByRoomId(Integer roomId);

    /**
     * 查询房间列表
     * 
     * @param rooms 房间
     * @return 房间集合
     */
    public List<Rooms> selectRoomsList(Rooms rooms);

    /**
     * 新增房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    public int insertRooms(Rooms rooms);

    /**
     * 修改房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    public int updateRooms(Rooms rooms);

    /**
     * 删除房间
     * 
     * @param roomId 房间主键
     * @return 结果
     */
    public int deleteRoomsByRoomId(Integer roomId);

    /**
     * 批量删除房间
     * 
     * @param roomIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRoomsByRoomIds(Integer[] roomIds);
}
