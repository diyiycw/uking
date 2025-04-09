#ifndef TASK_H
#define TASK_H

#ifdef __cplusplus
extern "C" {
#endif

/*********************
 *      INCLUDES
 *********************/

#include <stdio.h>  
#include <string.h>
#include <time.h> 
#include <sys/time.h>
#include <sys/param.h>
#include "driver/gpio.h"
#include "driver/uart.h"
#include "esp_system.h"
#include "esp_log.h"
#include "esp_wifi.h"  
#include "esp_attr.h"
#include "esp_sleep.h"
#include "esp_event.h"
#include "esp_netif.h"
#include "esp_netif_sntp.h"
#include "esp_sntp.h"
#include "freertos/FreeRTOS.h"
#include "freertos/queue.h"
#include "freertos/task.h"
#include "freertos/event_groups.h"
#include "lvgl.h"
#include "lv_conf.h"
#include "lwip/err.h"
#include "lwip/errno.h"
#include "lwip/ip_addr.h"
#include "lwip/prot/dns.h"
#include "lwip/sockets.h"
#include "nvs_flash.h"
#include "sdkconfig.h"
#include "lv_demo_widgets.h"
#include "lv_lib_qrcode/lv_qrcode.h"

/*********************
 *      DEFINES
 *********************/

// WIFI 账号密码
#define DEFAULT_SSID "MYSSID"  
#define DEFAULT_PWD "MYPASSWORD"

#define INET6_ADDRSTRLEN 48

#define TXD_PIN (GPIO_NUM_13)
#define RXD_PIN (GPIO_NUM_11)

#define HOST_IP_ADDR "192.168.158.1"
#define PORT 19999

/**********************
 *      TYPEDEFS
 **********************/

/**********************
 * GLOBAL PROTOTYPES
 **********************/

/**********************
 *      MACROS
 **********************/

#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /*TASK_H*/