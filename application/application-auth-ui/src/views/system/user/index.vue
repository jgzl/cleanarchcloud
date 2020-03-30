<template>
  <div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="userId"
        label="用户ID"
        width="150"
        type="expand"
      >
        <template slot-scope="props">
          <el-form label-position="left" inline class="user-table-expand">
            <el-form-item label="ID">
              <span>{{ props.row.userId }}</span>
            </el-form-item>
            <el-form-item label="用户名">
              <span>{{ props.row.username }}</span>
            </el-form-item>
            <el-form-item label="用户别名">
              <span>{{ props.row.nickname }}</span>
            </el-form-item>
            <el-form-item label="手机号">
              <span>{{ props.row.mobile }}</span>
            </el-form-item>
            <el-form-item label="邮件">
              <span>{{ props.row.email }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="120"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>用户名: {{ scope.row.username }}</p>
            <p>用户别名: {{ scope.row.nickname }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.username }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="别名"
        width="120"
      />
      <el-table-column
        prop="mobile"
        label="手机"
        width="120"
      />
      <el-table-column
        prop="email"
        label="邮箱"
        width="120"
      />
      <el-table-column
        prop="avatar"
        label="头像"
        width="300"
      >
        <template slot-scope="scope">
          <img class="avatar" :src="scope.row.avatar" min-width="70" height="70" alt="avatar"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="createUser"
        label="创建人"
        width="300"
      />
      <el-table-column
        prop="updateUser"
        label="更新人"
        width="300"
      />
      <el-table-column
        align="right"
        width="200"
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
  import {getList} from '@/api/user'

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
    width: 50%;
  }
</style>
