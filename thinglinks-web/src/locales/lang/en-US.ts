import { genMessage } from '../helper';
import antdLocale from 'ant-design-vue/es/locale/en_US';

const modules = import.meta.glob('./en-US/**/*.ts', { eager: true });
// const modules = import.meta.globEager('./en/**/*.ts');

export default {
  message: {
    ...genMessage(modules as Recordable<Recordable>, 'en-US'),
    antdLocale,
  },
  dateLocale: null,
  dateLocaleName: 'en',
};
