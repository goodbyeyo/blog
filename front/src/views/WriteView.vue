<script setup lang="ts">
import {ref} from "vue"
import axios from "axios"
import {useRouter} from "vue-router";

const title = ref("")
const content = ref("")
const router = useRouter()

const write = () => {
  axios
      .post("/api/posts", {
        title: title.value,
        content: content.value,
      })
      .then(() => {
        router.replace({ name: "home" })
      });
};

</script>

<template>
  <div>
    <el-input v-model="title" placeholder="제목을 입력하세요" />

    <div>
      <div>
        <el-input type="textarea" v-model="content" rows="15" placeholder="내용을 입력하세요" />
      </div>

      <div>
        <el-button type="primary" @click="write()"> 글 작성 완료</el-button>
      </div>
    </div>
  </div>
</template>

<style>

</style>
