package com.uwbSystem.radar_records.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uwbSystem.common.annotation.Excel;
import com.uwbSystem.common.core.domain.BaseEntity;

/**
 * 雷达记录对象 radar_records
 * 
 * @author zyt
 * @date 2025-02-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RadarRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ESP号 */
    private Integer espId;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    /** 心率 */
    @Excel(name = "心率")
    private Integer heartRate;

    /** 呼吸率 */
    @Excel(name = "呼吸率")
    private Integer respirationRate;

    /** 人体存在状态 */
    @Excel(name = "人体存在状态")
    private Integer presentState;

    /** 人体活动状态 */
    @Excel(name = "人体活动状态")
    private Integer activeStatus;

    /** 最近目标距离 */
    @Excel(name = "最近目标距离")
    private Integer nearestTargetDistance;

    /** 生命体征异常 */
    @Excel(name = "生命体征异常")
    private Integer vitalSignAbnormality;

    /** 信号强度 */
    @Excel(name = "信号强度")
    private Double signalStrength;

    /** 持续在床时长 */
    @Excel(name = "持续在床时长")
    private Integer durationOfTimeInBed;

    /** 持续离床时长 */
    @Excel(name = "持续离床时长")
    private Integer durationOfTimeOutOfBed;

    @Excel(name = "房间ID")
    private Integer roomId;
    @Excel(name = "房间名称")
    private String roomName;

    public void setEspId(Integer espId) 
    {
        this.espId = espId;
    }

    public Integer getEspId() 
    {
        return espId;
    }
    public void setRecordTime(Date recordTime) 
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime() 
    {
        return recordTime;
    }
    public void setHeartRate(Integer heartRate) 
    {
        this.heartRate = heartRate;
    }

    public Integer getHeartRate() 
    {
        return heartRate;
    }
    public void setRespirationRate(Integer respirationRate) 
    {
        this.respirationRate = respirationRate;
    }

    public Integer getRespirationRate() 
    {
        return respirationRate;
    }
    public void setPresentState(Integer presentState) 
    {
        this.presentState = presentState;
    }

    public Integer getPresentState() 
    {
        return presentState;
    }
    public void setActiveStatus(Integer activeStatus) 
    {
        this.activeStatus = activeStatus;
    }

    public Integer getActiveStatus() 
    {
        return activeStatus;
    }
    public void setNearestTargetDistance(Integer nearestTargetDistance) 
    {
        this.nearestTargetDistance = nearestTargetDistance;
    }

    public Integer getNearestTargetDistance() 
    {
        return nearestTargetDistance;
    }
    public void setVitalSignAbnormality(Integer vitalSignAbnormality) 
    {
        this.vitalSignAbnormality = vitalSignAbnormality;
    }

    public Integer getVitalSignAbnormality() 
    {
        return vitalSignAbnormality;
    }
    public void setSignalStrength(Double signalStrength) 
    {
        this.signalStrength = signalStrength;
    }

    public Double getSignalStrength() 
    {
        return signalStrength;
    }
    public void setDurationOfTimeInBed(Integer durationOfTimeInBed) 
    {
        this.durationOfTimeInBed = durationOfTimeInBed;
    }

    public Integer getDurationOfTimeInBed() 
    {
        return durationOfTimeInBed;
    }
    public void setDurationOfTimeOutOfBed(Integer durationOfTimeOutOfBed) 
    {
        this.durationOfTimeOutOfBed = durationOfTimeOutOfBed;
    }

    public Integer getDurationOfTimeOutOfBed() 
    {
        return durationOfTimeOutOfBed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("espId", getEspId())
            .append("recordTime", getRecordTime())
            .append("heartRate", getHeartRate())
            .append("respirationRate", getRespirationRate())
            .append("presentState", getPresentState())
            .append("activeStatus", getActiveStatus())
            .append("nearestTargetDistance", getNearestTargetDistance())
            .append("vitalSignAbnormality", getVitalSignAbnormality())
            .append("signalStrength", getSignalStrength())
            .append("durationOfTimeInBed", getDurationOfTimeInBed())
            .append("durationOfTimeOutOfBed", getDurationOfTimeOutOfBed())
            .toString();
    }
}
