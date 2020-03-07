<template>
  <a-card :bordered="false">
    <div style="margin-bottom: 18px;text-align:right;">
      <a-button type="default" icon="reload" @click="refreshList" style="margin-right:10px">刷新</a-button>
      <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
    </div>
    <s-table ref="table" :columns="columns" :data="loadData" :rowKey="record => record.userId">
      <span slot="avatar" slot-scope="text">
        <a>{{ text }}</a>
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit(record)">编辑</a>
        <a-divider type="vertical"/>
        <a @click="handleDelete(record)">删除</a>
      </span>
    </s-table>
    <CreateUser ref="CreateUser" @on-refresh="refreshList"/>
  </a-card>
</template>

<script>
  import {STable} from '@/components'
  import CreateUser from './components/CreateUser'
  import {deleteUser, getList} from '@/api/user'

  export default {
    name: 'TarocoUsers',
    components: {
      STable, CreateUser
    },
    data() {
      return {
        columns: [
          {
            title: '用户ID',
            dataIndex: 'userId'
          },
          {
            title: '用户姓名',
            dataIndex: 'username'
          },
          {
            title: '用户昵称',
            dataIndex: 'nickname'
          },
          {
            title: '手机号',
            dataIndex: 'mobile'
          },
          {
            title: '邮箱',
            dataIndex: 'email'
          },
          {
            title: '头像',
            dataIndex: 'avatar',
            scopedSlots: {customRender: 'avatar'}
          },
          {
            title: '操作',
            width: '150px',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'}
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          console.log(`loadData:${parameter}`);
          return getList({
            size: parameter.pageSize,
            current: parameter.pageNo
          }).then(res => {
            console.log(res);
            return res.result
          })
        }
      }
    },
    methods: {
      /**
       * 点击新增
       */
      handleAdd() {
        this.$refs.CreateUser.init()
      },
      /**
       * 编辑
       */
      handleEdit(record) {
        this.$refs.CreateUser.init(record.userId)
      },
      /**
       * 删除
       */
      handleDelete(record) {
        this.$confirm({
          title: '操作确认',
          content: '是否确认删除用户: ' + record.userName + ' ?',
          onOk: () => {
            deleteUser(record.userId).then(res => {
              if (res.status === 'SUCCEED') {
                this.$message.success('操作成功!');
                this.refreshList()
              }
            })
          }
        })
      },
      /**
       * 刷新列表
       */
      refreshList() {
        this.$refs.table.refresh(true)
      }
    }
  }
</script>
