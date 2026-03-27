<script lang="ts" setup>
  import { nextTick, ref } from 'vue';
  import { PageWrapper } from '/@/components/Page';

  import { BasicTree } from '/@/components/Tree';
  import { ApiSelect } from '/@/components/Form';

  import Content from './modules/content.vue';

  import { findAppList, findDocTree } from '/@/api/open/doc/openApi';
  import { SopDocInfoResultVO } from '/@/api/devOperation/sop/model/sopDocInfoModel';

  const treeRef = ref();
  const contentRef = ref();

  const docAppIdRef = ref<string>('');
  const treeLoading = ref<boolean>(false);
  const docTreeDataRef = ref<SopDocInfoResultVO[]>([]);

  const loadDocTree = async (appId: string) => {
    try {
      treeLoading.value = true;
      const treeList = await findDocTree(appId);
      docTreeDataRef.value = treeList;
      await nextTick();
      await treeRef.value?.expandAll(true);
    } finally {
      treeLoading.value = false;
    }
  };
  const handleOptionsChange = async (options: any) => {
    if (options.length > 0) {
      docAppIdRef.value = options?.[0]?.value;
      await loadDocTree(options?.[0]?.value);
    }
  };

  const handleChange = async (value: any) => {
    await loadDocTree(value);
  };

  const handleSelect = (_, { node }: { node: any }) => {
    if (node.isFolder === 1) {
      return;
    }

    contentRef.value?.loadContent(node.id);
  };
</script>

<template>
  <PageWrapper contentClass="flex" dense>
    <div
      class="border-border bg-card mr-2 h-full rounded-[var(--radius)] border p-2 md:w-1/3 xl:w-1/3"
    >
      <ApiSelect
        style="width: 100%"
        v-model:value="docAppIdRef"
        :api="findAppList"
        value-field="id"
        label-field="appName"
        @options-change="handleOptionsChange"
        @change="handleChange"
      />
      <BasicTree
        ref="treeRef"
        :field-names="{ key: 'id', title: 'docTitle' }"
        :click-row-to-expand="false"
        :loading="treeLoading"
        :tree-data="docTreeDataRef"
        default-expand-all
        check-strictly
        highlight
        search
        toolbar
        @select="handleSelect"
      />
    </div>
    <Content class="md:w-2/3" ref="contentRef" />
  </PageWrapper>
</template>
