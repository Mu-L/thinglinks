import { withInstall } from '/@/utils/index';
import vPlayer from './src/VideoPlayer.vue';
import RealTimeInfo from './components/RealTimeInfo.vue';

const VPlayer = withInstall(vPlayer);
export default VPlayer;
export { RealTimeInfo };
