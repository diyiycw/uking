package com.uwbSystem.esps.controller;

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
import com.uwbSystem.esps.domain.Esps;
import com.uwbSystem.esps.service.IEspsService;
import com.uwbSystem.common.utils.poi.ExcelUtil;
import com.uwbSystem.common.core.page.TableDataInfo;

/**
 * ESP盒子Controller
 * 
 * @author zyt
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/esps/esps")
public class EspsController extends BaseController
{
    @Autowired
    private IEspsService espsService;

    /**
     * 查询ESP盒子列表
     */
    @PreAuthorize("@ss.hasPermi('esps:esps:list')")
    @GetMapping("/list")
    public TableDataInfo list(Esps esps)
    {
        startPage();
        List<Esps> list = espsService.selectEspsList(esps);
        return getDataTable(list);
    }

    /**
     * 导出ESP盒子列表
     */
    @PreAuthorize("@ss.hasPermi('esps:esps:export')")
    @Log(title = "ESP盒子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Esps esps)
    {
        List<Esps> list = espsService.selectEspsList(esps);
        ExcelUtil<Esps> util = new ExcelUtil<Esps>(Esps.class);
        util.exportExcel(response, list, "ESP盒子数据");
    }

    /**
     * 获取ESP盒子详细信息
     */
    @PreAuthorize("@ss.hasPermi('esps:esps:query')")
    @GetMapping(value = "/{espId}")
    public AjaxResult getInfo(@PathVariable("espId") Integer espId)
    {
        return success(espsService.selectEspsByEspId(espId));
    }

    /**
     * 新增ESP盒子
     */
    @PreAuthorize("@ss.hasPermi('esps:esps:add')")
    @Log(title = "ESP盒子", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Esps esps)
    {
        return toAjax(espsService.insertEsps(esps));
    }

    /**
     * 修改ESP盒子
     */
    @PreAuthorize("@ss.hasPermi('esps:esps:edit')")
    @Log(title = "ESP盒子", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Esps esps)
    {
        return toAjax(espsService.updateEsps(esps));
    }

    /**
     * 删除ESP盒子
     */
    @PreAuthorize("@ss.hasPermi('esps:esps:remove')")
    @Log(title = "ESP盒子", businessType = BusinessType.DELETE)
	@DeleteMapping("/{espIds}")
    public AjaxResult remove(@PathVariable Integer[] espIds)
    {
        return toAjax(espsService.deleteEspsByEspIds(espIds));
    }
}
