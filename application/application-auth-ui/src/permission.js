import router from './router'
import store from './store'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import {domTitle, setDocumentTitle} from '@/utils/domUtil'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

router.beforeEach((to, from, next) => {
  // start progress bar
  NProgress.start()
  // 设置文档标题
  to.meta && (typeof to.meta.title !== 'undefined' && setDocumentTitle(`${to.meta.title} - ${domTitle}`))
  if (to.path === '/user/login') {
    // 登录界面直接放行
    next()
  } else {
    if (store.getters.nickname) {
      next()
    } else {
      store.dispatch('GetInfo').then(res => {
        store.dispatch('GenerateRoutes', {}).then(() => {
          router.addRoutes(store.getters.addRouters)
          // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          next({ ...to, replace: true })
        })
      }).catch(() => {
        next({ path: '/user/login' })
        NProgress.done()
      })
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
