import request from '@/utils/request'

// 查询雷达记录列表
export function listRadar_records(query) {
  return request({
    url: '/radar_records/radar_records/list',
    method: 'get',
    params: query
  })
}

// 查询雷达记录详细
export function getRadar_records(espId) {
  return request({
    url: '/radar_records/radar_records/' + espId,
    method: 'get'
  })
}

// 新增雷达记录
export function addRadar_records(data) {
  return request({
    url: '/radar_records/radar_records',
    method: 'post',
    data: data
  })
}

// 修改雷达记录
export function updateRadar_records(data) {
  return request({
    url: '/radar_records/radar_records',
    method: 'put',
    data: data
  })
}

// 删除雷达记录
export function delRadar_records(espId) {
  return request({
    url: '/radar_records/radar_records/' + espId,
    method: 'delete'
  })
}
