package com.uwbSystem.radar_records.mapper;

import java.util.List;
import com.uwbSystem.radar_records.domain.RadarRecords;

/**
 * 雷达记录Mapper接口
 * 
 * @author zyt
 * @date 2025-02-08
 */
public interface RadarRecordsMapper 
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
     * 删除雷达记录
     * 
     * @param espId 雷达记录主键
     * @return 结果
     */
    public int deleteRadarRecordsByEspId(Integer espId);

    /**
     * 批量删除雷达记录
     * 
     * @param espIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRadarRecordsByEspIds(Integer[] espIds);
}
