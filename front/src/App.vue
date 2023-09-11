<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Header from './components/Header.vue'
import Content from './components/Content.vue'
</script>

<template>
  <Header />
  <Content></Content>
</template>

<style>
body {
  margin: 0;
  padding: 0;
  width: 100%;
  font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
  /*font-weight: 400;
  line-height: 1.6;*/
  color: #212529;
  text-align: left;
  background-color: #fff;}
</style>
