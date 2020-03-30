<template>
  <div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="clientId"
        label="id"
        width="150"
        type="expand"
      >
        <template slot-scope="props">
          <el-form label-position="left" inline class="user-table-expand">
            <el-form-item label="appId">
              <span>{{ props.row.clientId }}</span>
            </el-form-item>
            <el-form-item label="appSecret">
              <span>{{ props.row.clientSecret }}</span>
            </el-form-item>
            <el-form-item label="客户端名称">
              <span>{{ props.row.appName }}</span>
            </el-form-item>
            <el-form-item label="redirect url">
              <span>{{ props.row.webServerRedirectUri }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        prop="clientId"
        label="客户端Id"
        width="150"
      />
      <el-table-column
        prop="clientSecret"
        label="客户端Secret"
        width="150"
      />
      <el-table-column
        prop="appName"
        label="客户端名称"
        width="150"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>客户端名称: {{ scope.row.appName }}</p>
            <p>客户端ID: {{ scope.row.clientId }}</p>
            <p>客户端Secret: {{ scope.row.clientSecret }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.appName }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="webServerRedirectUri"
        label="重定向web url"
        width="500"
      />
      <el-table-column
        align="right"
        width="180"
        fixed="right"
      >
        <template slot="header">
          <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"
          />
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >
            Edit
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
  </div>
</template>

<script>
  import Pagination from '@/components/Pagination'
  import {getList} from '@/api/oauthclient'

  export default {
    components: {Pagination},
    data() {
      return {
        total: 0,
        list: [],
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10
        },
        search: ''
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true;
        getList(this.listQuery).then(response => {
          console.log(response.data);
          this.list = response.data.records;
          this.total = response.data.total;
          setTimeout(() => {
            this.listLoading = false
          }, 1.5 * 1000)
        })
      },
      handleEdit(index, row) {
        console.log(index, row)
      },
      handleDelete(index, row) {
        console.log(index, row)
      }
    }
  }
</script>
<style>
  .user-table-expand {
    font-size: 0;
  }

  .user-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .user-table-expand .el-form-item {
    margin-bottom: 0;
    width: 100%;
  }
</style>
