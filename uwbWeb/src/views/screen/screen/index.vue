<template>
  <div class="app-container">
    <el-card>
      <el-row gutter="20" align="middle">
        <el-col span="3">
          <el-page-header title=" " icon="HomeFilled">
            <template #content>
              <span class="screen_title">房间状态</span>
            </template>
          </el-page-header>
        </el-col>
        <el-divider direction="vertical" />
        <el-col span="2" >
          <el-tag color="#2c3e50" size="large" effect="dark" style="width: 110px;height: 40px">
            <div v-if="recent_record" class="titleStyle">{{recent_record.roomName}}</div>
          </el-tag>
        </el-col>
        <el-divider direction="vertical" />
        <el-col span="2" >
          <el-row align="middle">
            <div style="color: #c62828;font-size: 18px;font-weight: bold;margin-right: 5px; border-bottom: 2px solid #c62828; ">心率</div>
            <el-tag color="#b71c1c" size="large" effect="dark" style="width: 60px;height: 40px">
              <div v-if="recent_record" class="titleStyle">{{recent_record.heartRate}}</div>
            </el-tag>
          </el-row>
        </el-col>
        <el-divider direction="vertical" />
        <el-col span="3" >
          <el-row align="middle">
            <div style="color: #4527a0;font-size: 18px;font-weight: bold;margin-right: 5px; border-bottom: 2px solid #4527a0; ">呼吸率</div>
            <el-tag color="#311b92" size="large" effect="dark" style="width: 60px;height: 40px">
              <div v-if="recent_record" class="titleStyle">{{recent_record.respirationRate}}</div>
            </el-tag>
          </el-row>
        </el-col>
        <el-divider direction="vertical" />
        <el-col span="3" >
          <el-row align="middle">
            <div style="color: #263238;font-size: 18px;font-weight: bold;margin-right: 5px; border-bottom: 2px solid #4527a0; ">监测状态</div>
            <el-tag color="#546e7a" size="large" effect="dark" style="width: 60px;height: 40px">
              <div v-if="active_status && recent_record" class="titleStyle">
                <div v-for="dict in active_status">{{parseInt(dict.value) === recent_record.activeStatus ? dict.label : ''}}
                </div>
              </div>
            </el-tag>
          </el-row>
        </el-col>
        <el-col span="3" >
          <el-row align="middle">
            <el-tag color="#33691e" size="large" effect="dark" style="width: 60px;height: 40px">
              <div v-if="active_status && recent_record" class="titleStyle">
                  <div v-for="dict in present_state">{{parseInt(dict.value) === recent_record.presentState ? dict.label : ''}}
                </div>
              </div>
            </el-tag>
          </el-row>
        </el-col>
        <el-col span="3" >
          <el-row align="middle">
            <el-tag color="#37474f" size="large" effect="dark" style="width: 60px;height: 40px">
              <div v-if="active_status && recent_record" class="titleStyle">
                <div v-for="dict in vital_sign_abnormality">{{parseInt(dict.value) === recent_record.vitalSignAbnormality ? dict.label : ''}}
                </div>
              </div>
            </el-tag>
          </el-row>
        </el-col>
        <el-divider direction="vertical" />
        <el-col span="3" >
          <el-row align="middle">
            <div style="color: #130f40;font-size: 18px;font-weight: bold;margin-right: 5px; border-bottom: 2px solid #4527a0; ">信号强度</div>
              <div v-if="recent_record" class="titleStyle">{{recent_record.signalStrength}}
              </div>
          </el-row>
        </el-col>
        <el-divider direction="vertical" />
        <el-col span="3" >
          <el-row align="middle">
            <div style="color: #130f40;font-size: 18px;font-weight: bold;margin-right: 5px; ">更新时间</div>
            <div v-if="recent_record" class="titleStyle">{{recent_record.recordTime}}
            </div>
          </el-row>
        </el-col>
      </el-row>
    </el-card>
    <el-divider>-</el-divider>
    <el-row gutter="5">
      <el-col :span="16">
        <el-row gutter="5">
          <el-col :span="12"  class="form-item">
            <el-card id="heartContainer" style="height: 100%;width: 100%;padding-top: 10px">
            </el-card>
          </el-col>
          <el-col :span="12"  class="form-item">
            <el-card id="respirationContainer" style="height: 100%;width: 100%;padding-top: 10px">
            </el-card>
          </el-col>
        </el-row>
        <el-row gutter="5">
          <el-col :span="7"  class="small-item">
            <el-card id="strengthContainer" style="height: 100%;width: 100%;padding-top: 10px"></el-card>
          </el-col>
          <el-col :span="17"  class="small-item">
            <el-card id="nearestTargetDistanceContainer" style="height: 100%;width: 100%;padding-top: 10px"></el-card>
          </el-col>
        </el-row>
        <el-row gutter="5">
          <el-col :span="5"  class="small-item" >
            <div v-for="name in buttonsName" style="height:20%;width: 100%;align-items: center;justify-content: center;display: flex;flex-direction: column">
              <el-button type="primary" style="font-size:21px;width: 100%;height: 100%; max-width: 200px; margin-bottom: 10px; background-color: #003d74; border-color: #003d74;">{{name}}</el-button>
            </div>
          </el-col>
          <el-col :span="19"  class="small-item">
            <el-card id="allStateContainer" style="height: 100%;width: 100%;padding-top: 10px"></el-card>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="8"  class="form-item">
        <el-card>
          <el-page-header title=" " icon="View">
            <template #content>
              <span class="screen_title">  所有记录  </span>
            </template>
          </el-page-header>
          <br>
          <el-table height="70vh"  width="100%" border="border" size="small" table-layout="fixed" fixed="fixed" stripe="stripe" v-loading="loading" :data="radar_recordsList" @selection-change="handleSelectionChange">
            <el-table-column label="记录时间" align="center" prop="recordTime"/>
            <el-table-column label="心率" align="center" prop="heartRate" />
            <el-table-column label="呼吸率" align="center" prop="respirationRate" />
            <el-table-column label="存在状态" align="center" prop="presentState">
              <template #default="scope">
                <dict-tag :options="present_state" :value="scope.row.presentState"/>
              </template>
            </el-table-column>
            <el-table-column label="活动状态" align="center" prop="activeStatus">
              <template #default="scope">
                <dict-tag :options="active_status" :value="scope.row.activeStatus"/>
              </template>
            </el-table-column>
            <el-table-column label="最近距离" align="center" prop="nearestTargetDistance" />
            <el-table-column label="体征异常" align="center" prop="vitalSignAbnormality">
              <template #default="scope">
                <dict-tag :options="vital_sign_abnormality" :value="scope.row.vitalSignAbnormality"/>
              </template>
            </el-table-column>
            <el-table-column label="信号强度" align="center" prop="signalStrength" />
            <el-table-column label="持续在床" align="center" prop="durationOfTimeInBed" />
            <el-table-column label="持续离床" align="center" prop="durationOfTimeOutOfBed" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template #default="scope">
                <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['radar_records:radar_records:edit']">修改</el-button>
                <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['radar_records:radar_records:remove']">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
              v-show="total>0"
              :total="total"
              size="small"
              v-model:current-page="queryParams.pageNum"
              v-model:page-size="queryParams.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              @update:current-page="getList"
              @update:page-size="getList"
          />
        </el-card>
      </el-col>
    </el-row>


  </div>
</template>

<script setup name="Radar_records">
import { listRadar_records, getRadar_records, delRadar_records, addRadar_records, updateRadar_records } from "@/api/radar_records/radar_records";
import {Warning} from "@element-plus/icons-vue";
import Default from "vuedraggable";
import {listEsps} from "@/api/esps/esps.js";
import * as echarts from "echarts";
const buttonsName = ["导出记录","通知预警","重置默认","手动刷新"]
const { proxy } = getCurrentInstance();
const { active_status } = proxy.useDict('active_status');
const { present_state } = proxy.useDict('present_state');
const { vital_sign_abnormality } = proxy.useDict('vital_sign_abnormality');
const espRadarRecord = ref([]);
const radar_recordsList = ref([]);
const echart_data = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const recent_record = computed(() => {
  return echart_data.value[echart_data.value.length-1];
});
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 20,
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

let heartChart;
let respirationChart;
let strengthChart;
let nearestTargetDistanceChart;
let allStateChart;
const heartOption = ref({})
const respirationOption = ref({})
const strengthOption = ref({})
const nearestTargetDistanceOption = ref({})
const allStateOption = ref({})

/** 查询雷达记录列表 */
async function getList() {
  loading.value = true;
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
  queryParams.value.espId = data.value[0].espId;
  await listRadar_records(queryParams.value).then(response => {
    response.rows.sort((b, a) => new Date(a.recordTime) - new Date(b.recordTime));
    radar_recordsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  })
  const queryAll = {
    pageSize: 1000,
    pageNum: 1,
  }
  await listRadar_records(queryAll).then(response => {
    response.rows.sort((a, b) => new Date(a.recordTime) - new Date(b.recordTime));
    echart_data.value = response.rows;
  })
  const time_data = echart_data.value.map(function (item) {
    return item.recordTime;
  })
  const heart_data = echart_data.value.map(function (item) {
    return item.heartRate;
  });
  const respiration_data = echart_data.value.map(function (item) {
    return item.respirationRate;
  })
  const nearestTargetDistance_data = echart_data.value.map(function (item) {
    return item.nearestTargetDistance;
  })
  const presentState_data = echart_data.value.map(function (item) {
    return item.presentState;
  })
  const activeState_data = echart_data.value.map(function (item) {
    return item.activeStatus;
  })
  const vitalSignAbnormality_data = echart_data.value.map(function (item) {
    return item.vitalSignAbnormality;
  })

  heartOption.value = {
    title: {
      text: '心率变化',
      left: '1%',
      textStyle: {
        fontSize: 25,
        fontWeight: 'bold',
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    grid: {
      left: '5%',
      right: '15%',
      bottom: '10%'
    },
    xAxis: {
      data: time_data
    },
    yAxis: {},
    toolbox: {
      right: 10,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        restore: {},
        saveAsImage: {}
      }
    },
    dataZoom: [
      {
        startValue: time_data[time_data.length-200],
      },
      {
        type: 'inside'
      }
    ],
    visualMap: {
      top: 50,
      right: 0,
      pieces: [
        {
          gt: 0,
          lte: 50,
          color: '#1A237E',
          label: '极低心率'
        },
        {
          gt: 50,
          lte: 60,
          color: '#4DB6AC',
          label: '偏低心率'
        },
        {
          gt: 60,
          lte: 90,
          color: '#66BB6A',
          label: '静息心率'
        },
        {
          gt: 90,
          lte: 120,
          color: '#FFA726',
          label: '偏高心率'
        },
        {
          gt: 120,
          color: '#EF5350',
          label: '危险心率'
        }
      ],
      outOfRange: {
        color: '#999'
      }
    },
    series: {
      name: '心率 Heart Rate',
      type: 'line',
      data: heart_data,
      markLine: {
        silent: true,
        lineStyle: {
          color: '#333'
        },
        data: [
          {
            yAxis: 50
          },
          {
            yAxis: 60
          },
          {
            yAxis: 90
          },
          {
            yAxis: 120
          }
        ]
      }
    }
  };
  respirationOption.value = {
    title: {
      text: '呼吸率变化',
      left: '1%',
      textStyle: {
        fontSize: 25,
        fontWeight: 'bold',
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    grid: {
      left: '5%',
      right: '15%',
      bottom: '10%'
    },
    xAxis: {
      data: time_data
    },
    yAxis: {},
    toolbox: {
      right: 10,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        restore: {},
        saveAsImage: {}
      }
    },
    dataZoom: [
      {
        startValue: time_data[time_data.length-200],
      },
      {
        type: 'inside'
      }
    ],
    visualMap: {
      top: 50,
      right: 0,
      pieces: [
        {
          gt: 0,
          lte: 10,
          color: '#1A237E',
          label: '严重过缓'
        },
        {
          gt: 10,
          lte: 15,
          color: '#4DB6AC',
          label: '轻度缓慢'
        },
        {
          gt: 15,
          lte: 22,
          color: '#66BB6A',
          label: '静息速率'
        },
        {
          gt: 22,
          color: '#FF7043',
          label: '呼吸急促'
        }
      ],
      outOfRange: {
        color: '#999'
      }
    },
    series: {
      name: '呼吸率 Respiration Rate',
      type: 'line',
      data: respiration_data,
      markLine: {
        silent: true,
        lineStyle: {
          color: '#333'
        },
        data: [
          {
            yAxis: 10
          },
          {
            yAxis: 15
          },
          {
            yAxis: 22
          }
        ]
      }
    }
  };
  strengthOption.value = {
    title: {
      text: '信号强度',
      left: '1%',
      textStyle: {
        fontSize: 25,
        fontWeight: 'bold',
      }
    },
    tooltip: {
      formatter: '{a} <br/>{b} : {c}'
    },
    series: [
      {
        name: '信号强度',
        type: 'gauge',
        min: -100, // 起始值
        max: 100, // 结束值
        progress: {
          show: true
        },
        detail: {
          valueAnimation: true,
          formatter: '{value}',
          fontSize: 20, // 设置详情区域字体大小
          offsetCenter: [0, 80] // 设置详情区域位置
        },
        data: [
          {
            value: recent_record.value.signalStrength,
            name: '信号强度'
          }
        ]
      }
    ]};
  nearestTargetDistanceOption.value = {
    visualMap: [
      {
        show: false,
        type: 'continuous',
        seriesIndex: 0,
        min: Math.min(...nearestTargetDistance_data),
        max: Math.max(...nearestTargetDistance_data),
        inRange: {
          color: ['#622774', '#c53364'] // 渐变颜色为深红色基色
        }
      }
    ],
    legend: {
      data: ['最近目标距离']
    },
    title: {
      left: '1%',
      text: '最近目标距离变化',
      textStyle: {
        fontSize: 25,
        fontWeight: 'bold',
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      data: time_data
    },
    yAxis: {},
    grid: {
      bottom: '20%'
    },
    dataZoom: [
      {
        startValue: time_data[time_data.length-200],
      },
      {
        type: 'inside'
      }
    ],
    series: [
      {
        name: '最近目标距离',
        type: 'line',
        showSymbol: false,
        data: nearestTargetDistance_data,
        itemStyle: {
          color: '#622774' // 深红色
        },
      }
    ]}
  allStateOption.value = {
    title: {
      left: '1%',
      text: '所有监测状态变化'
    },
    visualMap: [{
      top: 50,
      right: 0,
      pieces: [
        {
          gt: 0,
          lte: 1,
          color: '#2a6e3f',
          label: '无人/正常'
        },
        {
          gt: 1,
          lte: 2,
          color: '#06436f',
          label: '有人/静息'
        },
        {
          gt: 2,
          lte: 3,
          color: '#007175',
          label: '信号弱/安静'
        },
        {
          gt: 3,
          lte: 4,
          color: '#d08635',
          label: '动作'
        },
        {
          gt: 4,
          lte: 5,
          color: '#7c191e',
          label: '持续动作/无信号'
        }
      ],
      outOfRange: {
        color: '#999'
      }
    }],
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['存在状态', '活动状态', '生命体征异常']
    },
    xAxis: {
      data: time_data
    },
    yAxis: {},
    grid: {
      bottom: '20%'
    },
    dataZoom: [
      {
        type: 'slider',
        startValue: time_data[time_data.length-200],
      },
      {
        type: 'inside',
        startValue: time_data[time_data.length-200],
      }
    ],
    series: [
      {
        name: '存在状态',
        type: 'line',
        showSymbol: true,
        data: presentState_data,
        itemStyle: {
          color: '#ff4d4f' // 深红色
        },
      },
      {
        name: '活动状态',
        type: 'line',
        showSymbol: true,
        data: activeState_data,
        itemStyle: {
          color: '#69c0ff' // 浅蓝色
        }
      },
      {
        name: '生命体征异常',
        type: 'line',
        showSymbol: true,
        data: vitalSignAbnormality_data,
        itemStyle: {
          color: '#ffc53d' // 黄色
        }
      }
    ]
  };
  heartChart.setOption(heartOption.value);
  heartChart.resize();
  respirationChart.setOption(respirationOption.value);
  respirationChart.resize();
  strengthChart.setOption(strengthOption.value);
  strengthChart.resize();
  nearestTargetDistanceChart.setOption(nearestTargetDistanceOption.value);
  nearestTargetDistanceChart.resize();
  allStateChart.setOption(allStateOption.value);
  allStateChart.resize();


}

onMounted(() => {
  let loadingOption = {
    text: 'Loading...',
    color: '#c23531',
    textColor: '#000',
    maskColor: 'rgba(255, 255, 255, 0.8)',
    zlevel: 0
  };

  const dom = document.getElementById('heartContainer');
  heartChart = echarts.init(dom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  });
  const respirationDom = document.getElementById('respirationContainer');
  respirationChart = echarts.init(respirationDom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  });
  const strengthDom = document.getElementById('strengthContainer');
  strengthChart = echarts.init(strengthDom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  })
  const nearestTargetDistanceDom = document.getElementById('nearestTargetDistanceContainer');
  nearestTargetDistanceChart = echarts.init(nearestTargetDistanceDom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  })
  const allStateDom = document.getElementById('allStateContainer');
  allStateChart = echarts.init(allStateDom, null, {
    renderer: "canvas",
    useDirtyRect: false
  })
  heartChart.showLoading(loadingOption);
  respirationChart.showLoading(loadingOption);
  strengthChart.showLoading(loadingOption);
  nearestTargetDistanceChart.showLoading(loadingOption);
  allStateChart.showLoading(loadingOption);

 setTimeout(function (){
   heartChart.hideLoading();
   respirationChart.hideLoading();
   strengthChart.hideLoading();
   nearestTargetDistanceChart.hideLoading();
   allStateChart.hideLoading();
   heartChart.setOption(heartOption.value);
   respirationChart.setOption(respirationOption.value);
   strengthChart.setOption(strengthOption.value);
   nearestTargetDistanceChart.setOption((nearestTargetDistanceOption.value));
   allStateChart.setOption(allStateOption.value)

   if (heartOption && typeof heartOption === 'object') {
     heartChart.setOption(heartOption.value);
   }
   window.addEventListener('resize', heartChart.resize);

   if (respirationOption && typeof respirationOption === 'object') {
     respirationChart.setOption(respirationOption.value);
   }
   window.addEventListener('resize', respirationChart.resize);

   if (strengthOption && typeof strengthOption === 'object') {
     strengthChart.setOption(strengthOption.value);
   }
   window.addEventListener('resize', strengthChart.resize);

   if (nearestTargetDistanceOption && typeof nearestTargetDistanceOption === 'object') {
     nearestTargetDistanceChart.setOption(nearestTargetDistanceOption.value);
   }
   window.addEventListener('resize', nearestTargetDistanceChart.resize);

   if (allStateOption && typeof allStateOption === 'object') {
     allStateChart.setOption(allStateOption.value);
   }
   window.addEventListener('resize', allStateChart.resize);
 },2000)

  setInterval(() => {
    getList()
  }, 1500); // 1000 毫秒 = 1 秒
})
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
<script>


</script>
<style scoped>
.data-card {
  font-family: Arial, sans-serif;
}
.form-item {

  height: 30vh;
}
.small-item{
  display: flex;
  flex-direction: column;

  justify-content: center;
  align-items: center;
  height: 25vh;
}
.data-value {
  margin-right: 20px;
}
.screen_title{
  font-family: sans-serif;
  font-size: 25px;
  font-weight: bold;
}

.status {
  margin-right: 10px;
}

.data-value.status {
  display: flex;
  align-items: center;
  gap: 2px;
}
.form-item.bordered-col {
  border: 1px solid #000000;
  border-radius: 1px;
}
.titleStyle {
  font-size: 25px;
  font-weight: bold;
  font-family: Arial;
  text-align: center;
}
</style>
