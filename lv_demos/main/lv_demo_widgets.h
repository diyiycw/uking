/**
 * @file lv_demo_widgets.h
 *
 */

#ifndef LV_DEMO_WIDGETS_H
#define LV_DEMO_WIDGETS_H

#ifdef __cplusplus
extern "C" {
#endif

/*********************
 *      INCLUDES
 *********************/
#include "freertos/FreeRTOS.h"
#include "lvgl.h"

/*********************
 *      DEFINES
 *********************/

/**********************
 *      TYPEDEFS
 **********************/

/**********************
 * GLOBAL PROTOTYPES
 **********************/
void lv_demo_widgets(void);
void lv_demo_widgets_close(void);
void lv_update(lv_obj_t *heart_rate_label, lv_obj_t *breath_rate_label, int heart_rate_value, int breath_rate_value);
void get_wifi_credentials(char *out_wifi_id, char *out_password);

/**********************
 * GLOBAL VARIABLES
 **********************/
//extern lv_obj_t *chart1;
extern lv_obj_t *value_label1;
extern lv_obj_t *value_label2;
extern lv_obj_t *chart3;
extern lv_obj_t *qrCode;

/**********************
 *      MACROS
 **********************/

#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /*LV_DEMO_WIDGETS_H*/
