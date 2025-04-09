package com.uwbSystem.room_user.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uwbSystem.room_user.mapper.RoomUserMapper;
import com.uwbSystem.room_user.domain.RoomUser;
import com.uwbSystem.room_user.service.IRoomUserService;

/**
 * 房间用户Service业务层处理
 * 
 * @author zyt
 * @date 2025-02-08
 */
@Service
public class RoomUserServiceImpl implements IRoomUserService 
{
    @Autowired
    private RoomUserMapper roomUserMapper;

    /**
     * 查询房间用户
     * 
     * @param roomId 房间用户主键
     * @return 房间用户
     */
    @Override
    public RoomUser selectRoomUserByRoomId(Integer roomId)
    {
        return roomUserMapper.selectRoomUserByRoomId(roomId);
    }

    /**
     * 查询房间用户列表
     * 
     * @param roomUser 房间用户
     * @return 房间用户
     */
    @Override
    public List<RoomUser> selectRoomUserList(RoomUser roomUser)
    {
        return roomUserMapper.selectRoomUserList(roomUser);
    }

    /**
     * 新增房间用户
     * 
     * @param roomUser 房间用户
     * @return 结果
     */
    @Override
    public int insertRoomUser(RoomUser roomUser)
    {
        return roomUserMapper.insertRoomUser(roomUser);
    }

    /**
     * 修改房间用户
     * 
     * @param roomUser 房间用户
     * @return 结果
     */
    @Override
    public int updateRoomUser(RoomUser roomUser)
    {
        return roomUserMapper.updateRoomUser(roomUser);
    }

    /**
     * 批量删除房间用户
     * 
     * @param roomIds 需要删除的房间用户主键
     * @return 结果
     */
    @Override
    public int deleteRoomUserByRoomIds(Integer[] roomIds)
    {
        return roomUserMapper.deleteRoomUserByRoomIds(roomIds);
    }

    /**
     * 删除房间用户信息
     * 
     * @param roomId 房间用户主键
     * @return 结果
     */
    @Override
    public int deleteRoomUserByRoomId(Integer roomId)
    {
        return roomUserMapper.deleteRoomUserByRoomId(roomId);
    }
}
