// eslint-disable-next-line
import {BasicLayout, PageView, RouteView, UserLayout} from '@/layouts'

export const asyncRouterMap = [
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: '首页' },
    redirect: '/home',
    children: [
      // home
      {
        path: '/home',
        name: 'home',
        component: () => import('@/views/taroco/home/Index'),
        meta: { title: '首页', icon: 'home' }
      },
      // client
      {
        path: '/manage',
        redirect: '/manage/index',
        component: PageView,
        meta: {title: '系统管理', icon: 'setting'},
        children: [
          {
            path: '/manage/client',
            name: 'clients',
            component: () => import('@/views/taroco/client/Index'),
            meta: {title: '应用管理', icon: 'desktop'}
          },
          {
            path: '/manage/user',
            name: 'user',
            component: () => import('@/views/taroco/user/Index'),
            meta: {title: '用户管理', icon: 'desktop'}
          }
        ]
      }
    ]
  }
];

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  // 登录界面
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      }
    ]
  },
  // 认证界面
  {
    path: '/confirm_access',
    component: () => import('@/views/taroco/confirmAccess/Index')
  },
  // Exception
  {
    path: '/exception',
    name: 'exception',
    component: RouteView,
    redirect: '/exception/403',
    meta: { title: '异常页', icon: 'warning' },
    children: [
      {
        path: '/exception/403',
        name: 'Exception403',
        component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
        meta: { title: '403' }
      },
      {
        path: '/exception/404',
        name: 'Exception404',
        component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
        meta: { title: '404' }
      },
      {
        path: '/exception/500',
        name: 'Exception500',
        component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),
        meta: {title: '500'}
      }
    ]
  },
  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }
];
