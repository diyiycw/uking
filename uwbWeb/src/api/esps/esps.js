import request from '@/utils/request'

// 查询ESP盒子列表
export function listEsps(query) {
  return request({
    url: '/esps/esps/list',
    method: 'get',
    params: query
  })
}

// 查询ESP盒子详细
export function getEsps(espId) {
  return request({
    url: '/esps/esps/' + espId,
    method: 'get'
  })
}

// 新增ESP盒子
export function addEsps(data) {
  return request({
    url: '/esps/esps',
    method: 'post',
    data: data
  })
}

// 修改ESP盒子
export function updateEsps(data) {
  return request({
    url: '/esps/esps',
    method: 'put',
    data: data
  })
}

// 删除ESP盒子
export function delEsps(espId) {
  return request({
    url: '/esps/esps/' + espId,
    method: 'delete'
  })
}
