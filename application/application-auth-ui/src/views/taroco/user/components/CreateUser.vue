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
        label="用户姓名"
        hasFeedback
      >
        <a-input
          :disabled="!isAdd"
          v-decorator="[
            'username',
            {rules: [{ required: true, max: 32, message: '请输入用户名称,32以内' }]
            }]"
        />
      </a-form-item>

      <a-form-item
        v-if="isAdd"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="用户密码"
        hasFeedback
      >
        <a-input
          type="password"
          v-decorator="[
            'password',
            {rules: [{ required: true, min: 8,max: 32, message: '请输入用户密码,8以上32以内' }]
            }]"
        />
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="用户昵称"
        hasFeedback
      >
        <a-input
          v-decorator="[
            'nickname',
            {rules: [{ required: true, max: 64, message: '请输入用户别名,64以内' }]
            }]"
        />
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="邮件"
        hasFeedback
      >
        <a-input
          v-decorator="
          ['email',
            {
              rules:
              [
                { required: true, max: 64, message: '请输入邮箱地址,256以内' },
                { pattern: /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/, message: '请输入合法的邮箱地址' }
              ]
            }
          ]"
        />
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="手机"
        hasFeedback
      >
        <a-input
          v-decorator="[
            'mobile',
            {
              rules: [
                { required: true, max: 64, message: '请输入手机号,11位' },
                { pattern: /^1(3|4|5|6|7|8|9)\d{9}$/, message: '请输入合法的手机号码' }
              ]
            }]"
        />
      </a-form-item>

      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="头像"
        hasFeedback
      >
        <a-input
          v-decorator="['avatar']"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
  import {addUser, getUser, updateUser} from '@/api/user'

  export default {
    name: 'CreateUser',
    data() {
      return {
        userId: null,
        visible: false,
        isAdd: true,
        labelCol: {
          xs: {span: 24},
          sm: {span: 5}
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16}
        },
        form: this.$form.createForm(this)
      }
    },
    filters: {
      titleFilter(status) {
        const statusMap = {
          true: '新增',
          false: '编辑'
        };
        return statusMap[status]
      }
    },
    methods: {
      /**
       * 页面初始化
       */
      init(userId) {
        const that = this;
        this.isAdd = true;
        this.resetForm();
        this.visible = true;
        if (userId.length !== 0) {
          this.userId = userId;
          this.isAdd = false;
          getUser(userId).then(res => {
            if (res.status === 'SUCCEED') {
              that.$nextTick(() => {
                that.form.setFieldsValue({
                  username: res.result.username,
                  nickname: res.result.nickname,
                  email: res.result.email,
                  mobile: res.result.mobile,
                  avatar: res.result.avatar
                })
              })
            }
          })
        }
      },
      handleOk() {
        this.form.validateFields((err, values) => {
          if (!err) {
            if (this.isAdd) {
              addUser(values).then(res => {
                if (res.status === 'SUCCEED') {
                  this.$message.success('操作成功!');
                  this.visible = false;
                  this.$emit('on-refresh')
                }
              })
            } else {
              updateUser(Object.assign({userId: this.userId}, values)).then(res => {
                if (res.status === 'SUCCEED') {
                  this.$message.success('操作成功!');
                  this.visible = false;
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
      resetForm() {
        this.form.resetFields()
      }
    }
  }
</script>
