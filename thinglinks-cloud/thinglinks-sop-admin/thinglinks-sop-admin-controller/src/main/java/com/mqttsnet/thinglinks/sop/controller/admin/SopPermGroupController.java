package com.mqttsnet.thinglinks.sop.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.sop.entity.SopPermGroup;
import com.mqttsnet.thinglinks.sop.service.SopPermGroupService;
import com.mqttsnet.thinglinks.sop.service.SopPermIsvGroupService;
import com.mqttsnet.thinglinks.sop.vo.query.SopPermGroupPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopPermGroupResultVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopPermGroupSaveVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopPermIsvGroupSaveVO;
import com.mqttsnet.thinglinks.sop.vo.update.SopPermGroupUpdateVO;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * 分组表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/sopPermGroup")
@Tag(name = "分组表")
public class SopPermGroupController extends SuperController<SopPermGroupService, Long, SopPermGroup, SopPermGroupSaveVO, SopPermGroupUpdateVO, SopPermGroupPageQuery, SopPermGroupResultVO> {
    private final EchoService echoService;
    private final SopPermIsvGroupService sopPermIsvGroupService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @GetMapping("/listByGroupId")
    public R<List<Long>> listByGroupId(@RequestParam Long isvId) {
        return R.success(sopPermIsvGroupService.listByGroupId(isvId));
    }


    /**
     * 设置分组
     *
     * @param param 表单数据
     * @return 返回影响行数
     */
    @PostMapping("updateIsvGroup")
    public R<Boolean> updateIsvGroup(@Validated @RequestBody SopPermIsvGroupSaveVO param) {
        return R.success(superService.updateIsvGroup(param));
    }
}


