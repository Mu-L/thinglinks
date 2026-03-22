import { open } from './basic/open';
import { user } from './basic/user';
import { sop } from './devOperation/sop';

/**
 * 按钮权限定义
 */
export const PermCode = {
  basic: {
    open,
    user,
  },
  devOperation: {
    sop,
  },
};
