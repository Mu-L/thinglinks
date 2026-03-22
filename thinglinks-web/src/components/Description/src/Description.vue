<script lang="tsx">
  import type { DescriptionProps, DescInstance, DescItem } from './typing';
  import type { DescriptionsProps } from 'ant-design-vue/es/descriptions/index';
  import type { CSSProperties } from 'vue';
  import type { CollapseContainerOptions } from '/@/components/Container/index';
  import { defineComponent, computed, ref, unref, toRefs } from 'vue';
  import { get } from 'lodash-es';
  import { Descriptions } from 'ant-design-vue';
  import { EyeOutlined, EyeInvisibleOutlined } from '@ant-design/icons-vue';
  import { CollapseContainer } from '/@/components/Container/index';
  import { useDesign } from '/@/hooks/web/useDesign';
  import { isFunction } from '/@/utils/is';
  import { getSlot } from '/@/utils/helper/tsxHelper';
  import { useAttrs } from '/@/hooks/core/useAttrs';
  import { useModal } from '/@/components/Modal';
  import CopyModal from '/@/components/CopyModal/index.vue';

  /**
   * 脱敏处理函数
   */
  const desensitizeValue = (
    value: string,
    type: boolean | 'phone' | 'email' | 'idCard' | 'bankCard' | string,
  ): string => {
    if (!value) return value;

    // 如果是字符串类型，作为自定义脱敏字符
    if (typeof type === 'string' && !['phone', 'email', 'idCard', 'bankCard'].includes(type)) {
      return type;
    }

    // 默认脱敏
    if (type === true) {
      return '******';
    }

    switch (type) {
      case 'phone':
        // 手机号：验证11位，保留前3后4位
        if (value.length === 11 && /^1\d{10}$/.test(value)) {
          return value.substring(0, 3) + '****' + value.substring(value.length - 4);
        }
        // 非标准手机号，仅保留前后各2位
        if (value.length >= 5) {
          return value.substring(0, 2) + '***' + value.substring(value.length - 2);
        }
        return '***';
      case 'email':
        // 邮箱：保留首字母和@后内容
        const atIndex = value.indexOf('@');
        if (atIndex > 1 && atIndex < value.length - 1) {
          const emailPrefix = value.substring(0, 1);
          const emailDomain = value.substring(atIndex);
          // 验证邮箱格式
          if (/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
            return emailPrefix + '***' + emailDomain;
          }
        }
        // 非标准邮箱，返回默认脱敏
        return '***@***.***';
      case 'idCard':
        // 身份证：验证18位，保留前3后4位
        if (value.length === 18 && /^\d{17}[\dXx]$/.test(value)) {
          return value.substring(0, 3) + '***********' + value.substring(value.length - 4);
        }
        // 15位旧身份证号
        if (value.length === 15 && /^\d{15}$/.test(value)) {
          return value.substring(0, 3) + '********' + value.substring(value.length - 4);
        }
        // 非标准身份证号，仅保留前后各2位
        if (value.length >= 6) {
          return value.substring(0, 2) + '****' + value.substring(value.length - 2);
        }
        return '****';
      case 'bankCard':
        // 银行卡：验证16-19位，保留前4后4位
        if (/^\d{16,19}$/.test(value)) {
          const maskedLength = value.length - 8;
          const masked = '*'.repeat(Math.max(maskedLength, 4));
          return value.substring(0, 4) + masked + value.substring(value.length - 4);
        }
        // 非标准银行卡号，仅保留前后各2位
        if (value.length >= 5) {
          return value.substring(0, 2) + '***' + value.substring(value.length - 2);
        }
        return '****';
      default:
        return '******';
    }
  };

  const props = {
    useCollapse: { type: Boolean, default: true },
    title: { type: String, default: '' },
    size: {
      type: String,
      validator: (v) => ['small', 'default', 'middle', undefined].includes(v),
      default: 'small',
    },
    bordered: { type: Boolean, default: true },
    column: {
      type: [Number, Object] as PropType<number | Recordable>,
      default: () => {
        return { xxl: 4, xl: 3, lg: 3, md: 3, sm: 2, xs: 1 };
      },
    },
    collapseOptions: {
      type: Object as PropType<CollapseContainerOptions>,
      default: null,
    },
    schema: {
      type: Array as PropType<DescItem[]>,
      default: () => [],
    },
    data: { type: Object },
  };

  export default defineComponent({
    name: 'Description',
    components: {
      CopyModal,
    },
    props,
    emits: ['register'],
    setup(props, { slots, emit }) {
      const propsRef = ref<Partial<DescriptionProps> | null>(null);

      const { prefixCls } = useDesign('description');
      const attrs = useAttrs();

      // CopyModal for view mode
      const [registerCopyModal, { openModal: openCopyModal }] = useModal();

      // Custom title component: get title
      const getMergeProps = computed(() => {
        return {
          ...props,
          ...(unref(propsRef) as Recordable),
        } as DescriptionProps;
      });

      const getProps = computed(() => {
        const opt = {
          ...unref(getMergeProps),
          title: undefined,
        };
        return opt as DescriptionProps;
      });

      /**
       * @description: Whether to setting title
       */
      const useWrapper = computed(() => !!unref(getMergeProps).title);

      /**
       * @description: Get configuration Collapse
       */
      const getCollapseOptions = computed((): CollapseContainerOptions => {
        return {
          // Cannot be expanded by default
          canExpand: false,
          ...unref(getProps).collapseOptions,
        };
      });

      const getDescriptionsProps = computed(() => {
        return { ...unref(attrs), ...unref(getProps) } as DescriptionsProps;
      });

      /**
       * @description:设置desc
       */
      function setDescProps(descProps: Partial<DescriptionProps>): void {
        // Keep the last setDrawerProps
        propsRef.value = {
          ...(unref(propsRef) as Recordable),
          ...descProps,
        } as Recordable;
      }

      // Prevent line breaks
      function renderLabel({ label, labelMinWidth, labelStyle }: DescItem) {
        if (!labelStyle && !labelMinWidth) {
          return label;
        }

        const labelStyles: CSSProperties = {
          ...labelStyle,
          minWidth: `${labelMinWidth}px `,
        };
        return <div style={labelStyles}>{label}</div>;
      }

      function renderItem() {
        const { schema, data } = unref(getProps);
        return unref(schema)
          .map((item) => {
            const { render, field, span, show, contentMinWidth, slot } = item;

            if (show && isFunction(show) && !show(data)) {
              return null;
            }

            const getContent = () => {
              // 如果定义了 slot，优先使用插槽
              if (slot) {
                const slotContent = getSlot(slots, slot, data);
                if (slotContent) {
                  return slotContent;
                }
              }

              const _data = unref(getProps)?.data;
              if (!_data) {
                return null;
              }

              // 改进：更好地处理多层级字段 xxx.xxx 的情况
              const getField = get(_data, field);

              // 检查多层级字段是否存在的改进逻辑
              const hasField = () => {
                if (!field) return false;

                // 如果是简单字段（不包含点），使用原来的逻辑
                if (!field.includes('.')) {
                  return toRefs(_data).hasOwnProperty(field);
                }

                // 对于多层级字段，逐层检查路径是否存在
                const fieldParts = field.split('.');
                let current = _data;

                for (const part of fieldParts) {
                  if (current === null || current === undefined || typeof current !== 'object') {
                    return false;
                  }
                  if (!Object.prototype.hasOwnProperty.call(current, part)) {
                    return false;
                  }
                  current = current[part];
                }
                return true;
              };

              // 如果字段不存在但有值（可能是计算属性或其他情况），使用空值渲染
              if (getField !== undefined && !hasField()) {
                return isFunction(render) ? render('', _data) : '';
              }

              const content = isFunction(render) ? render(getField, _data) : getField ?? '';

              // 如果启用了脱敏模式，显示脱敏内容和切换图标
              if (item.desensitize) {
                const contentStr =
                  typeof content === 'string' || typeof content === 'number'
                    ? String(content)
                    : JSON.stringify(content, null, 2);
                const maskedValue = desensitizeValue(contentStr, item.desensitize);
                return (
                  <span style={{ display: 'inline-flex', alignItems: 'center', gap: '8px' }}>
                    <span>{maskedValue}</span>
                    <EyeOutlined
                      style={{ cursor: 'pointer', fontSize: '14px', color: '#1890ff' }}
                      onClick={() => {
                        openCopyModal(true, {
                          title: String(item.label),
                          value: contentStr,
                        });
                      }}
                    />
                  </span>
                );
              }

              // 如果启用了查看模式，显示眼睛图标
              if (item.enableView) {
                const contentStr =
                  typeof content === 'string' || typeof content === 'number'
                    ? String(content)
                    : JSON.stringify(content, null, 2);
                return (
                  <EyeOutlined
                    style={{ cursor: 'pointer', fontSize: '16px' }}
                    onClick={() => {
                      openCopyModal(true, {
                        title: String(item.label),
                        value: contentStr,
                      });
                    }}
                  />
                );
              }

              return content;
            };

            const width = contentMinWidth;
            return (
              <Descriptions.Item label={renderLabel(item)} key={field} span={span}>
                {() => {
                  if (!contentMinWidth) {
                    return getContent();
                  }
                  const style: CSSProperties = {
                    minWidth: `${width}px`,
                  };
                  return <div style={style}>{getContent()}</div>;
                }}
              </Descriptions.Item>
            );
          })
          .filter((item) => !!item);
      }

      const renderDesc = () => {
        return (
          <Descriptions class={`${prefixCls}`} {...(unref(getDescriptionsProps) as any)}>
            {renderItem()}
          </Descriptions>
        );
      };

      const renderContainer = () => {
        const content = props.useCollapse ? renderDesc() : <div>{renderDesc()}</div>;
        // Reduce the dom level
        if (!props.useCollapse) {
          return content;
        }

        const { canExpand, helpMessage } = unref(getCollapseOptions);
        const { title } = unref(getMergeProps);

        return (
          <CollapseContainer title={title} canExpan={canExpand} helpMessage={helpMessage}>
            {{
              default: () => content,
              action: () => getSlot(slots, 'action'),
            }}
          </CollapseContainer>
        );
      };

      const methods: DescInstance = {
        setDescProps,
      };

      emit('register', methods);
      return () => (
        <>
          {unref(useWrapper) ? renderContainer() : renderDesc()}
          <CopyModal onRegister={registerCopyModal} />
        </>
      );
    },
  });
</script>
