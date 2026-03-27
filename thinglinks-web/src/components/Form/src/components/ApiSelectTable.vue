<template>
  <a-button type="primary" @click="onClickVisible" v-model:value="obj.id">{{ obj?.productName || obj?.deviceName || title }} <PlusOutlined></PlusOutlined></a-button>
  <TableListModal  v-bind="$attrs"  @register="registerModal" @success="handleSuccess"></TableListModal>
</template>
<script lang="ts">
import { useModal } from '/@/components/Modal';
import { defineComponent, ref, reactive, toRaw, watch } from 'vue';
import { Button } from 'ant-design-vue';
import { useAttrs } from '/@/hooks/core/useAttrs';
import { PlusOutlined } from '@ant-design/icons-vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { propTypes } from '/@/utils/propTypes';
import TableListModal from './TableList.vue';

export default defineComponent({
  name: 'ApiSelectTabel',
  components: {
    Button,
    PlusOutlined,
    TableListModal
  },
  inheritAttrs: false,
  props: {
    type: String,
    typeName: String,
    title: String,
    subTitle: String,
    value: String,
    numberToString: propTypes.bool,
    afterFetch: {
      type: Function,
      default: null,
    },
    info: {
      type: Object,
      default: {},
    },
    // api params
    // params: {
    //   type: [Object, String] as PropType<Recordable | string>,
    //   default: () => ({}),
    // },
    // support xxx.xxx.xx
    resultField: propTypes.string.def(''),
    labelField: propTypes.string.def('label'),
    valueField: propTypes.string.def('value'),
    immediate: propTypes.bool.def(true),
    alwaysLoad: propTypes.bool.def(false),
    allData: propTypes.bool.def(true),
  },
  emits: ['select', 'update:value'],
  setup(props, { emit }) {
    // console.log(ref(props.value).value,'ssssss')
    // console.log(ref(props.type).value,'ssssss')
    
    const [registerModal, { openModal }] = useModal();
    const loading = ref(false);
    const attrs = useAttrs();
    const { t } = useI18n();
    const title = ref(props.title)
    // Embedded in the form, just use the hook binding to perform form verification
    const obj = reactive({
      productName: '',
      deviceName: '',
      id: ''
    })

    watch(
        () => props.value,
        (data: string) => {
          // console.log(data)
          if(!data){
            obj.productName = ''
            obj.deviceName = ''
            obj.id = ''
          }else{
            console.log(ref(props).value,'value')
            // console.log(ref(props.type).value,'type')
            // const arr = ref(props.type).value?.split('*&*')
            obj[ref(props.typeName).value] = ref(props.type).value
          }
        },
    );
    function onClickVisible() {
      openModal(true, {
        type: props.subTitle, //device
      });
    }
    function handleSuccess(e) {
      Object.assign(obj,e)
      emit('update:value',e.id)
      emit('select',e)
    }
    return { attrs, loading, t, title, registerModal, onClickVisible, handleSuccess, obj };
  },
});
</script>
