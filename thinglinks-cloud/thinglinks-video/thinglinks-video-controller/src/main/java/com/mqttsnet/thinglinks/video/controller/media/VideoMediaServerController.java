package com.mqttsnet.thinglinks.video.controller.media;

import com.mqttsnet.basic.annotation.log.WebLog;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.video.entity.media.VideoMediaServer;
import com.mqttsnet.thinglinks.video.service.media.VideoMediaServerService;
import com.mqttsnet.thinglinks.video.vo.query.media.VideoMediaServerPageQuery;
import com.mqttsnet.thinglinks.video.vo.result.media.VideoMediaServerResultVO;
import com.mqttsnet.thinglinks.video.vo.save.media.VideoMediaServerSaveVO;
import com.mqttsnet.thinglinks.video.vo.update.media.VideoMediaServerUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * 流媒体服务器信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-03 17:56:38
 * @create [2024-07-03 17:56:38] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/videoMediaServer")
@Tag(name = "流媒体服务器信息")
public class VideoMediaServerController extends SuperController<VideoMediaServerService, Long, VideoMediaServer, VideoMediaServerSaveVO,
        VideoMediaServerUpdateVO, VideoMediaServerPageQuery, VideoMediaServerResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }


    /**
     * 新增 流媒体服务器信息
     *
     * @param saveVO 保存参数
     * @return {@link VideoMediaServerSaveVO} 实体
     */
    @Operation(summary = "保存流媒体服务器", description = "保存流媒体服务器信息")
    @PostMapping("/saveMediaServer")
    @WebLog(value = "保存流媒体服务器", request = false)
    public R<VideoMediaServerSaveVO> saveMediaServer(@RequestBody VideoMediaServerSaveVO saveVO) {
        return R.success(superService.saveMediaServer(saveVO));
    }

    /**
     * 修改 流媒体服务器信息
     *
     * @param updateVO 更新参数
     * @return {@link VideoMediaServerUpdateVO} 实体
     */
    @Operation(summary = "修改流媒体服务器", description = "修改流媒体服务器信息")
    @PutMapping("/updateMediaServer")
    @WebLog(value = "修改流媒体服务器", request = false)
    public R<VideoMediaServerUpdateVO> updateMediaServer(@RequestBody VideoMediaServerUpdateVO updateVO) {
        return R.success(superService.updateMediaServer(updateVO));
    }

    /**
     * 删除多媒体节点信息
     *
     * @param id 多媒体节点信息ID
     * @return 删除结果
     */
    @Operation(summary = "删除多媒体节点信息", description = "根据多媒体节点信息ID删除多媒体节点信息")
    @Parameters({@Parameter(name = "id", description = "多媒体节点信息ID", required = true),})
    @DeleteMapping("/deleteMediaServer/{id}")
    @WebLog(value = "删除多媒体节点信息", request = false)
    public R<Boolean> deleteMediaServer(@PathVariable("id") Long id) {
        log.info("deleteMediaServer id:{}", id);
        return R.success(superService.deleteMediaServer(id));
    }

    /**
     * 批量删除多媒体节点信息
     *
     * @param ids 多媒体节点信息ID列表
     * @return 删除结果
     */
    @Operation(summary = "批量删除多媒体节点信息", description = "根据多媒体节点信息ID列表批量删除多媒体节点信息")
    @DeleteMapping("/deleteMediaServers")
    @WebLog(value = "批量删除多媒体节点信息", request = false)
    public R<Boolean> deleteMediaServers(@RequestBody List<Long> ids) {
        log.info("deleteMediaServers ids:{}", ids);
        boolean allDeleted = ids.stream().distinct().allMatch(id -> superService.deleteMediaServer(id));
        return R.success(allDeleted);
    }


    /**
     * 根据媒体服务器ID获取媒体服务器详情
     *
     * @param id 媒体服务器ID
     * @return 媒体服务器详情
     */
    @Operation(summary = "根据媒体服务器ID获取媒体服务器详情", description = "根据媒体服务器ID获取媒体服务器详情")
    @GetMapping("/getMediaServerDetails/{id}")
    @Parameters({@Parameter(name = "id", description = "媒体服务器ID", required = true),})
    public R<VideoMediaServerResultVO> getMediaServerDetails(@PathVariable("id") Long id) {
        log.info("getMediaServerDetails id:{}", id);
        return R.success(superService.getMediaServerDetails(id));
    }
}


