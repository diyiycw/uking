package com.uwbSystem.radar_records.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uwbSystem.radar_records.mapper.RadarRecordsMapper;
import com.uwbSystem.radar_records.domain.RadarRecords;
import com.uwbSystem.radar_records.service.IRadarRecordsService;

/**
 * 雷达记录Service业务层处理
 * 
 * @author zyt
 * @date 2025-02-08
 */
@Service
public class RadarRecordsServiceImpl implements IRadarRecordsService 
{
    @Autowired
    private RadarRecordsMapper radarRecordsMapper;

    /**
     * 查询雷达记录
     * 
     * @param espId 雷达记录主键
     * @return 雷达记录
     */
    @Override
    public RadarRecords selectRadarRecordsByEspId(Integer espId)
    {
        return radarRecordsMapper.selectRadarRecordsByEspId(espId);
    }

    /**
     * 查询雷达记录列表
     * 
     * @param radarRecords 雷达记录
     * @return 雷达记录
     */
    @Override
    public List<RadarRecords> selectRadarRecordsList(RadarRecords radarRecords)
    {
        return radarRecordsMapper.selectRadarRecordsList(radarRecords);
    }

    /**
     * 新增雷达记录
     * 
     * @param radarRecords 雷达记录
     * @return 结果
     */
    @Override
    public int insertRadarRecords(RadarRecords radarRecords)
    {
        return radarRecordsMapper.insertRadarRecords(radarRecords);
    }

    /**
     * 修改雷达记录
     * 
     * @param radarRecords 雷达记录
     * @return 结果
     */
    @Override
    public int updateRadarRecords(RadarRecords radarRecords)
    {
        return radarRecordsMapper.updateRadarRecords(radarRecords);
    }

    /**
     * 批量删除雷达记录
     * 
     * @param espIds 需要删除的雷达记录主键
     * @return 结果
     */
    @Override
    public int deleteRadarRecordsByEspIds(Integer[] espIds)
    {
        return radarRecordsMapper.deleteRadarRecordsByEspIds(espIds);
    }

    /**
     * 删除雷达记录信息
     * 
     * @param espId 雷达记录主键
     * @return 结果
     */
    @Override
    public int deleteRadarRecordsByEspId(Integer espId)
    {
        return radarRecordsMapper.deleteRadarRecordsByEspId(espId);
    }
}
