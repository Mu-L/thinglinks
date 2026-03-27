package com.mqttsnet.thinglinks.productcommand.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.productcommand.entity.ProductCommand;
import com.mqttsnet.thinglinks.productcommand.manager.ProductCommandManager;
import com.mqttsnet.thinglinks.productcommand.service.ProductCommandService;
import com.mqttsnet.thinglinks.productcommand.vo.save.ProductCommandSaveVO;
import com.mqttsnet.thinglinks.productcommand.vo.update.ProductCommandUpdateVO;
import com.mqttsnet.thinglinks.productservice.manager.ProductServiceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 产品模型设备服务命令表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductCommandServiceImpl extends SuperServiceImpl<ProductCommandManager, Long, ProductCommand> implements ProductCommandService {

    private final ProductServiceManager productServiceManager;

    /**
     * 保存产品模型设备服务命令
     *
     * @param saveVO
     * @return
     */
    @Override
    public ProductCommand saveProductCommand(ProductCommandSaveVO saveVO) {
        log.info("saveProductCommand saveVO:{}", saveVO);
        //校验参数
        checkedProductCommandSaveVO(saveVO);
        //构建参数
        ProductCommand productCommand = builderProductCommandSaveVO(saveVO);
        //更新
        superManager.save(productCommand);
        return productCommand;
    }

    /**
     * 修改产品模型设备服务命令
     *
     * @param updateVO
     * @return
     */
    @Override
    public ProductCommand updateProductCommand(ProductCommandUpdateVO updateVO) {
        log.info("updateProductCommand updateVO:{}", updateVO);
        //校验参数
        checkedProductCommandUpdateVO(updateVO);
        //构建参数
        ProductCommand productCommand = BeanPlusUtil.toBeanIgnoreError(updateVO, ProductCommand.class);
        //更新
        superManager.updateById(productCommand);
        return productCommand;
    }

    @Override
    public Boolean deleteProductCommand(Long id) {
        ArgumentAssert.notNull(id, "id Cannot be null");
        ProductCommand productCommand = superManager.getById(id);
        if (null == productCommand) {
            throw BizException.wrap("The productCommand does not exist");
        }
        return superManager.removeById(id);
    }

    @Override
    public List<ProductCommand> findAllByServiceIds(List<Long> serviceIds) {
        return superManager.findAllByServiceIds(serviceIds);
    }

    /**
     * 新增 校验参数
     *
     * @param saveVO
     */
    private void checkedProductCommandSaveVO(ProductCommandSaveVO saveVO) {
        ArgumentAssert.notNull(saveVO.getServiceId(), "serviceId Cannot be null");
        //校验产品模型服务是否存在
        ArgumentAssert.notNull(productServiceManager.findOneByProductServiceId(saveVO.getServiceId()), "productService not found");
        ArgumentAssert.notBlank(saveVO.getCommandCode(), "commandCode Cannot be null");
        //校验CODE
        if (CollUtil.isNotEmpty(superManager.checkCode(saveVO.getServiceId(), saveVO.getCommandCode()))) {
            throw BizException.wrap("commandCode already exists");
        }
        ArgumentAssert.notBlank(saveVO.getCommandName(), "commandName Cannot be null");
    }

    /**
     * 新增 构建参数
     *
     * @param saveVO
     * @return
     */
    private ProductCommand builderProductCommandSaveVO(ProductCommandSaveVO saveVO) {
        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return BeanPlusUtil.copyProperties(saveVO, ProductCommand.class);
    }

    /**
     * 修改 校验参数
     *
     * @param updateVO
     */
    private void checkedProductCommandUpdateVO(ProductCommandUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "id Cannot be null");
        ArgumentAssert.notNull(updateVO.getServiceId(), "serviceId Cannot be null");
        //校验产品模型是否存在
        ArgumentAssert.notNull(productServiceManager.findOneByProductServiceId(updateVO.getServiceId()), "productService not found");
        ArgumentAssert.notBlank(updateVO.getCommandCode(), "commandCode Cannot be null");
        ArgumentAssert.notBlank(updateVO.getCommandName(), "commandName Cannot be null");

        //校验CODE
        List<ProductCommand> productCommands = superManager.checkCode(updateVO.getServiceId(), updateVO.getCommandCode());
        productCommands.stream()
                .filter(productCommand -> !productCommand.getId().equals(updateVO.getId()))
                .findAny()
                .ifPresent(productProperty -> {
                    throw BizException.wrap("commandCode already exists");
                });
    }

}


