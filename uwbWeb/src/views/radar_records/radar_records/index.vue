<template>
  <div class="app-container">

    <el-page-header title=" " icon="HomeFilled">
      <template #content>
        <span style="font-size: 25px;font-weight: bold;">  房间设备实时状态  </span>
      </template>
    </el-page-header>
    <el-row>
      <el-col v-for="esp in espRadarRecord" :span="1.5" style="margin: 10px">
        <el-card class="data-card" v-if="esp">
          <el-row gutter="5px">
            <el-col :span="1.5">
              <div class="data-item">
                <el-tag color="#D15B00" size="large" effect="dark">
                  <div class="roomName">
                    {{esp.recentValue.roomName}}
                  </div>
                </el-tag>
              </div>
            </el-col>
            <el-col :span="1.5">
              <div class="data-item">
                <el-statistic :value-style="valueStyleTime" :value="esp.recentValue.recordTime">
                  <template #title>
                    <div style="">
                      最近记录时间 Last updated time
                    </div>
                  </template>
                </el-statistic>
              </div>
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="1.5">
              <div class="data-item">
                <div class="data-item bordered-col">
                  <el-statistic :value="esp.recentValue.heartRate">
                  <template #title>
                    <div style="display: inline-flex; align-items: center">
                      心率
                      <el-tooltip
                          effect="dark"
                          content="次/分"
                          placement="top"
                      >
                        <el-icon style="margin-left: 4px" :size="12">
                          <Warning />
                        </el-icon>
                      </el-tooltip>
                    </div>
                  </template>
                </el-statistic>
                </div>
                <div class="data-item bordered-col">
                  <el-statistic :value="esp.recentValue.respirationRate">
                  <template #title>
                    <div style="display: inline-flex; align-items: center">
                      呼吸率
                      <el-tooltip
                          effect="dark"
                          content="次/分"
                          placement="top"
                      >
                        <el-icon style="margin-left: 4px" :size="12">
                          <Warning />
                        </el-icon>
                      </el-tooltip>
                    </div>
                  </template>
                </el-statistic>
                </div>
              </div>
              <div class="data-item">
                <span class="data-value status">
                  <dict-tag :options="present_state" :value="esp.recentValue.presentState"/>
                  <dict-tag :options="active_status" :value="esp.recentValue.activeStatus"/>
                  <dict-tag :options="vital_sign_abnormality" :value="esp.recentValue.vitalSignAbnormality"/>
                </span>
              </div>
            </el-col>
            <el-col :span="1.5">
              <div class="data-item" style="border-bottom: 1px solid #888888;padding-bottom: 6px">
                <el-statistic title="最近目标距离" :value="esp.recentValue.nearestTargetDistance">
                  <template #suffix>
                    <el-text style="vertical-align: -0.125em">cm
                    </el-text>
                  </template>
                </el-statistic>
                <el-statistic title="信号强度" :value="esp.recentValue.signalStrength">
                  <template #suffix>
                    <el-text style="vertical-align: -0.125em">db
                    </el-text>
                  </template>
                </el-statistic>
              </div>
              <div class="data-item" style="border-bottom: 1px solid #888888;padding-bottom: 6px">
                <el-statistic title="持续在床" :value="esp.recentValue.durationOfTimeInBed">
                  <template #suffix>
                    <el-text style="vertical-align: -0.125em">分钟
                    </el-text>
                  </template>
                </el-statistic>
                <el-statistic title="持续离床" :value="esp.recentValue.durationOfTimeOutOfBed">
                  <template #suffix>
                    <el-text style="vertical-align: -0.125em">分钟
                    </el-text>
                  </template>
                </el-statistic>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-page-header title=" " icon="View">
      <template #content>
        <span style="font-size: 25px;font-weight: bold;">  雷达记录全览  </span>
      </template>
    </el-page-header>
    <br>
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="心率区间筛选" prop="heartRate">
        <el-input
            v-model="queryParams.heartRate"
            placeholder="请输入心率"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="呼吸率区间筛选" prop="respirationRate">
        <el-input
            v-model="queryParams.respirationRate"
            placeholder="请输入呼吸率"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="存在状态" prop="presentState">
        <el-select v-model="queryParams.presentState" placeholder="请选择人体存在状态" clearable>
          <el-option
              v-for="dict in present_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动状态" prop="activeStatus">
        <el-select v-model="queryParams.activeStatus" placeholder="请选择人体活动状态" clearable>
          <el-option
              v-for="dict in active_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="目标距离区间筛选" prop="nearestTargetDistance">
        <el-input
            v-model="queryParams.nearestTargetDistance"
            placeholder="请输入最近目标距离"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="体征状态" prop="vitalSignAbnormality">
        <el-select v-model="queryParams.vitalSignAbnormality" placeholder="请选择体征状态状态" clearable>
          <el-option
              v-for="dict in vital_sign_abnormality"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['radar_records:radar_records:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['radar_records:radar_records:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['radar_records:radar_records:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['radar_records:radar_records:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="radar_recordsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ESP号" align="center" prop="espId" />
      <el-table-column label="记录时间" align="center" prop="recordTime" />
      <el-table-column label="心率" align="center" prop="heartRate" />
      <el-table-column label="呼吸率区间筛选" align="center" prop="respirationRate" />
      <el-table-column label="人体存在状态" align="center" prop="presentState">
        <template #default="scope">
          <dict-tag :options="present_state" :value="scope.row.presentState"/>
        </template>
      </el-table-column>
      <el-table-column label="人体活动状态" align="center" prop="activeStatus">
        <template #default="scope">
          <dict-tag :options="active_status" :value="scope.row.activeStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="最近目标距离" align="center" prop="nearestTargetDistance" />
      <el-table-column label="生命体征异常" align="center" prop="vitalSignAbnormality">
        <template #default="scope">
          <dict-tag :options="vital_sign_abnormality" :value="scope.row.vitalSignAbnormality"/>
        </template>
      </el-table-column>
      <el-table-column label="信号强度" align="center" prop="signalStrength" />
      <el-table-column label="持续在床时长" align="center" prop="durationOfTimeInBed" />
      <el-table-column label="持续离床时长" align="center" prop="durationOfTimeOutOfBed" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['radar_records:radar_records:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['radar_records:radar_records:remove']">删除</el-button>
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

    <!-- 添加或修改雷达记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="radar_recordsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="心率" prop="heartRate">
          <el-input v-model="form.heartRate" placeholder="请输入心率" />
        </el-form-item>
        <el-form-item label="呼吸率" prop="respirationRate">
          <el-input v-model="form.respirationRate" placeholder="请输入呼吸率" />
        </el-form-item>
        <el-form-item label="人体存在状态" prop="presentState">
          <el-radio-group v-model="form.presentState">
            <el-radio
                v-for="dict in present_state"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="人体活动状态" prop="activeStatus">
          <el-radio-group v-model="form.activeStatus">
            <el-radio
              v-for="dict in active_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="最近目标距离" prop="nearestTargetDistance">
          <el-input v-model="form.nearestTargetDistance" placeholder="请输入最近目标距离" />
        </el-form-item>
        <el-form-item label="生命体征异常" prop="vitalSignAbnormality">
          <el-radio-group v-model="form.vitalSignAbnormality">
            <el-radio
                v-for="dict in vital_sign_abnormality"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="信号强度" prop="signalStrength">
          <el-input v-model="form.signalStrength" placeholder="请输入信号强度" />
        </el-form-item>
        <el-form-item label="持续在床时长" prop="durationOfTimeInBed">
          <el-input v-model="form.durationOfTimeInBed" placeholder="请输入持续在床时长" />
        </el-form-item>
        <el-form-item label="持续离床时长" prop="durationOfTimeOutOfBed">
          <el-input v-model="form.durationOfTimeOutOfBed" placeholder="请输入持续离床时长" />
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

<script setup name="Radar_records">
import { listRadar_records, getRadar_records, delRadar_records, addRadar_records, updateRadar_records } from "@/api/radar_records/radar_records";
import {Warning} from "@element-plus/icons-vue";
import Default from "vuedraggable";
import {listEsps} from "@/api/esps/esps.js";

const { proxy } = getCurrentInstance();
const { active_status } = proxy.useDict('active_status');
const { present_state } = proxy.useDict('present_state');
const { vital_sign_abnormality } = proxy.useDict('vital_sign_abnormality');
const espRadarRecord = ref([]);
const radar_recordsList = ref([]);
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
    heartRate: null,
    respirationRate: null,
    presentState: null,
    activeStatus: null,
    nearestTargetDistance: null,
    vitalSignAbnormality: null,
    signalStrength: null,
    durationOfTimeInBed: null,
    durationOfTimeOutOfBed: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);
const valueStyleTime = {
  'font-size': '16px',
}
/** 查询雷达记录列表 */
async function getList() {
  loading.value = true;
  listRadar_records(queryParams.value).then(response => {
    radar_recordsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
  const queryParamsESP = {
    pageNum: 1,
    pageSize: 10,
    conductId: null,
    registrationTime: null
  }
  const data = ref([])
  await listEsps(queryParamsESP).then(response => {
    data.value = response.rows;
  });
  for(let i = 0; i < data.value.length; i++){
    const esp = data.value[i];
    espRadarRecord.value[i] = {
      espId: null,
      recentValue: {
        espId: null,
        heartRate: null,
        respirationRate: null,
        presentState: null,
        activeStatus: null,
        nearestTargetDistance: null,
        vitalSignAbnormality: null,
        signalStrength: null,
        durationOfTimeInBed: null,
        durationOfTimeOutOfBed: null,
        recordTime: null,
        roomId: null,
        roomName: null,
      },
    };
    espRadarRecord.value[i].espId = esp.espId;
    await listRadar_records({espId: esp.espId}).then(response => {
      if (response.rows.length > 0) {
        // 获取最近一次记录，多个记录值中的时间recordTime字段最近的一条
        response.rows.sort((a, b) => new Date(b.recordTime) - new Date(a.recordTime));
        espRadarRecord.value[i].recentValue = response.rows[0];
        console.log(espRadarRecord.value[i].recentValue)
      }
    });
  }
  console.log(espRadarRecord.value)
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
    recordTime: null,
    heartRate: null,
    respirationRate: null,
    presentState: null,
    activeStatus: null,
    nearestTargetDistance: null,
    vitalSignAbnormality: null,
    signalStrength: null,
    durationOfTimeInBed: null,
    durationOfTimeOutOfBed: null
  };
  proxy.resetForm("radar_recordsRef");
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
  title.value = "添加雷达记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _espId = row.espId || ids.value
  getRadar_records(_espId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改雷达记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["radar_recordsRef"].validate(valid => {
    if (valid) {
      if (form.value.espId != null) {
        updateRadar_records(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRadar_records(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除雷达记录编号为"' + _espIds + '"的数据项？').then(function() {
    return delRadar_records(_espIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('radar_records/radar_records/export', {
    ...queryParams.value
  }, `radar_records_${new Date().getTime()}.xlsx`)
}

getList();
</script>
<style scoped>.data-card {
  font-family: Arial, sans-serif;
}

.data-item {
  display: flex;
  align-items: center;
  margin-bottom: 3px;
  margin-right: 5px;
  gap: 5px;
}
.data-value {
  margin-right: 20px;
}


.status {
  margin-right: 10px;
}

.data-value.status {
  display: flex;
  align-items: center;
  gap: 2px;
}
.data-item.bordered-col {
  border: 2px solid #9d9d9d;
  padding: 11px;
  border-radius: 4px;
  margin-bottom: 3px;
}
.roomName {
  font-size: 20px;
  font-weight: bold;
  font-family: "Microsoft YaHei",serif;
  text-align: center;
  width: 70px;
}
</style>
