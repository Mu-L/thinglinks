import { withInstall } from '/@/utils';
import basicStepModal from './BasicStepModal/BasicStepModal.vue';
import basicDeviceSelector from './BasicSelec/BasicDevice/BasicDeviceSelector.vue';
import basicProductSelector from './BasicSelec/BasicProduct/BasicProductSelector.vue';
import basicAreaSelector from './BasicAreaSelection/BasicAreaSelector.vue';
import basicSelectDeviceModal from './BasicSelectDeviceModal/BasicSelectDeviceModal.vue';

export const BasicStepModal = withInstall(basicStepModal);
export const BasicDeviceSelector = withInstall(basicDeviceSelector);
export const BasicProductSelector = withInstall(basicProductSelector);
export const BasicAreaSelector = withInstall(basicAreaSelector);
export const BasicSelectDeviceModal = withInstall(basicSelectDeviceModal);

export * from './types';
