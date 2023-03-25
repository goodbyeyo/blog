<script setup lang="ts">
import {ref} from "vue";

const count = ref(0)
</script>

<template>
  <div>
    <el-input placeholder="제목을 입력하세요" />

    <div>
      <div>
        <el-input type="textarea" rows="15" placeholder="내용을 입력하세요" />
      </div>

      <div>
        <el-button type="primary"> 글 작성 완료</el-button>
      </div>
    </div>
  </div>
</template>

<style>

</style>
