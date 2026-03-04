import { useMessage as Message } from 'naive-ui'

/**
 * @description: message
 */
export function useMessage() {
  return {
    createMessage: Message,
  };
}
