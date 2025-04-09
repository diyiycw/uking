package com.uwbSystem.room_user.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uwbSystem.common.annotation.Log;
import com.uwbSystem.common.core.controller.BaseController;
import com.uwbSystem.common.core.domain.AjaxResult;
import com.uwbSystem.common.enums.BusinessType;
import com.uwbSystem.room_user.domain.RoomUser;
import com.uwbSystem.room_user.service.IRoomUserService;
import com.uwbSystem.common.utils.poi.ExcelUtil;
import com.uwbSystem.common.core.page.TableDataInfo;

/**
 * 房间用户Controller
 * 
 * @author zyt
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/room_user/room_user")
public class RoomUserController extends BaseController
{
    @Autowired
    private IRoomUserService roomUserService;

    /**
     * 查询房间用户列表
     */
    @PreAuthorize("@ss.hasPermi('room_user:room_user:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomUser roomUser)
    {
        startPage();
        List<RoomUser> list = roomUserService.selectRoomUserList(roomUser);
        return getDataTable(list);
    }

    /**
     * 导出房间用户列表
     */
    @PreAuthorize("@ss.hasPermi('room_user:room_user:export')")
    @Log(title = "房间用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomUser roomUser)
    {
        List<RoomUser> list = roomUserService.selectRoomUserList(roomUser);
        ExcelUtil<RoomUser> util = new ExcelUtil<RoomUser>(RoomUser.class);
        util.exportExcel(response, list, "房间用户数据");
    }

    /**
     * 获取房间用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('room_user:room_user:query')")
    @GetMapping(value = "/{roomId}")
    public AjaxResult getInfo(@PathVariable("roomId") Integer roomId)
    {
        return success(roomUserService.selectRoomUserByRoomId(roomId));
    }

    /**
     * 新增房间用户
     */
    @PreAuthorize("@ss.hasPermi('room_user:room_user:add')")
    @Log(title = "房间用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomUser roomUser)
    {
        return toAjax(roomUserService.insertRoomUser(roomUser));
    }

    /**
     * 修改房间用户
     */
    @PreAuthorize("@ss.hasPermi('room_user:room_user:edit')")
    @Log(title = "房间用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoomUser roomUser)
    {
        return toAjax(roomUserService.updateRoomUser(roomUser));
    }

    /**
     * 删除房间用户
     */
    @PreAuthorize("@ss.hasPermi('room_user:room_user:remove')")
    @Log(title = "房间用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roomIds}")
    public AjaxResult remove(@PathVariable Integer[] roomIds)
    {
        return toAjax(roomUserService.deleteRoomUserByRoomIds(roomIds));
    }
}
