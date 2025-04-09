/*
 * SPDX-FileCopyrightText: 2023-2024 Espressif Systems (Shanghai) CO LTD
 *
 * SPDX-License-Identifier: Unlicense OR CC0-1.0
 */

#include "bsp/esp-bsp.h"
#include "lv_demo_widgets.h"
#include "uart.h"
#include "esp_err.h"
#include "nvs_flash.h"
#include <time.h>

void app_main(void)
{
    uart_init();
    
    // 初始化NVS
    esp_err_t ret = nvs_flash_init();
    if (ret == ESP_ERR_NVS_NO_FREE_PAGES || ret == ESP_ERR_NVS_NEW_VERSION_FOUND) {
        ESP_ERROR_CHECK(nvs_flash_erase());
        ret = nvs_flash_init();
    }
    ESP_ERROR_CHECK(ret); 

    // 初始化Wi-Fi联网
    fast_scan();

    /* Initialize I2C (for touch and audio) */
    bsp_i2c_init();

    /* Initialize display and LVGL */
    bsp_display_cfg_t cfg = {
        .lvgl_port_cfg = ESP_LVGL_PORT_INIT_CONFIG(),
        .buffer_size = BSP_LCD_H_RES * CONFIG_BSP_LCD_DRAW_BUF_HEIGHT,
        .double_buffer = 0,
        .flags = {
            .buff_dma = true,
        }
    };

    bsp_display_start_with_config(&cfg);

    /* Set display brightness to 100% */
    bsp_display_backlight_on();

    setenv("TZ", "CST-8", 1);   
    tzset();
    time_t rawtime;  
    struct tm timeinfo;
    char strftime_buf[64]; 

    time(&rawtime);
    localtime_r(&rawtime, &timeinfo);
    strftime(strftime_buf, sizeof(strftime_buf), "%c", &timeinfo);

    /**
     * @brief Demos provided by LVGL.
     *
     * @note Only enable one demo every time.
     *
     */

    lv_obj_t *scr = lv_disp_get_scr_act(NULL);  // 获取当前显示屏的根对象
    lv_obj_clean(scr);  // 清空屏幕上的所有对象

    bsp_display_lock(0);

    lv_demo_widgets();

    bsp_display_unlock();

    xTaskCreate(rx_task, "uart_rx_task", 1024 * 4, NULL, configMAX_PRIORITIES - 1, NULL);
}
