import {axios} from '@/utils/request'

/**
 * 查询单个应用
 */
export function getClient (clientId) {
  return axios({
    url: `/clients/${clientId}`,
    method: 'get'
  })
}

/**
 * 查询应用分页
 */
export function getList (params) {
  return axios({
    url: `/clients`,
    method: 'get',
    params
  })
}

/**
 * 修改单个应用
 */
export function updateClient (data) {
  return axios({
    url: `/clients`,
    method: 'put',
    data
  })
}

/**
 * 新增单个应用
 */
export function addClient (data) {
  return axios({
    url: `/clients`,
    method: 'post',
    data
  })
}

/**
 * 删除单个应用
 */
export function deleteClient (clientId) {
  return axios({
    url: `/clients/${clientId}`,
    method: 'delete'
  })
}

/**
 * 确认授权
 */
export function confirmAccess (data) {
  return axios({
    url: '/oauth/custom_authorize',
    method: 'post',
    headers: { 'content-type': 'application/x-www-form-urlencoded' },
    data: data
  })
}
