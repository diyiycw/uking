import request from '@/utils/request'

// 查询房间用户列表
export function listRoom_user(query) {
  return request({
    url: '/room_user/room_user/list',
    method: 'get',
    params: query
  })
}

// 查询房间用户详细
export function getRoom_user(roomId) {
  return request({
    url: '/room_user/room_user/' + roomId,
    method: 'get'
  })
}

// 新增房间用户
export function addRoom_user(data) {
  return request({
    url: '/room_user/room_user',
    method: 'post',
    data: data
  })
}

// 修改房间用户
export function updateRoom_user(data) {
  return request({
    url: '/room_user/room_user',
    method: 'put',
    data: data
  })
}

// 删除房间用户
export function delRoom_user(roomId) {
  return request({
    url: '/room_user/room_user/' + roomId,
    method: 'delete'
  })
}
