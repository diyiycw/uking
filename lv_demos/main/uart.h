#ifndef UART_H
#define UART_H

void uart_init(void);
void time_sync_notification_cb(struct timeval *tv);
void fast_scan(void);
void rx_task(void *arg);

#endif