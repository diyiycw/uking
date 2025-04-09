package com.uwbSystem.esps.mapper;

import java.util.List;
import com.uwbSystem.esps.domain.Esps;

/**
 * ESP盒子Mapper接口
 * 
 * @author zyt
 * @date 2025-02-08
 */
public interface EspsMapper 
{
    /**
     * 查询ESP盒子
     * 
     * @param espId ESP盒子主键
     * @return ESP盒子
     */
    public Esps selectEspsByEspId(Integer espId);

    /**
     * 查询ESP盒子列表
     * 
     * @param esps ESP盒子
     * @return ESP盒子集合
     */
    public List<Esps> selectEspsList(Esps esps);

    /**
     * 新增ESP盒子
     * 
     * @param esps ESP盒子
     * @return 结果
     */
    public int insertEsps(Esps esps);

    /**
     * 修改ESP盒子
     * 
     * @param esps ESP盒子
     * @return 结果
     */
    public int updateEsps(Esps esps);

    /**
     * 删除ESP盒子
     * 
     * @param espId ESP盒子主键
     * @return 结果
     */
    public int deleteEspsByEspId(Integer espId);

    /**
     * 批量删除ESP盒子
     * 
     * @param espIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEspsByEspIds(Integer[] espIds);
}
