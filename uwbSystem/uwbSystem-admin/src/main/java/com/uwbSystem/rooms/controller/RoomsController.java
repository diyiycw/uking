package com.uwbSystem.rooms.controller;

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
import com.uwbSystem.rooms.domain.Rooms;
import com.uwbSystem.rooms.service.IRoomsService;
import com.uwbSystem.common.utils.poi.ExcelUtil;
import com.uwbSystem.common.core.page.TableDataInfo;

/**
 * 房间Controller
 * 
 * @author zyt
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/rooms/rooms")
public class RoomsController extends BaseController
{
    @Autowired
    private IRoomsService roomsService;

    /**
     * 查询房间列表
     */
    @PreAuthorize("@ss.hasPermi('rooms:rooms:list')")
    @GetMapping("/list")
    public TableDataInfo list(Rooms rooms)
    {
        startPage();
        List<Rooms> list = roomsService.selectRoomsList(rooms);
        return getDataTable(list);
    }

    /**
     * 导出房间列表
     */
    @PreAuthorize("@ss.hasPermi('rooms:rooms:export')")
    @Log(title = "房间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Rooms rooms)
    {
        List<Rooms> list = roomsService.selectRoomsList(rooms);
        ExcelUtil<Rooms> util = new ExcelUtil<Rooms>(Rooms.class);
        util.exportExcel(response, list, "房间数据");
    }

    /**
     * 获取房间详细信息
     */
    @PreAuthorize("@ss.hasPermi('rooms:rooms:query')")
    @GetMapping(value = "/{roomId}")
    public AjaxResult getInfo(@PathVariable("roomId") Integer roomId)
    {
        return success(roomsService.selectRoomsByRoomId(roomId));
    }

    /**
     * 新增房间
     */
    @PreAuthorize("@ss.hasPermi('rooms:rooms:add')")
    @Log(title = "房间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Rooms rooms)
    {
        return toAjax(roomsService.insertRooms(rooms));
    }

    /**
     * 修改房间
     */
    @PreAuthorize("@ss.hasPermi('rooms:rooms:edit')")
    @Log(title = "房间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Rooms rooms)
    {
        return toAjax(roomsService.updateRooms(rooms));
    }

    /**
     * 删除房间
     */
    @PreAuthorize("@ss.hasPermi('rooms:rooms:remove')")
    @Log(title = "房间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roomIds}")
    public AjaxResult remove(@PathVariable Integer[] roomIds)
    {
        return toAjax(roomsService.deleteRoomsByRoomIds(roomIds));
    }
}
