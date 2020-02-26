import {axios} from '@/utils/request'

/**
 * 用户登录
 *
 * @param {用户名,密码} data
 */
export function login (data) {
  const { username, password } = data
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
}

/**
 * 查询用户信息
 */
export function getInfo () {
  return axios({
    url: '/user',
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
export function smsCode (mobile) {
  return axios({
    url: `/smsCode/${mobile}`,
    method: 'get'
  })
}
