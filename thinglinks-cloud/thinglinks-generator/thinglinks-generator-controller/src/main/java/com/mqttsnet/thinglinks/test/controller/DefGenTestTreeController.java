package com.mqttsnet.thinglinks.test.controller;

import com.mqttsnet.basic.annotation.log.WebLog;
import com.mqttsnet.basic.annotation.user.LoginUser;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.model.entity.system.SysUser;
import com.mqttsnet.thinglinks.test.entity.DefGenTestTree;
import com.mqttsnet.thinglinks.test.service.DefGenTestTreeService;
import com.mqttsnet.thinglinks.test.vo.query.DefGenTestTreePageQuery;
import com.mqttsnet.thinglinks.test.vo.result.DefGenTestTreeResultVO;
import com.mqttsnet.thinglinks.test.vo.save.DefGenTestTreeSaveVO;
import com.mqttsnet.thinglinks.test.vo.update.DefGenTestTreeUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * 测试树结构
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-20 00:28:30
 * @create [2022-04-20 00:28:30] [mqttsnet] 
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/defGenTestTree")
@Tag(name = "测试树结构")
public class DefGenTestTreeController extends SuperController<DefGenTestTreeService, Long, DefGenTestTree, DefGenTestTreeSaveVO,
        DefGenTestTreeUpdateVO, DefGenTestTreePageQuery, DefGenTestTreeResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    /**
     * 按树结构查询
     *
     * @param pageQuery 查询参数
     * @return 查询结果
     */
    @Operation(summary = "按树结构查询", description = "按树结构查询")
    @PostMapping("/tree")
    @WebLog("级联查询")
    public R<List<DefGenTestTree>> tree(@RequestBody DefGenTestTreePageQuery pageQuery) {
        return success(superService.findTree(pageQuery));
    }

    @PostMapping("/anyone/test")
    public R<Object> test(@Parameter(hidden = true) @LoginUser(isFull = true) SysUser user) {
        return success(user);
    }
}


