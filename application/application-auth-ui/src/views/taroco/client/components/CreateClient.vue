<template>
  <a-modal
    :title="isAdd | titleFilter"
    :width="800"
    v-model="visible"
    @ok="handleOk"
    :destroyOnClose="true"
  >
    <a-form :form="form">

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="应用名称"
        hasFeedback
      >
        <a-input
          :disabled="!isAdd"
          v-decorator="[
            'appName',
            {rules: [{ required: true, max: 50, message: '请输入应用名称,50以内' }]
            }]"
        />
      </a-form-item>

      <a-form-item
        v-if="isAdd"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="应用密钥"
        hasFeedback
      >
        <a-input
          type="password"
          v-decorator="[
            'clientSecret',
            {rules: [{ required: true, max: 50, message: '请输入应用密钥,50以内' }]
            }]"
        />
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="授权类型"
        hasFeedback
      >
        <a-select
          mode="multiple"
          v-decorator="[
            'authorizedGrantTypes',
            {rules: [{ required: true, message: '请选择授权范围' }]}
          ]">
          <a-select-option value="password">password</a-select-option>
          <a-select-option value="authorization_code">authorization_code</a-select-option>
          <a-select-option value="refresh_token">refresh_token</a-select-option>
          <a-select-option value="client_credentials">client_credentials</a-select-option>
          <a-select-option value="implicit">implicit</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="权限范围"
        hasFeedback
      >
        <a-select
          mode="tags"
          v-decorator="[
            'scope',
            {rules: [{ required: true, message: '请选择权限范围' }]}
          ]">
          <a-select-option value="All">All</a-select-option>
          <a-select-option value="Read">Read</a-select-option>
          <a-select-option value="Write">Write</a-select-option>
          <a-select-option value="Server">Server</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="重定向地址"
        hasFeedback
      >
        <a-input
          v-decorator="[
            'webServerRedirectUri',
            {rules: [{ required: true, message: '请输入重定向地址' },
                     {pattern: /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/, message: '请输入合法的重定向地址'}]}
          ]"
        />
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="自动认证"
      >
        <a-radio-group
          v-decorator="[
            'autoapprove',
            { rules: [{ required: true, message: '请选择是否自动认证' }],
              initialValue: false
            }
          ]">
          <a-radio :value="true">是</a-radio>
          <a-radio :value="false">否</a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
  import {addClient, getClient, updateClient} from '@/api/client'

  export default {
  name: 'CreateClient',
  data () {
    return {
      clientId: null,
      visible: false,
      isAdd: true,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: this.$form.createForm(this)
    }
  },
  filters: {
    titleFilter (status) {
      const statusMap = {
        true: '新增',
        false: '编辑'
      }
      return statusMap[status]
    }
  },
  methods: {
    /**
     * 页面初始化
     */
    init (clientId) {
      const that = this
      this.isAdd = true
      this.resetForm()
      this.visible = true
      if (clientId) {
        this.clientId = clientId
        this.isAdd = false
        getClient(clientId).then(res => {
          if (res.status === 'SUCCEED') {
            that.$nextTick(() => {
              that.form.setFieldsValue({
                appName: res.result.appName,
                authorizedGrantTypes: res.result.authorizedGrantTypes,
                scope: res.result.scope,
                webServerRedirectUri: res.result.webServerRedirectUri,
                autoapprove: res.result.autoapprove
              })
            })
          }
        })
      }
    },
    handleOk () {
      this.form.validateFields((err, values) => {
        if (!err) {
          if (this.isAdd) {
            addClient(values).then(res => {
              if (res.status === 'SUCCEED') {
                this.$message.success('操作成功!')
                this.visible = false
                this.$emit('on-refresh')
              }
            })
          } else {
            updateClient(Object.assign({ clientId: this.clientId }, values)).then(res => {
              if (res.status === 'SUCCEED') {
                this.$message.success('操作成功!')
                this.visible = false
                this.$emit('on-refresh')
              }
            })
          }
        }
      })
    },
    /**
     * 重置表单
     */
    resetForm () {
      this.form.resetFields()
    }
  }
}
</script>
