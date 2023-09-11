<template>
  <el-main class="content">
    <el-row>
      <el-col>
        <RouterView />
      </el-col>
    </el-row>
  </el-main>
</template>

<script setup lang="ts"></script>

<style scoped>
  .content {
    height: calc(100vh - 60px);
    /*padding: 0;
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;*/
  }
</style>