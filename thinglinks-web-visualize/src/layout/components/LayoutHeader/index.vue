<template>
  <n-layout-header bordered class="go-header">
    <header class="go-header-box">
      <div class="header-item left">
        <n-space>
          <slot name="left"></slot>
        </n-space>
      </div>
      <div class="header-item center">
        <slot name="center"></slot>
      </div>
      <div class="header-item right">
        <n-space :wrap="false">
          <slot name="ri-left"> </slot>
          <go-lang-select></go-lang-select>
          <theme-color-select></theme-color-select>
          <go-theme-select></go-theme-select>
          <slot name="ri-right"> </slot>
        </n-space>
      </div>
    </header>
  </n-layout-header>
</template>

<script setup lang="ts">
import { GoThemeSelect } from '@/components/GoThemeSelect'
import { GoLangSelect } from '@/components/GoLangSelect'
import { ThemeColorSelect } from '@/components/Pages/ThemeColorSelect'
</script>

<style lang="scss" scoped>
@include go(header) {
  &-box {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 1rem;
    
    .header-item {
      display: flex;
      align-items: center;
      min-width: 0; // 允许收缩，避免溢出
      
      &.left {
        justify-content: flex-start;
        flex: 1 1 auto;
      }
      
      &.center {
        justify-content: center;
        flex: 0 0 auto;
      }
      
      &.right {
        justify-content: flex-end;
        flex: 1 1 auto;
      }
    }
    
    height: $--header-height;
    padding: 0 1.25rem 0 3.75rem;
    
    // 响应式适配
    @media (max-width: 48rem) {
      padding: 0 1rem 0 1rem;
      gap: 0.5rem;
      
      .header-item {
        &.left,
        &.right {
          flex: 0 1 auto;
        }
      }
    }
    
    @media (max-width: 30rem) {
      padding: 0 0.75rem;
      gap: 0.25rem;
    }
  }
}
</style>
