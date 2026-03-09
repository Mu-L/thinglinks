package com.mqttsnet.thinglinks.producttopic.controller;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.datascope.DataScopeHelper;
import com.mqttsnet.thinglinks.producttopic.entity.ProductTopic;
import com.mqttsnet.thinglinks.producttopic.service.ProductTopicService;
import com.mqttsnet.thinglinks.producttopic.vo.query.ProductTopicPageQuery;
import com.mqttsnet.thinglinks.producttopic.vo.result.ProductTopicResultVO;
import com.mqttsnet.thinglinks.producttopic.vo.save.ProductTopicSaveVO;
import com.mqttsnet.thinglinks.producttopic.vo.update.ProductTopicUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 产品Topic信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/productTopic")
@Tag(name = "产品Topic信息")
public class ProductTopicController extends SuperController<ProductTopicService, Long, ProductTopic, ProductTopicSaveVO,
        ProductTopicUpdateVO, ProductTopicPageQuery, ProductTopicResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @Override
    public QueryWrap<ProductTopic> handlerWrapper(ProductTopic model, PageParams<ProductTopicPageQuery> params) {
        QueryWrap<ProductTopic> queryWrap = super.handlerWrapper(model, params);
        // 开启数据权限
        DataScopeHelper.startDataScope("product_topic");
        return queryWrap;
    }



}


