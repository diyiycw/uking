#if CONFIG_EXAMPLE_WIFI_ALL_CHANNEL_SCAN
#define DEFAULT_SCAN_METHOD WIFI_ALL_CHANNEL_SCAN
#elif CONFIG_EXAMPLE_WIFI_FAST_SCAN
#define DEFAULT_SCAN_METHOD WIFI_FAST_SCAN
#else
#define DEFAULT_SCAN_METHOD WIFI_FAST_SCAN
#endif /*CONFIG_EXAMPLE_SCAN_METHOD*/
 
#if CONFIG_EXAMPLE_WIFI_CONNECT_AP_BY_SIGNAL
#define DEFAULT_SORT_METHOD WIFI_CONNECT_AP_BY_SIGNAL
#elif CONFIG_EXAMPLE_WIFI_CONNECT_AP_BY_SECURITY
#define DEFAULT_SORT_METHOD WIFI_CONNECT_AP_BY_SECURITY
#else
#define DEFAULT_SORT_METHOD WIFI_CONNECT_AP_BY_SIGNAL
#endif /*CONFIG_EXAMPLE_SORT_METHOD*/
#if CONFIG_EXAMPLE_FAST_SCAN_THRESHOLD
#define DEFAULT_RSSI CONFIG_EXAMPLE_FAST_SCAN_MINIMUM_SIGNAL
#if CONFIG_EXAMPLE_FAST_SCAN_WEAKEST_AUTHMODE_OPEN
#define DEFAULT_AUTHMODE WIFI_AUTH_OPEN
#elif CONFIG_EXAMPLE_FAST_SCAN_WEAKEST_AUTHMODE_WEP
#define DEFAULT_AUTHMODE WIFI_AUTH_WEP
#elif CONFIG_EXAMPLE_FAST_SCAN_WEAKEST_AUTHMODE_WPA
#define DEFAULT_AUTHMODE WIFI_AUTH_WPA_PSK
#elif CONFIG_EXAMPLE_FAST_SCAN_WEAKEST_AUTHMODE_WPA2
#define DEFAULT_AUTHMODE WIFI_AUTH_WPA2_PSK
#else
#define DEFAULT_AUTHMODE WIFI_AUTH_OPEN
#endif
#else
#define DEFAULT_RSSI -127
#define DEFAULT_AUTHMODE WIFI_AUTH_OPEN
#endif /*CONFIG_EXAMPLE_FAST_SCAN_THRESHOLD*/

#include "task.h"

static const char *SCREEN_TAG = "display";
static const char *WIFI_TAG = "wifi";
static const char *SNTP_TAG = "sntp";
static const char *TCP_TAG = "tcp";
static const char *TRANS_TAG = "translate";
static const char *QRCODE_TAG = "qrcode";

static const int RX_BUF_SIZE = 1024;
static const char *PAYLOAD = "Hello, I'm x5!";
//static bool ntp_synced = false;
static void obtain_time(void);

void uart_init(void)
{
    const uart_config_t uart_config = {
        .baud_rate = 115200,
        .data_bits = UART_DATA_8_BITS,
        .parity = UART_PARITY_DISABLE,
        .stop_bits = UART_STOP_BITS_1,
        .flow_ctrl = UART_HW_FLOWCTRL_DISABLE,
        .source_clk = UART_SCLK_DEFAULT,
    };
    // We won't use a buffer for sending data.
    uart_driver_install(UART_NUM_1, RX_BUF_SIZE * 2, 0, 0, NULL, 0);
    uart_param_config(UART_NUM_1, &uart_config);
    uart_set_pin(UART_NUM_1, TXD_PIN, RXD_PIN, UART_PIN_NO_CHANGE, UART_PIN_NO_CHANGE);
}

#ifdef CONFIG_SNTP_TIME_SYNC_METHOD_CUSTOM
void sntp_sync_time(struct timeval *tv)
{
   settimeofday(tv, NULL);
   ESP_LOGI(SNTP_TAG, "Time is synchronized from custom code");
   sntp_set_sync_status(SNTP_SYNC_STATUS_COMPLETED);
}
#endif

void time_sync_notification_cb(struct timeval *tv)
{
    ESP_LOGI(SNTP_TAG, "Notification of a time synchronization event");
}

static void print_servers(void)
{
    ESP_LOGI(SNTP_TAG, "List of configured NTP servers:");

    for (uint8_t i = 0; i < SNTP_MAX_SERVERS; ++i){
        if (esp_sntp_getservername(i)){
            ESP_LOGI(SNTP_TAG, "server %d: %s", i, esp_sntp_getservername(i));
        } else {
            // we have either IPv4 or IPv6 address, let's print it
            char buff[INET6_ADDRSTRLEN];
            ip_addr_t const *ip = esp_sntp_getserver(i);
            if (ipaddr_ntoa_r(ip, buff, INET6_ADDRSTRLEN) != NULL)
                ESP_LOGI(SNTP_TAG, "server %d: %s", i, buff);
        }
    }
}

static void initialize_sntp(void)
{
    ESP_LOGI(SNTP_TAG, "Initializing SNTP");
    sntp_setoperatingmode(SNTP_OPMODE_POLL);
    sntp_setservername(0, "pool.ntp.org");
    sntp_set_time_sync_notification_cb(time_sync_notification_cb);
#ifdef CONFIG_SNTP_TIME_SYNC_METHOD_SMOOTH
    sntp_set_sync_mode(SNTP_SYNC_MODE_SMOOTH);
#endif
    sntp_init();
}

static void obtain_time(void)
{
#ifdef LWIP_DHCP_GET_NTP_SRV
    sntp_servermode_dhcp(1);
#endif
 
    initialize_sntp();
 
    // wait for time to be set
    time_t now = 0;
    struct tm timeinfo = { 0 };
    int retry = 0;
    const int retry_count = 10;
    while (sntp_get_sync_status() == SNTP_SYNC_STATUS_RESET && ++retry < retry_count) {
        ESP_LOGI(SNTP_TAG, "Waiting for system time to be set... (%d/%d)", retry, retry_count);
        vTaskDelay(500 / portTICK_PERIOD_MS);
    }
    time(&now);
    localtime_r(&now, &timeinfo);
 
    //ESP_ERROR_CHECK( example_disconnect() );

    //打印时间
    char strftime_buf[64];
 
    // Set timezone to Eastern Standard Time and print local time
    setenv("TZ", "EST5EDT,M3.2.0/2,M11.1.0", 1);
    tzset();
    localtime_r(&now, &timeinfo);
    strftime(strftime_buf, sizeof(strftime_buf), "%c", &timeinfo);
    ESP_LOGI(SNTP_TAG, "The current date/time in New York is: %s", strftime_buf);
 
    // Set timezone to China Standard Time
    setenv("TZ", "CST-8", 1);
    tzset();
    localtime_r(&now, &timeinfo);
    strftime(strftime_buf, sizeof(strftime_buf), "%c", &timeinfo);
    ESP_LOGI(SNTP_TAG, "The current date/time in Shanghai is: %s", strftime_buf);

    // 输出时间到控制台（或任何你选择的输出方式）  
    printf("Current local time and date: %s", asctime(&timeinfo));     
}
 
static void event_handler(void* arg, esp_event_base_t event_base,
                                int32_t event_id, void* event_data)
{
    if (event_base == WIFI_EVENT && event_id == WIFI_EVENT_STA_START) {
        esp_wifi_connect();
    } else if (event_base == WIFI_EVENT && event_id == WIFI_EVENT_STA_DISCONNECTED) {
        esp_wifi_connect();
    } else if (event_base == IP_EVENT && event_id == IP_EVENT_STA_GOT_IP) {
        ip_event_got_ip_t* event = (ip_event_got_ip_t*) event_data;
        ESP_LOGI(WIFI_TAG, "got ip:" IPSTR, IP2STR(&event->ip_info.ip));
	obtain_time();
    }
}

/* Initialize Wi-Fi as sta and set scan method */
void fast_scan(void)
{
    ESP_ERROR_CHECK(esp_netif_init());
    ESP_ERROR_CHECK(esp_event_loop_create_default());
 
    wifi_init_config_t cfg = WIFI_INIT_CONFIG_DEFAULT();
    ESP_ERROR_CHECK(esp_wifi_init(&cfg));
 
    ESP_ERROR_CHECK(esp_event_handler_instance_register(WIFI_EVENT, ESP_EVENT_ANY_ID, &event_handler, NULL, NULL));
    ESP_ERROR_CHECK(esp_event_handler_instance_register(IP_EVENT, IP_EVENT_STA_GOT_IP, &event_handler, NULL, NULL));
 
    // Initialize default station as network interface instance (esp-netif)
    esp_netif_t *sta_netif = esp_netif_create_default_wifi_sta();
    assert(sta_netif);
 
    // Initialize and start WiFi
    wifi_config_t wifi_config = {
        .sta = {
            .ssid = DEFAULT_SSID,
            .password = DEFAULT_PWD,
            .scan_method = DEFAULT_SCAN_METHOD,
            .sort_method = DEFAULT_SORT_METHOD,
            .threshold.rssi = DEFAULT_RSSI,
            .threshold.authmode = DEFAULT_AUTHMODE,
        },
    };
    ESP_ERROR_CHECK(esp_wifi_set_mode(WIFI_MODE_STA));
    ESP_ERROR_CHECK(esp_wifi_set_config(WIFI_IF_STA, &wifi_config));
    ESP_ERROR_CHECK(esp_wifi_start());
}

int sendData(const char* logName, const char* data)
{
    const int len = strlen(data);
    const int txBytes = uart_write_bytes(UART_NUM_1, data, len);
    ESP_LOGI(logName, "Wrote %d bytes", txBytes);
    return txBytes;
}

static void tx_task(void *arg)
{
    static const char *TX_TASK_TAG = "TX_TASK";
    esp_log_level_set(TX_TASK_TAG, ESP_LOG_INFO);
    while (1) {
        sendData(TX_TASK_TAG, "Hello world");
        vTaskDelay(2000 / portTICK_PERIOD_MS);
    }
}

static void hex_string_to_bytes(const char* hex_string, uint8_t* byte_array, size_t* byte_array_len) {
    size_t len = strlen(hex_string);
    size_t byte_idx = 0;

    for (size_t i = 0; i < len; i += 2) {
        if (hex_string[i] == ' ') {
            i++;
            if(i >= len) break;
        }

        char byte_str[3] = {hex_string[i], hex_string[i + 1], '\0'};
        byte_array[byte_idx++] = (uint8_t)strtol(byte_str, NULL, 16);
    }

    *byte_array_len = byte_idx;
}

static void tx_time(char* now)
{
    static const char *TX_TASK_TAG = "TX_TASK";
    esp_log_level_set(TX_TASK_TAG, ESP_LOG_INFO);
    uint8_t* byte_array = (uint8_t*) malloc(RX_BUF_SIZE + 1);
    size_t byte_array_len = 0;
    hex_string_to_bytes(now, byte_array, &byte_array_len);
    // for(size_t i = 0; i < byte_array_len; i++)
    // {
    //     ESP_LOGI(TX_TASK_TAG, "%02x", byte_array[i]);
    // }
   
    const int txBytes = uart_write_bytes(UART_NUM_1, (const char*)byte_array, byte_array_len);
    ESP_LOGI(TX_TASK_TAG, "Wrote %d bytes: %s", txBytes, (const char*)byte_array);
    free(byte_array);
    vTaskDelay(500 / portTICK_PERIOD_MS);
}

static void translate(char *result, uint8_t *data, int bytes_read)
{
    *result = '\0';
    // ESP_LOGI(TRANS_TAG, "Data received (%d bytes):", bytes_read);

    // for (int i = 0; i < 38; i++) {
    //     ESP_LOGI(TRANS_TAG, "%02x ", data[i]);
    // }

    int i = 4;
    // 获取产品唯一性ID
    ESP_LOGI(TRANS_TAG, "Conduct ID: %02x %02x %02x %02x %02x %2x", data[i], data[i+1], data[i+2], data[i+3], data[i+4], data[i+5]);
    sprintf(result + strlen(result), "Conduct ID: %02x %02x %02x %02x %02x %2x\n", data[i], data[i+1], data[i+2], data[i+3], data[i+4], data[i+5]);
    i += 6;
    // 获取心率
    ESP_LOGI(TRANS_TAG, "Heart Rate: %d beats per minute", (int)data[i]);
    sprintf(result + strlen(result), "Heart Rate: %d beats per minute\n", (int)data[i]);
    i += 1;
    // 获取呼吸率
    ESP_LOGI(TRANS_TAG, "Respiration Rate: %d breaths per minute", (int)data[i]);
    sprintf(result + strlen(result), "Respiration Rate: %d breaths per minute\n", (int)data[i]);
    i += 1;
    // 获取人体存在状态
    char presence_status[21];
    if(data[i] == 0x00)
    {
        strcpy(presence_status, "No person detected");
    }
    else if(data[i] == 0x01)
    {
        strcpy(presence_status, "Person detected");
    }
    else
    {
        strcpy(presence_status, "Weak signal detected");
    }
    ESP_LOGI(TRANS_TAG, "Presence Status: %s", presence_status);
    sprintf(result + strlen(result), "Presence Status: %s\n", presence_status);
    i += 1;
    // 获取人体活动状态
    char activity_status[21];
    if(data[i] == 0x00)
    {
        strcpy(activity_status, "No person detected");
    }
    else if(data[i] == 0x01)
    {
        strcpy(activity_status, "Resting");
    }
    else if(data[i] == 0x02)
    {
        strcpy(activity_status, "Quiet");
    }
    else if(data[i] == 0x03)
    {
        strcpy(activity_status, "Active");
    }
    else
    {
        strcpy(activity_status, "Continuously active");
    }
    ESP_LOGI(TRANS_TAG, "Activity Status: %s", activity_status);
    sprintf(result + strlen(result), "Activity Status: %s\n", activity_status);
    i += 1;
    // 获取最近目标距离
    int distance = ((int)data[i] * 16 * 16) + (int)data[i+1];
    ESP_LOGI(TRANS_TAG, "Nearest Target Distance: %dcm", distance);
    sprintf(result + strlen(result), "Nearest Target Distance: %dcm\n", distance);
    i += 2;
    // 获取生命体征异常
    if(data[i] == 0x00)
    {
        ESP_LOGI(TRANS_TAG, "Vital Sign Abnormality: Normal");
        sprintf(result + strlen(result), "Vital Sign Abnormality: Normal\n");
    }
    else
    {
        ESP_LOGI(TRANS_TAG, "Vital Sign Abnormality: No signal detected or abnormal");
        sprintf(result + strlen(result), "Vital Sign Abnormality: No signal detected or abnormal\n");
    }
    i += 1;
    // 获取信号强度
    uint32_t combined = (data[i] << 24) | (data[i+1] << 16) | (data[i+2] << 8) | data[i+3];
    float signal_strength;
    *((uint32_t*)&signal_strength) = combined;
    ESP_LOGI(TRANS_TAG, "Signal Strength: %.2f dBm", signal_strength);
    sprintf(result + strlen(result), "Signal Strength: %.2f dBm\n", signal_strength);
    i += 4;
    // 获取持续在床时间
    int duration_in_bed = ((int)data[i] * 16 * 16) + (int)data[i+1];
    ESP_LOGI(TRANS_TAG, "Duration of Time in Bed: %dmin", duration_in_bed);
    sprintf(result + strlen(result), "Duration of Time in Bed: %dmin\n", duration_in_bed);
    i += 2;
    // 获取持续离床时间
    int duration_out_of_bed = ((int)data[i] * 16 * 16) + (int)data[i+1];
    ESP_LOGI(TRANS_TAG, "Duration of Time Out of Bed: %dmin", duration_out_of_bed);
    sprintf(result + strlen(result), "Duration of Time Out of Bed: %dmin\n", duration_out_of_bed);
    i += 5;
    // 获取当前时间
    int current_hour = (int)data[i];
    int current_minute = (int)data[i+1];
    int current_second = (int)data[i+2];
    ESP_LOGI(TRANS_TAG, "Current Time: %d:%d:%d", current_hour, current_minute, current_second);
    sprintf(result + strlen(result), "Current Time: %d:%d:%d\n", current_hour, current_minute, current_second);
    i += 3;
    return;
}

char* get_time_string(uint8_t terminal_status) {
    time_t rawtime;
    struct tm timeinfo;

    // 分配内存给结果字符串（需要返回，因此不能在函数内释放）
    char* time_str = (char*)malloc(104);

    if (time(&rawtime) == -1 || localtime_r(&rawtime, &timeinfo) == NULL) {
        // 获取时间失败，设置标志位为0x00，并返回一个错误字符串
        sprintf(time_str, "00 00 00 00 00 00 00 00 00");
        return time_str;
    }

    uint16_t checksum = (uint8_t)(timeinfo.tm_year - 100) + (uint8_t)(timeinfo.tm_mon + 1) + (uint8_t)timeinfo.tm_mday 
                        + (uint8_t)timeinfo.tm_hour + (uint8_t)timeinfo.tm_min + (uint8_t)timeinfo.tm_sec 
                        + (uint8_t)((timeinfo.tm_wday == 0) ? 7 : timeinfo.tm_wday) + (uint8_t)terminal_status + 1
                        + 0x55 + 0xAA + 0x03 + 0x09;
    
    // 获取时间成功，格式化字符串
    sprintf(time_str, "55 aa 03 09 01 %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X",
            timeinfo.tm_year - 100,     // 年份，以2000年为基准
            timeinfo.tm_mon + 1,        // 月份，从1开始
            timeinfo.tm_mday,           // 日期
            timeinfo.tm_hour,           // 小时
            timeinfo.tm_min,            // 分钟
            timeinfo.tm_sec,            // 秒钟
            (timeinfo.tm_wday == 0) ? 7 : timeinfo.tm_wday, // 星期几
            terminal_status,            // 终端在线/离线状态
            (checksum >> 8) & 0xFF,
            checksum & 0xFF); 
    
    return time_str;
}

static int tcp_connect_to_server(int pre_socket)
{
    /*
    if(pre_socket > 0)
    {
        return pre_socket;
    }
    */

    char rx_buffer[RX_BUF_SIZE];
    char host_ip[] = HOST_IP_ADDR;
    int addr_family = 0;
    int ip_protocol = 0;
    int overtip = 0;

    struct sockaddr_in dest_addr;
    dest_addr.sin_addr.s_addr = inet_addr(host_ip);
    dest_addr.sin_family = AF_INET;
    dest_addr.sin_port = htons(PORT);
    addr_family = AF_INET;
    ip_protocol = IPPROTO_IP;

    ESP_LOGI(TCP_TAG, "Socket type: TCP");
    int sock = socket(addr_family, SOCK_STREAM, ip_protocol);
    if (sock < 0) {
        ESP_LOGE(TCP_TAG, "Unable to create socket: errno %d", errno);
        return sock;
    }

    ESP_LOGI(TCP_TAG, "Socket created, connecting to %s:%d", host_ip, PORT);
    ESP_LOGI(TCP_TAG, "Waiting");
    vTaskDelay(500 / portTICK_PERIOD_MS);
    //ESP_LOGI(TCP_TAG, "STOP AT A");
    
    int err = connect(sock, (struct sockaddr *)&dest_addr, sizeof(struct sockaddr_in)); //客户端描述，服务器的socket地址，socket地址的长度
    
    //ESP_LOGI(TCP_TAG, "STOP AT B");
    if (err != 0) {
        ESP_LOGE(TCP_TAG, "Socket unable to connect: errno %d", errno);
        return sock;
    }
    
    ESP_LOGI(TCP_TAG, "Successfully connected");
    //vTaskDelay(2000 / portTICK_PERIOD_MS);

    return sock;
}

void tcp_client_config(void *pt) {
    char rx_buffer[RX_BUF_SIZE];
    char host_ip[] = HOST_IP_ADDR;
    int addr_family = 0;
    int ip_protocol = 0;
    int overtip = 0;
    while (1){
#if defined(CONFIG_EXAMPLE_IPV4)
        struct sockaddr_in dest_addr;
        dest_addr.sin_addr.s_addr = inet_addr(host_ip);
        dest_addr.sin_family = AF_INET;
        dest_addr.sin_port = htons(PORT);
        addr_family = AF_INET;
        ip_protocol = IPPROTO_IP;
#elif defined(CONFIG_EXAMPLE_IPV6)
        struct sockaddr_in6 dest_addr = {0};
        inet6_aton(host_ip, &dest_addr.sin6_addr);
        dest_addr.sin6_family = AF_INET6;
        dest_addr.sin6_port = htons(PORT);
        dest_addr.sin6_scope_id = esp_netif_get_netif_impl_index(EXAMPLE_INTERFACE);
        addr_family = AF_INET6;
        ip_protocol = IPPROTO_IPV6;
#elif defined(CONFIG_EXAMPLE_SOCKET_IP_INPUT_STDIN)
        struct sockaddr_storage dest_addr = {0};
        ESP_ERROR_CHECK(get_addr_from_stdin(PORT, SOCK_STREAM, &ip_protocol, &addr_family, &dest_addr));
#endif

        struct sockaddr_in dest_addr;
        dest_addr.sin_addr.s_addr = inet_addr(host_ip);
        dest_addr.sin_family = AF_INET;
        dest_addr.sin_port = htons(PORT);
        addr_family = AF_INET;
        ip_protocol = IPPROTO_IP;

        ESP_LOGI(TCP_TAG, "Socket type: TCP");
        int sock = socket(addr_family, SOCK_STREAM, ip_protocol);
        if (sock < 0) {
            ESP_LOGE(TCP_TAG, "Unable to create socket: errno %d", errno);
            break;
        }
        ESP_LOGI(TCP_TAG, "Socket created, connecting to %s:%d", host_ip, PORT);
        ESP_LOGI(TCP_TAG, "Waiting");
        vTaskDelay(500 / portTICK_PERIOD_MS); //这里要给个时间，因为目前pc作为我们的服务端，需要手动连接（自己按）
        ESP_LOGI(TCP_TAG, "STOP AT A");
       
        int err = connect(sock, (struct sockaddr *)&dest_addr, sizeof(struct sockaddr_in)); //客户端描述，服务器的socket地址，socket地址的长度
        
        ESP_LOGI(TCP_TAG, "STOP AT B");
        if (err != 0) {
            ESP_LOGE(TCP_TAG, "Socket unable to connect: errno %d", errno);
            break;
        }
        
        ESP_LOGI(TCP_TAG, "Successfully connected");

        ESP_LOGI(TCP_TAG, "Sending");
        int usersend = send(sock, PAYLOAD, strlen(PAYLOAD), 0);
        
        vTaskDelay(500 / portTICK_PERIOD_MS);

        if (sock != -1) {
            ESP_LOGI("Finished","...");
            ESP_LOGE(TCP_TAG, "Shutting down socket and restarting...");
            shutdown(sock, 0); //关闭TCP连接，不关闭sock（0：SHUT_RD关闭接收通道 ；1：SHUT_WR关闭发送通道 ；2：SHUT_RDWR同时关闭接收通道和发送通道）
            close(sock); //关闭sock并释放资源
            if(overtip)
            break; //跳出最外面的while
        }
    }
    vTaskDelete(NULL);
}

void generate_qrcode(const uint8_t *data) {
    char text[13];
    snprintf(text, sizeof(text), "%02X%02X%02X%02X%02X%02X",
             data[4], data[5], data[6], data[7], data[8], data[9]);

    // 更新二维码内容
    lv_qrcode_update(qrCode, text, strlen(text));
    lv_obj_clear_flag(qrCode, LV_OBJ_FLAG_HIDDEN); // 确保二维码对象显示
}

void rx_task(void *arg)
{
    static const char *RX_TASK_TAG = "RX_TASK";
    esp_log_level_set(RX_TASK_TAG, ESP_LOG_INFO);
    static lv_obj_t *label = NULL;
    uint8_t* data = (uint8_t*) malloc(RX_BUF_SIZE + 1);
  
    int sock = -1;
    bool first_iter = true;

    while (1) {  
        memset(data, 0, RX_BUF_SIZE + 1);

        const int rxBytes = uart_read_bytes(UART_NUM_1, data, RX_BUF_SIZE, 1000 / portTICK_PERIOD_MS); 

        ESP_LOGE("CheckPoint", "We read %d bytes.", rxBytes);

        if(first_iter) {
            generate_qrcode(data);
            first_iter = false;
        }

        lv_update(value_label1, value_label2, 100, 20);

        if (rxBytes > 0) {  
            data[rxBytes] = 0; 
            ESP_LOGI(RX_TASK_TAG, "Read %d bytes: '%s'", rxBytes, data); 
            ESP_LOG_BUFFER_HEXDUMP(RX_TASK_TAG, data, rxBytes, ESP_LOG_INFO);
            char *monitor_result = (char*) malloc(RX_BUF_SIZE * 2);
            if(rxBytes > 13 && data[10] < 0x05 && data[11] > 0x70) {
                continue;
            }
            else if(rxBytes > 13) {
                size_t i = 0;
                uint8_t* sub_data;
                int len_sub_data;
                for (i = 0; i < rxBytes - 2; i++) {
                    if (data[i] == 0x55 && data[i + 1] == 0xAA && data[i + 2] == 0x30) {
                        break;
                    }
                }
                sub_data = &data[i];
                len_sub_data = (int)sub_data[3];
                ESP_LOGI(RX_TASK_TAG, "Sub data (%d bytes): %2x %2x %2x %2x %2x", len_sub_data, 
                                        sub_data[0], sub_data[1], sub_data[2], sub_data[3], sub_data[4]);

                int heart_rate_value = data[10];
                int breath_rate_value = data[11];

                // int hour = data[29];
                // int minute = data[30];
                // char* time_now = strcat((char *)hour, (char *)minute);
                // ESP_LOGE(TCP_TAG, "%d %d", heart_rate_value, breath_rate_value);

                lv_update(value_label1, value_label2, heart_rate_value, breath_rate_value);

                translate(monitor_result, sub_data, len_sub_data + 3);
                
                // if(sock < 0)
                // {
                //     ESP_LOGE(TCP_TAG, "Socket disconnected!");
                //     sock = tcp_connect_to_server(sock);
                // }

                /////////////////////////////////////////////////////////
                /*
                int close_info = send(sock, "Close", strlen("Close"), 0);
                shutdown(sock, 2);
                close(sock);
                sock = tcp_connect_to_server(sock);

                ESP_LOGI(TCP_TAG, "Sending");
                int usersend = send(sock, monitor_result, strlen(monitor_result), 0);
                */
                /////////////////////////////////////////////////////////

                //ESP_LOGE(TCP_TAG, "%d", usersend);
                //vTaskDelay(2000 / portTICK_PERIOD_MS);
            }
            else
            {
                uint8_t terminal_status = 0x5A;
                char* time_str = get_time_string(terminal_status);
                ESP_LOGI(RX_TASK_TAG, "Time string: %s", time_str);
                tx_time(time_str);
                free(time_str);
            }
            free(monitor_result);
        }
    }  
    free(data);
}
