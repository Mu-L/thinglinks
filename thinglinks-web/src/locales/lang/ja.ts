import { genMessage } from '../helper';
import antdLocale from 'ant-design-vue/es/locale/ja_JP';

const modules = import.meta.glob('./ja/**/*.ts', { eager: true });
// const modules = import.meta.globEager('./ja/**/*.ts');
export default {
  message: {
    ...genMessage(modules as Recordable<Recordable>, 'ja'),
    antdLocale,
  },
};
