package com.uwbSystem.radar_records.controller;

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
import com.uwbSystem.radar_records.domain.RadarRecords;
import com.uwbSystem.radar_records.service.IRadarRecordsService;
import com.uwbSystem.common.utils.poi.ExcelUtil;
import com.uwbSystem.common.core.page.TableDataInfo;

/**
 * 雷达记录Controller
 * 
 * @author zyt
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/radar_records/radar_records")
public class RadarRecordsController extends BaseController
{
    @Autowired
    private IRadarRecordsService radarRecordsService;

    /**
     * 查询雷达记录列表
     */
    @PreAuthorize("@ss.hasPermi('radar_records:radar_records:list')")
    @GetMapping("/list")
    public TableDataInfo list(RadarRecords radarRecords)
    {
        startPage();
        List<RadarRecords> list = radarRecordsService.selectRadarRecordsList(radarRecords);
        return getDataTable(list);
    }

    /**
     * 导出雷达记录列表
     */
    @PreAuthorize("@ss.hasPermi('radar_records:radar_records:export')")
    @Log(title = "雷达记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RadarRecords radarRecords)
    {
        List<RadarRecords> list = radarRecordsService.selectRadarRecordsList(radarRecords);
        ExcelUtil<RadarRecords> util = new ExcelUtil<RadarRecords>(RadarRecords.class);
        util.exportExcel(response, list, "雷达记录数据");
    }

    /**
     * 获取雷达记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('radar_records:radar_records:query')")
    @GetMapping(value = "/{espId}")
    public AjaxResult getInfo(@PathVariable("espId") Integer espId)
    {
        return success(radarRecordsService.selectRadarRecordsByEspId(espId));
    }

    /**
     * 新增雷达记录
     */
    @PreAuthorize("@ss.hasPermi('radar_records:radar_records:add')")
    @Log(title = "雷达记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RadarRecords radarRecords)
    {
        return toAjax(radarRecordsService.insertRadarRecords(radarRecords));
    }

    /**
     * 修改雷达记录
     */
    @PreAuthorize("@ss.hasPermi('radar_records:radar_records:edit')")
    @Log(title = "雷达记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RadarRecords radarRecords)
    {
        return toAjax(radarRecordsService.updateRadarRecords(radarRecords));
    }

    /**
     * 删除雷达记录
     */
    @PreAuthorize("@ss.hasPermi('radar_records:radar_records:remove')")
    @Log(title = "雷达记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{espIds}")
    public AjaxResult remove(@PathVariable Integer[] espIds)
    {
        return toAjax(radarRecordsService.deleteRadarRecordsByEspIds(espIds));
    }
}
