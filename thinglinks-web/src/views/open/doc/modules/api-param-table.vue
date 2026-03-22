<script setup lang="ts">
  import { computed } from 'vue';

  import { Table, TableColumn } from 'ant-design-vue';

  interface Props {
    dataSource: any[];
  }
  const props = withDefaults(defineProps<Props>(), {
    dataSource: () => [],
  });

  function clearChildren(list: any[]) {
    return list.map((item) => {
      if (item.children?.length === 0) {
        item.children = undefined;
      }
      if (item.children?.length > 0) {
        clearChildren(item.children);
      }
      return item;
    });
  }

  const getData = computed(() => {
    return clearChildren(props.dataSource);
  });
</script>
<template>
  <Table row-key="id" size="small" :data-source="getData" bordered :pagination="false">
    <TableColumn key="name" data-index="name" title="名称" width="200px" />
    <TableColumn
      key="type"
      data-index="type"
      title="类型"
      width="100px"
      :max-width="100"
      ellipsis
    />
    <TableColumn key="required" data-index="required" title="必须" width="50px">
      <template #default="{ record }">
        <span :class="record.required ? 'danger' : ''">
          {{ record.required ? `是` : `否` }}
        </span>
      </template>
    </TableColumn>
    <TableColumn key="maxLength" data-index="maxLength" title="最大长度" width="80px" />
    <TableColumn ellipsis key="description" data-index="description" title="描述">
      <template #default="{ record }">
        <div v-html="record.description"></div>
      </template>
    </TableColumn>
    <TableColumn key="example" data-index="example" title="示例值" width="200px" />
  </Table>
</template>
<style lang="scss" scoped>
  a {
    color: #409eff;
  }

  .danger {
    color: #f56c6c;
  }
</style>
