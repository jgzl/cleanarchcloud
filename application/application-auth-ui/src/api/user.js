import {axios} from '@/utils/request'

/**
 * 用户登录
 *
 * @param {用户名,密码} data
 */
export function login (data) {
  const {username, password} = data;
  return axios({
    url: '/login',
    method: 'post',
    data: { username, password },
    params: { 'remember-me': data['remember-me'] }
  })
}

/**
 * 手机号登录
 *
 * @param {手机号} mobile
 * @param {验证码} code
 */
export const loginByMobile = (params) => {
  return axios({
    url: '/login/mobile',
    method: 'post',
    params
  })
};

/**
 * 查询用户信息
 */
export function getInfo () {
  return axios({
    url: '/user/userInfo',
    method: 'get'
  })
}

/**
 * 退出登录
 */
export function logout () {
  return axios({
    url: '/logout',
    method: 'post'
  })
}

/**
 * 发送手机验证码
 */
export function smsCode(mobile) {
  return axios({
    url: `/user/smsCode/${mobile}`,
    method: 'get'
  })
}

/**
 * 查询单个用户
 */
export function getUser(userId) {
  return axios({
    url: `/user/${userId}`,
    method: 'get'
  })
}

/**
 * 查询用户分页
 */
export function getList(params) {
  return axios({
    url: `/user`,
    method: 'get',
    params
  })
}

/**
 * 修改单个用户
 */
export function updateUser(data) {
  return axios({
    url: `/user`,
    method: 'put',
    data
  })
}

/**
 * 新增单个用户
 */
export function addUser(data) {
  return axios({
    url: `/user`,
    method: 'post',
    data
  })
}

/**
 * 删除单个用户
 */
export function deleteUser(userId) {
  return axios({
    url: `/user/${userId}`,
    method: 'delete'
  })
}
