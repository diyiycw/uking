package com.uwbSystem.radar_records.service;

import java.util.List;
import com.uwbSystem.radar_records.domain.RadarRecords;

/**
 * 雷达记录Service接口
 * 
 * @author zyt
 * @date 2025-02-08
 */
public interface IRadarRecordsService 
{
    /**
     * 查询雷达记录
     * 
     * @param espId 雷达记录主键
     * @return 雷达记录
     */
    public RadarRecords selectRadarRecordsByEspId(Integer espId);

    /**
     * 查询雷达记录列表
     * 
     * @param radarRecords 雷达记录
     * @return 雷达记录集合
     */
    public List<RadarRecords> selectRadarRecordsList(RadarRecords radarRecords);

    /**
     * 新增雷达记录
     * 
     * @param radarRecords 雷达记录
     * @return 结果
     */
    public int insertRadarRecords(RadarRecords radarRecords);

    /**
     * 修改雷达记录
     * 
     * @param radarRecords 雷达记录
     * @return 结果
     */
    public int updateRadarRecords(RadarRecords radarRecords);

    /**
     * 批量删除雷达记录
     * 
     * @param espIds 需要删除的雷达记录主键集合
     * @return 结果
     */
    public int deleteRadarRecordsByEspIds(Integer[] espIds);

    /**
     * 删除雷达记录信息
     * 
     * @param espId 雷达记录主键
     * @return 结果
     */
    public int deleteRadarRecordsByEspId(Integer espId);
}
