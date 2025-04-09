package com.uwbSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author uking
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class UkingApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(UkingApplication.class, args);
//        C:\Redis-x64-5.0.14\redis-server.exe
        System.out.println("Yumaoo Successful Start\n");
    }
}
