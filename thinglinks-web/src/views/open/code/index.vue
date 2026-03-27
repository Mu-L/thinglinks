<script lang="ts" setup>
  import { onMounted, ref } from 'vue';

  import { Card, Table, TableColumn } from 'ant-design-vue';

  import { BasicTitle } from '/@/components/Basic';
  import Axios from 'axios';

  const tableData = ref<any[]>([]);

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

  onMounted(() => {
    Axios.get('/static/json/code.json', {
      responseType: 'text',
    }).then((resp) => {
      tableData.value = clearChildren(resp.data);
    });
  });
</script>
<template>
  <Card>
    <BasicTitle span h2>公共错误码</BasicTitle>
    <div class="isp-info mt-2">
      <Table
        row-key="id"
        size="small"
        v-if="tableData.length > 0"
        :data-source="tableData"
        bordered
        :pagination="false"
        default-expand-all-rows
      >
        <TableColumn key="code" title="code" data-index="code" width="200px" />
        <TableColumn key="msg" title="msg" data-index="msg" width="200px" />
        <TableColumn
          key="sub_code"
          title="sub_code（详细错误码）"
          data-index="sub_code"
          width="250"
        />
        <TableColumn title="sub_msg（详细错误信息）" data-index="sub_msg" key="sub_msg" />
        <TableColumn title="解决方案" data-index="solution" key="solution" />
      </Table>
    </div>
  </Card>
</template>
