import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: {token}
  })
}

/**
 * 查询单个用户
 */
export function getUser(userId) {
  return request({
    url: `/user/${userId}`,
    method: 'get'
  })
}

/**
 * 查询用户分页
 */
export function getList(params) {
  return request({
    url: `/user`,
    method: 'get',
    params
  })
}

/**
 * 修改单个用户
 */
export function updateUser(data) {
  return request({
    url: `/user`,
    method: 'put',
    data
  })
}

/**
 * 新增单个用户
 */
export function addUser(data) {
  return request({
    url: `/user`,
    method: 'post',
    data
  })
}

/**
 * 删除单个用户
 */
export function deleteUser(userId) {
  return request({
    url: `/user/${userId}`,
    method: 'delete'
  })
}
