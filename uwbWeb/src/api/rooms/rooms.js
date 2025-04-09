import request from '@/utils/request'

// 查询房间列表
export function listRooms(query) {
  return request({
    url: '/rooms/rooms/list',
    method: 'get',
    params: query
  })
}

// 查询房间详细
export function getRooms(roomId) {
  return request({
    url: '/rooms/rooms/' + roomId,
    method: 'get'
  })
}

// 新增房间
export function addRooms(data) {
  return request({
    url: '/rooms/rooms',
    method: 'post',
    data: data
  })
}

// 修改房间
export function updateRooms(data) {
  return request({
    url: '/rooms/rooms',
    method: 'put',
    data: data
  })
}

// 删除房间
export function delRooms(roomId) {
  return request({
    url: '/rooms/rooms/' + roomId,
    method: 'delete'
  })
}
