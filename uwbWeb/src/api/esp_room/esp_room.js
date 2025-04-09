import request from '@/utils/request'

// 查询设备房间列表
export function listEsp_room(query) {
  return request({
    url: '/esp_room/esp_room/list',
    method: 'get',
    params: query
  })
}

// 查询设备房间详细
export function getEsp_room(espId) {
  return request({
    url: '/esp_room/esp_room/' + espId,
    method: 'get'
  })
}

// 新增设备房间
export function addEsp_room(data) {
  return request({
    url: '/esp_room/esp_room',
    method: 'post',
    data: data
  })
}

// 修改设备房间
export function updateEsp_room(data) {
  return request({
    url: '/esp_room/esp_room',
    method: 'put',
    data: data
  })
}

// 删除设备房间
export function delEsp_room(espId) {
  return request({
    url: '/esp_room/esp_room/' + espId,
    method: 'delete'
  })
}
