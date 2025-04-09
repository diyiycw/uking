package com.uwbSystem.room_user.service;

import java.util.List;
import com.uwbSystem.room_user.domain.RoomUser;

/**
 * 房间用户Service接口
 * 
 * @author zyt
 * @date 2025-02-08
 */
public interface IRoomUserService 
{
    /**
     * 查询房间用户
     * 
     * @param roomId 房间用户主键
     * @return 房间用户
     */
    public RoomUser selectRoomUserByRoomId(Integer roomId);

    /**
     * 查询房间用户列表
     * 
     * @param roomUser 房间用户
     * @return 房间用户集合
     */
    public List<RoomUser> selectRoomUserList(RoomUser roomUser);

    /**
     * 新增房间用户
     * 
     * @param roomUser 房间用户
     * @return 结果
     */
    public int insertRoomUser(RoomUser roomUser);

    /**
     * 修改房间用户
     * 
     * @param roomUser 房间用户
     * @return 结果
     */
    public int updateRoomUser(RoomUser roomUser);

    /**
     * 批量删除房间用户
     * 
     * @param roomIds 需要删除的房间用户主键集合
     * @return 结果
     */
    public int deleteRoomUserByRoomIds(Integer[] roomIds);

    /**
     * 删除房间用户信息
     * 
     * @param roomId 房间用户主键
     * @return 结果
     */
    public int deleteRoomUserByRoomId(Integer roomId);
}
