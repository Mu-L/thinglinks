import { defineComponent, PropType } from 'vue';
import { Tooltip } from 'ant-design-vue';
import { CopyOutlined } from '@ant-design/icons-vue';
import { handleCopyTextV2 } from '/@/utils/thinglinks/common';

/**
 * 可复制文本组件
 * 支持文本显示、Tooltip提示和一键复制功能
 */
export default defineComponent({
  name: 'CopyableText',
  props: {
    // 要显示和复制的文本内容
    text: {
      type: String,
      default: '',
    },
    // Tooltip 的位置
    tooltipPlacement: {
      type: String as PropType<'top' | 'left' | 'right' | 'bottom' | 'topLeft' | 'topRight' | 'bottomLeft' | 'bottomRight' | 'leftTop' | 'leftBottom' | 'rightTop' | 'rightBottom'>,
      default: 'topLeft',
    },
    // 是否启用文本溢出省略
    ellipsis: {
      type: Boolean,
      default: true,
    },
    // 自定义样式
    style: {
      type: Object,
      default: () => ({}),
    },
    // 复制图标的样式
    iconStyle: {
      type: Object,
      default: () => ({}),
    },
    // 是否显示复制图标
    showCopyIcon: {
      type: Boolean,
      default: true,
    },
    // 文本为空时显示的内容
    emptyText: {
      type: String,
      default: '--',
    },
  },
  setup(props) {
    const handleCopy = () => {
      handleCopyTextV2(props.text || '');
    };

    return () => {
      const { text, tooltipPlacement, ellipsis, style, iconStyle, showCopyIcon, emptyText } = props;

      if (!text) {
        return <span>{emptyText}</span>;
      }

      const containerStyle = {
        display: 'flex',
        alignItems: 'center',
        ...style,
      };

      const iconDefaultStyle = {
        marginRight: '8px',
        color: '#1890ff',
        cursor: 'pointer',
        flexShrink: 0,
        fontSize: '14px',
        ...iconStyle,
      };

      const textStyle = ellipsis
        ? {
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            whiteSpace: 'nowrap',
          }
        : {};

      return (
        <div style={containerStyle}>
          {showCopyIcon && (
            <CopyOutlined
              onClick={handleCopy}
              style={iconDefaultStyle}
            />
          )}
          <Tooltip placement={tooltipPlacement} title={text}>
            <span style={textStyle}>{text}</span>
          </Tooltip>
        </div>
      );
    };
  },
});