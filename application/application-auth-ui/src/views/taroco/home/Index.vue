<template>
  <a-card>
    {{ username }} 恭喜你登录成功, {{ welcome }}
  </a-card>
</template>

<script>
  import {timeFix} from '@/utils/util'
  import {mapState} from 'vuex'

  export default {
  name: 'Home',
  computed: {
    ...mapState({
      username: state => state.user.name,
      welcome: state => state.user.welcome
    })
  },
  mounted () {
    // 延迟 1 秒显示欢迎信息
    setTimeout(() => {
      this.$notification.success({
        message: '欢迎',
        description: `${timeFix()}，欢迎回来`
      })
    }, 1000)
  }
}
</script>
