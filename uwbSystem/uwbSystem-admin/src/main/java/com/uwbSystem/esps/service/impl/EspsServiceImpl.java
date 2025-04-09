package com.uwbSystem.esps.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uwbSystem.esps.mapper.EspsMapper;
import com.uwbSystem.esps.domain.Esps;
import com.uwbSystem.esps.service.IEspsService;

/**
 * ESP盒子Service业务层处理
 * 
 * @author zyt
 * @date 2025-02-08
 */
@Service
public class EspsServiceImpl implements IEspsService 
{
    @Autowired
    private EspsMapper espsMapper;

    /**
     * 查询ESP盒子
     * 
     * @param espId ESP盒子主键
     * @return ESP盒子
     */
    @Override
    public Esps selectEspsByEspId(Integer espId)
    {
        return espsMapper.selectEspsByEspId(espId);
    }

    /**
     * 查询ESP盒子列表
     * 
     * @param esps ESP盒子
     * @return ESP盒子
     */
    @Override
    public List<Esps> selectEspsList(Esps esps)
    {
        return espsMapper.selectEspsList(esps);
    }

    /**
     * 新增ESP盒子
     * 
     * @param esps ESP盒子
     * @return 结果
     */
    @Override
    public int insertEsps(Esps esps)
    {
        return espsMapper.insertEsps(esps);
    }

    /**
     * 修改ESP盒子
     * 
     * @param esps ESP盒子
     * @return 结果
     */
    @Override
    public int updateEsps(Esps esps)
    {
        return espsMapper.updateEsps(esps);
    }

    /**
     * 批量删除ESP盒子
     * 
     * @param espIds 需要删除的ESP盒子主键
     * @return 结果
     */
    @Override
    public int deleteEspsByEspIds(Integer[] espIds)
    {
        return espsMapper.deleteEspsByEspIds(espIds);
    }

    /**
     * 删除ESP盒子信息
     * 
     * @param espId ESP盒子主键
     * @return 结果
     */
    @Override
    public int deleteEspsByEspId(Integer espId)
    {
        return espsMapper.deleteEspsByEspId(espId);
    }
}
