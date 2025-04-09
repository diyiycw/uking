<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="注册时间" prop="registerTime">
        <el-date-picker clearable
          v-model="queryParams.registerTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择注册时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['room_user:room_user:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['room_user:room_user:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['room_user:room_user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['room_user:room_user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="room_userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="房间id" align="center" prop="roomId" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="注册时间" align="center" prop="registerTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.registerTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['room_user:room_user:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['room_user:room_user:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改房间用户对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="room_userRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="注册时间" prop="registerTime">
          <el-date-picker clearable
            v-model="form.registerTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择注册时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Room_user">
import { listRoom_user, getRoom_user, delRoom_user, addRoom_user, updateRoom_user } from "@/api/room_user/room_user";

const { proxy } = getCurrentInstance();

const room_userList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    registerTime: null
  },
  rules: {
    registerTime: [
      { required: true, message: "注册时间不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询房间用户列表 */
function getList() {
  loading.value = true;
  listRoom_user(queryParams.value).then(response => {
    room_userList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    roomId: null,
    userId: null,
    registerTime: null
  };
  proxy.resetForm("room_userRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.roomId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加房间用户";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _roomId = row.roomId || ids.value
  getRoom_user(_roomId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改房间用户";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["room_userRef"].validate(valid => {
    if (valid) {
      if (form.value.roomId != null) {
        updateRoom_user(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRoom_user(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _roomIds = row.roomId || ids.value;
  proxy.$modal.confirm('是否确认删除房间用户编号为"' + _roomIds + '"的数据项？').then(function() {
    return delRoom_user(_roomIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('room_user/room_user/export', {
    ...queryParams.value
  }, `room_user_${new Date().getTime()}.xlsx`)
}

getList();
</script>
