import ImageDisplay from './src/ImageDisplay.vue';
import type { App } from 'vue';

export { ImageDisplay };

export default {
  install(app: App) {
    app.component(ImageDisplay.name || 'ImageDisplay', ImageDisplay);
  },
};