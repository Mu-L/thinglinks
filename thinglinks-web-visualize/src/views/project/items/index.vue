<template>
  <div class="go-project-mt-template">
    <project-items-list :is-my-project="false"></project-items-list>
  </div>
</template>

<script setup lang="ts">
import { ProjectItemsList } from './components/ProjectItemsList'
</script>

<style lang="scss" scoped>
@include go(project-mt-template) {
  padding: 20px 20px;
}
</style>
