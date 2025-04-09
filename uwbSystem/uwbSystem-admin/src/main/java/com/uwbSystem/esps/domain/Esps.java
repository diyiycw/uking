package com.uwbSystem.esps.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.uwbSystem.common.annotation.Excel;
import com.uwbSystem.common.core.domain.BaseEntity;

/**
 * ESP盒子对象 esps
 * 
 * @author zyt
 * @date 2025-02-08
 */
public class Esps extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备id */
    private Integer espId;

    /** 设备号 */
    @Excel(name = "设备号")
    private String conductId;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registrationTime;

    public void setEspId(Integer espId) 
    {
        this.espId = espId;
    }

    public Integer getEspId() 
    {
        return espId;
    }
    public void setConductId(String conductId) 
    {
        this.conductId = conductId;
    }

    public String getConductId() 
    {
        return conductId;
    }
    public void setRegistrationTime(Date registrationTime) 
    {
        this.registrationTime = registrationTime;
    }

    public Date getRegistrationTime() 
    {
        return registrationTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("espId", getEspId())
            .append("conductId", getConductId())
            .append("registrationTime", getRegistrationTime())
            .toString();
    }
}
