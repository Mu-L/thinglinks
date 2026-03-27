package com.mqttsnet.thinglinks.sop.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.model.vo.save.IdVO;
import com.mqttsnet.thinglinks.sop.entity.SopNotifyInfo;
import com.mqttsnet.thinglinks.sop.service.SopNotifyInfoService;
import com.mqttsnet.thinglinks.sop.vo.query.SopNotifyInfoPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopNotifyInfoResultVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopNotifyInfoSaveVO;
import com.mqttsnet.thinglinks.sop.vo.update.SopNotifyInfoUpdateVO;

/**
 * <p>
 * 前端控制器
 * 回调信息-提供给前端管理页面使用
 * </p>
 *
 * @author zuihou
 * @date 2025-12-17 15:38:07
 * @create [2025-12-17 15:38:07] [zuihou] [代码生成器生成]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/sopNotifyInfo")
@Tag(name = "回调信息")
public class SopNotifyInfoController extends SuperController<SopNotifyInfoService, Long, SopNotifyInfo, SopNotifyInfoSaveVO, SopNotifyInfoUpdateVO, SopNotifyInfoPageQuery, SopNotifyInfoResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }


    /**
     * 重新推送
     *
     * @param id ID
     * @param url 重试地址
     * @return 返回影响行数
     */
    @PostMapping("/push")
    public R<Long> push(@RequestParam Long id, @RequestParam(required = false) String url) {
        return R.success(superService.push(id, url));
    }

    /**
     * 结束重试
     *
     * @param param 表单数据
     * @return 返回影响行数
     */
    @PostMapping("/end")
    public R<Boolean> end(@Validated @RequestBody IdVO param) {
        return R.success(superService.end(param.getId()));
    }
}


