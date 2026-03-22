import { withInstall } from '/@/utils/index';
import tinymce from './src/Editor.vue';
import tinymceCustom from './src/EditorCustom.vue';

export const Tinymce = withInstall(tinymce);
export const TinymceCustom = withInstall(tinymceCustom);
