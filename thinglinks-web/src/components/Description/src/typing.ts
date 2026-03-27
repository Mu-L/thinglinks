import type { VNode, CSSProperties } from 'vue';
import type { CollapseContainerOptions } from '/@/components/Container/index';
import type { DescriptionsProps } from 'ant-design-vue/es/descriptions/index';

export interface DescItem {
  labelMinWidth?: number;
  contentMinWidth?: number;
  labelStyle?: CSSProperties;
  field: string;
  label: string | VNode | JSX.Element;
  // Merge column
  span?: number;
  show?: (...arg: any) => boolean;
  // slot name for custom content
  slot?: string;
  // render
  render?: (
    val: any,
    data: Recordable,
  ) => VNode | undefined | JSX.Element | Element | string | number;
  // enable view mode: show eye icon, click to view content in modal
  enableView?: boolean;
  // desensitize mode: mask sensitive data, click eye icon to view plain text
  // - true: default mask (show ****)
  // - 'phone': phone number mask (138****1234)
  // - 'email': email mask (a***@example.com)
  // - 'idCard': ID card mask (110***********1234)
  // - 'bankCard': bank card mask (6222****1234)
  // - string: custom mask string
  desensitize?: boolean | 'phone' | 'email' | 'idCard' | 'bankCard' | string;
}

export interface DescriptionProps extends DescriptionsProps {
  // Whether to include the collapse component
  useCollapse?: boolean;
  /**
   * item configuration
   * @type DescItem
   */
  schema: DescItem[];
  /**
   * 数据
   * @type object
   */
  data: Recordable;
  /**
   * Built-in CollapseContainer component configuration
   * @type CollapseContainerOptions
   */
  collapseOptions?: CollapseContainerOptions;
}

export interface DescInstance {
  setDescProps(descProps: Partial<DescriptionProps>): void;
}

export type Register = (descInstance: DescInstance) => void;

/**
 * @description:
 */
export type UseDescReturnType = [Register, DescInstance];
