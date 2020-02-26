import Vue from 'vue'
import axios from 'axios'
import {VueAxios} from './axios'
import notification from 'ant-design-vue/es/notification'
import {ACCESS_TOKEN} from '@/store/mutation-types'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL, // api base_url
  withCredentials: true, // 跨域请求，允许保存cookie
  timeout: 10000 // 请求超时时间
})

const err = (error) => {
  if (error.response) {
    const data = error.response.data
    if (error.response.status === 400) {
      notification.error({
        message: data.errorCode ? data.errorCode : '请求错误',
        description: data.errorMessage
      })
    }
    if (error.response.status === 500) {
      notification.error({
        message: data.errorCode ? data.errorCode : '请求错误',
        description: data.errorMessage
      })
    }
    if (error.response.status === 403) {
      notification.error({
        message: '拒绝访问',
        description: data.errorMessage
      })
    }
    if (error.response.status === 401 && !(data.result && data.status === 'FAILED')) {
      notification.error({
        message: '认证失败',
        description: data.errorMessage
      })
    }
  }
  return Promise.reject(error)
}

// request interceptor
service.interceptors.request.use(config => {
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  return config
}, err)

// response interceptor
service.interceptors.response.use((response) => {
  const { status, errorCode, errorMessage } = response.data
  if (status === 'FAILED') {
    notification.error({
      message: errorCode || '请求错误',
      description: errorMessage
    })
  }
  return response.data
}, err)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}
