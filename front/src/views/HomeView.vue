<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter()
const posts = ref([ ]);

axios.get('/api/posts?page=1&size=5').then((response) => {
  console.log(response.data)
  response.data.forEach((r: any) => {
    posts.value.push(r)
  })
});

const moveToRead = () => {
  console.log("moveToRead")
  router.push({ name: "read" })
}
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
    <!-- a 태그를 사용하면 전체 태그를 불러오기때문에 리소스 낭비가 심하다 따라서 router-link 사용 -->
    <!--  <li v-for="post in posts" :key="post.id" @click="moveToRead()"> -->
      <div class="title">
        <router-link :to="{ name: 'read', params: { postId: post.id } }">
          {{post.title}}
        </router-link>
      </div>
      <div class="content">
          {{post.content}}
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2021-08-01</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      line-height: 1.4;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}
</style>