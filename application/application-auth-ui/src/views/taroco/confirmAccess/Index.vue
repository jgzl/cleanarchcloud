<template>
  <div class="exception">
    <div class="imgBlock">
      <div class="imgEle" :style="{backgroundImage: `url(https://gw.alipayobjects.com/zos/rmsportal/wZcnGqRDyhPOEYFcZDnb.svg)`}">
      </div>
    </div>
    <div class="content">
      <h1>应用授权请求</h1>
      <div class="desc">同意授权后将允许应用{{ appName }}，获得你的用户信息及以下权限：</div>
      <div class="desc">
        <a-tag v-for="(item, index) in scope" :key="index" color="#2db7f5">{{ item }}</a-tag>
      </div>
      <div class="actions">
        <a-button type="danger" @click="handleConfirm(false)">拒绝授权</a-button>
        <a-button type="primary" @click="handleConfirm(true)">同意授权</a-button>
      </div>
    </div>
  </div>
</template>

<script>
  import {confirmAccess} from '@/api/client'
  import qs from 'qs'

  export default {
  name: 'ConfirmAccess',
  data () {
    return {
      appName: this.$route.query.appName,
      clientId: this.$route.query.clientId,
      redirectUri: this.$route.query.redirectUri,
      scope: this.$route.query.scope.split(',')
    }
  },
  methods: {
    handleConfirm (value) {
      const params = {
        'user_oauth_approval': true
      }
      this.scope.forEach(s => {
        params['scope.' + s] = value
      })
      confirmAccess(qs.stringify(params)).then(({ result }) => {
        window.location.href = result
      })
    }
  }
}
</script>
<style lang="less">
@import "~ant-design-vue/lib/style/index";

.exception {
  display: flex;
  align-items: center;
  height: 80%;
  min-height: 500px;

  .imgBlock {
    flex: 0 0 62.5%;
    width: 62.5%;
    padding-right: 152px;
    zoom: 1;
    &::before,
    &::after {
      content: ' ';
      display: table;
    }
    &::after {
      clear: both;
      height: 0;
      font-size: 0;
      visibility: hidden;
    }
  }

  .imgEle {
    float: right;
    width: 100%;
    max-width: 430px;
    height: 360px;
    background-repeat: no-repeat;
    background-position: 50% 50%;
    background-size: contain;
  }

  .content {
    flex: auto;

    h1 {
      margin-bottom: 24px;
      color: #434e59;
      font-weight: 600;
      font-size: 28px;
      line-height: 28px;
    }

    .desc {
      margin-bottom: 16px;
      color: @text-color-secondary;
      font-size: 16px;
      line-height: 16px;
    }

    .actions {
      button:not(:last-child) {
        margin-right: 8px;
      }
    }
  }
}

@media screen and (max-width: @screen-xl) {
  .exception {
    .imgBlock {
      padding-right: 88px;
    }
  }
}

@media screen and (max-width: @screen-sm) {
  .exception {
    display: block;
    text-align: center;
    .imgBlock {
      margin: 0 auto 24px;
      padding-right: 0;
    }
  }
}

@media screen and (max-width: @screen-xs) {
  .exception {
    .imgBlock {
      margin-bottom: -24px;
      overflow: hidden;
    }
  }
}
</style>
