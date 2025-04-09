<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备号" prop="conductId">
        <el-input
          v-model="queryParams.conductId"
          placeholder="请输入设备号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册时间" prop="registrationTime">
        <el-date-picker clearable
          v-model="queryParams.registrationTime"
          type="date"
          value-format="YYYY-MM-DD HH:mm:ss"
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
          v-hasPermi="['esps:esps:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['esps:esps:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['esps:esps:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['esps:esps:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="espsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备id" align="center" prop="espId" />
      <el-table-column label="设备号" align="center" prop="conductId" />
      <el-table-column label="注册时间" align="center" prop="registrationTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.registrationTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['esps:esps:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['esps:esps:remove']">删除</el-button>
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

    <!-- 添加或修改ESP盒子对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="espsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备号" prop="conductId">
          <el-input v-model="form.conductId" placeholder="请输入设备号" />
        </el-form-item>
        <el-form-item label="注册时间" prop="registrationTime">
          <el-date-picker clearable
            v-model="form.registrationTime"
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

<script setup name="Esps">
import { listEsps, getEsps, delEsps, addEsps, updateEsps } from "@/api/esps/esps";
import {parseTime} from "@/utils/uking.js";

const { proxy } = getCurrentInstance();

const espsList = ref([]);
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
    conductId: null,
    registrationTime: null
  },
  rules: {
    conductId: [
      { required: true, message: "设备号不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询ESP盒子列表 */
function getList() {
  loading.value = true;
  listEsps(queryParams.value).then(response => {
    espsList.value = response.rows;
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
    espId: null,
    conductId: null,
    registrationTime: null
  };
  proxy.resetForm("espsRef");
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
  ids.value = selection.map(item => item.espId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加ESP盒子";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _espId = row.espId || ids.value
  getEsps(_espId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改ESP盒子";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["espsRef"].validate(valid => {
    if (valid) {
      if (form.value.espId != null) {
        updateEsps(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEsps(form.value).then(response => {
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
  const _espIds = row.espId || ids.value;
  proxy.$modal.confirm('是否确认删除ESP盒子编号为"' + _espIds + '"的数据项？').then(function() {
    return delEsps(_espIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('esps/esps/export', {
    ...queryParams.value
  }, `esps_${new Date().getTime()}.xlsx`)
}

getList();
</script>
