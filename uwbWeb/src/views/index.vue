<template>
  <div class="home">
    <!-- 首页欢迎语 -->
    <h2>欢迎来到养老服务企业数据监控平台</h2>
    <p>在这里，我们通过融合移动端信息和传感器数据，为您提供实时的数据监控和提醒服务，确保您的养老服务更加高效和智能。</p>

    <!-- 特色服务推荐 -->
    <h2>特色服务推荐</h2>
    <ul>
      <li>实时数据监控：通过传感器和移动端设备，实时监控老人的健康状况。</li>
      <li>智能提醒服务：根据监控数据，智能提醒您需要关注的事项。</li>
      <li>数据分析报告：提供详细的健康数据分析报告，帮助您更好地了解老人的需求。</li>
    </ul>

    <!-- 最新版本更新日志 -->
    <h2>最新版本更新日志</h2>
    <blockquote>版本 {{ version }}</blockquote>
    <hr>
    <div class="update-log">
      <ol>
        <li>新增了实时数据监控功能，通过传感器和移动端设备实时监控老人健康状况。</li>
        <li>优化了智能提醒服务，提供更精准的提醒信息。</li>
        <li>增加了数据分析报告功能，帮助您更好地了解老人的需求。</li>
      </ol>
    </div>
  </div>
</template>

<script setup name="Index">
const version = ref('3.8.7')

function goTarget(url) {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">
.home {
  margin-left: 10px;
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
