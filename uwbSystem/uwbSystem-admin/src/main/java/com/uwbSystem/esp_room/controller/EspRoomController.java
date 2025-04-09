package com.uwbSystem.esp_room.controller;

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
import com.uwbSystem.esp_room.domain.EspRoom;
import com.uwbSystem.esp_room.service.IEspRoomService;
import com.uwbSystem.common.utils.poi.ExcelUtil;
import com.uwbSystem.common.core.page.TableDataInfo;

/**
 * 设备房间Controller
 * 
 * @author zyt
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/esp_room/esp_room")
public class EspRoomController extends BaseController
{
    @Autowired
    private IEspRoomService espRoomService;

    /**
     * 查询设备房间列表
     */
    @PreAuthorize("@ss.hasPermi('esp_room:esp_room:list')")
    @GetMapping("/list")
    public TableDataInfo list(EspRoom espRoom)
    {
        startPage();
        List<EspRoom> list = espRoomService.selectEspRoomList(espRoom);
        return getDataTable(list);
    }

    /**
     * 导出设备房间列表
     */
    @PreAuthorize("@ss.hasPermi('esp_room:esp_room:export')")
    @Log(title = "设备房间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EspRoom espRoom)
    {
        List<EspRoom> list = espRoomService.selectEspRoomList(espRoom);
        ExcelUtil<EspRoom> util = new ExcelUtil<EspRoom>(EspRoom.class);
        util.exportExcel(response, list, "设备房间数据");
    }

    /**
     * 获取设备房间详细信息
     */
    @PreAuthorize("@ss.hasPermi('esp_room:esp_room:query')")
    @GetMapping(value = "/{espId}")
    public AjaxResult getInfo(@PathVariable("espId") Integer espId)
    {
        return success(espRoomService.selectEspRoomByEspId(espId));
    }

    /**
     * 新增设备房间
     */
    @PreAuthorize("@ss.hasPermi('esp_room:esp_room:add')")
    @Log(title = "设备房间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EspRoom espRoom)
    {
        return toAjax(espRoomService.insertEspRoom(espRoom));
    }

    /**
     * 修改设备房间
     */
    @PreAuthorize("@ss.hasPermi('esp_room:esp_room:edit')")
    @Log(title = "设备房间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EspRoom espRoom)
    {
        return toAjax(espRoomService.updateEspRoom(espRoom));
    }

    /**
     * 删除设备房间
     */
    @PreAuthorize("@ss.hasPermi('esp_room:esp_room:remove')")
    @Log(title = "设备房间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{espIds}")
    public AjaxResult remove(@PathVariable Integer[] espIds)
    {
        return toAjax(espRoomService.deleteEspRoomByEspIds(espIds));
    }
}
