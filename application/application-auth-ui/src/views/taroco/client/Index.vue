<template>
  <a-card :bordered="false">
    <div style="margin-bottom: 18px;text-align:right;">
      <a-button type="default" icon="reload" @click="refreshList" style="margin-right:10px">刷新</a-button>
      <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
    </div>
    <s-table ref="table" :columns="columns" :data="loadData" :rowKey="record => record.clientId">
      <span slot="authorizedGrantTypes" slot-scope="text, record">
        <a-tag v-for="(action, index) in record.authorizedGrantTypes" :key="index" color="blue">{{ action }}</a-tag>
      </span>
      <span slot="scopes" slot-scope="text, record">
        <a-tag v-for="(action, index) in record.scope" :key="index" color="green">{{ action }}</a-tag>
      </span>
      <span slot="webServerRedirectUri" slot-scope="text">
        <a>{{ text }}</a>
      </span>
      <span slot="autoapprove" slot-scope="text">
        <template v-if="text">是</template>
        <template v-else>否</template>
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit(record)">编辑</a>
        <a-divider type="vertical" />
        <a @click="handleDelete(record)">删除</a>
      </span>
    </s-table>
    <CreateClient ref="CreateClient" @on-refresh="refreshList"/>
  </a-card>
</template>

<script>
  import {STable} from '@/components'
  import CreateClient from './components/CreateClient'
  import {deleteClient, getList} from '@/api/client'

  export default {
  name: 'TarocoClients',
  components: {
    STable, CreateClient
  },
  data () {
    return {
      columns: [
        {
          title: '应用名称',
          dataIndex: 'appName'
        },
        {
          title: '应用ID',
          dataIndex: 'clientId'
        },
        {
          title: '授权类型',
          dataIndex: 'authorizedGrantTypes',
          scopedSlots: { customRender: 'authorizedGrantTypes' }
        },
        {
          title: '权限范围',
          dataIndex: 'scope',
          scopedSlots: { customRender: 'scopes' }
        },
        {
          title: '重定向地址',
          dataIndex: 'webServerRedirectUri',
          scopedSlots: { customRender: 'webServerRedirectUri' }
        },
        {
          title: '自动认证',
          dataIndex: 'autoapprove',
          scopedSlots: { customRender: 'autoapprove' }
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getList({
          size: parameter.pageSize,
          current: parameter.pageNo
        }).then(res => {
          return res.result
        })
      }
    }
  },
  methods: {
    /**
     * 点击新增
     */
    handleAdd () {
      this.$refs.CreateClient.init()
    },
    /**
     * 编辑
     */
    handleEdit (record) {
      this.$refs.CreateClient.init(record.clientId)
    },
    /**
     * 删除
     */
    handleDelete (record) {
      this.$confirm({
        title: '操作确认',
        content: '是否确认删除应用: ' + record.appName + ' ?',
        onOk: () => {
          deleteClient(record.clientId).then(res => {
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
    refreshList () {
      this.$refs.table.refresh(true)
    }
  }
}
</script>
