package com.mqttsnet.thinglinks.mobile.mobilespace.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.device.vo.result.DeviceDetailsResultVO;
import com.mqttsnet.thinglinks.link.facade.DeviceFacade;
import com.mqttsnet.thinglinks.mobile.mobilespace.entity.MobileSpace;
import com.mqttsnet.thinglinks.mobile.mobilespace.manager.MobileSpaceManager;
import com.mqttsnet.thinglinks.mobile.mobilespace.service.MobileSpaceService;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.query.MobileSpacePageQuery;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.result.MobileSpaceDetailsResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.result.MobileSpaceResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.save.MobileSpaceSaveVO;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.update.MobileSpaceUpdateVO;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.service.MobileSpaceDeviceService;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.query.MobileSpaceDevicePageQuery;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.result.MobileSpaceDeviceDetailsResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.result.MobileSpaceDeviceResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.result.MobileSpaceProductDetailsResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.save.MobileSpaceDeviceSaveVO;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.enumeration.SpaceMemberTypeEnum;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.service.MobileSpaceMemberService;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.result.MobileSpaceDefUserResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.result.MobileSpaceMemberResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.save.MobileSpaceMemberSaveVO;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.system.facade.DefUserFacade;
import com.mqttsnet.thinglinks.system.vo.result.tenant.DefUserDetailsResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class MobileSpaceServiceImpl extends SuperServiceImpl<MobileSpaceManager, Long, MobileSpace> implements MobileSpaceService {

    private final MobileSpaceDeviceService mobileSpaceDeviceService;

    private final MobileSpaceMemberService mobileSpaceMemberService;

    private final DefUserFacade defUserApi;

    private final DeviceFacade deviceApi;


    /**
     * 保存空间
     *
     * @param saveVO
     * @return
     */
    @Override
    public MobileSpaceSaveVO saveMobileSpace(MobileSpaceSaveVO saveVO) {
        // 校验参数
        checkedMobileSpaceSaveVO(saveVO);
        //构建参数
        MobileSpace mobileSpace = builderMobileSpaceSaveVO(saveVO);
        //保存空间
        boolean save = superManager.save(mobileSpace);
        if (save) {
            // 保存空间人员
            saveMobileSpaceMember(mobileSpace);
        }
        return saveVO;

    }

    // 用于保存 MobileSpaceMember
    private void saveMobileSpaceMember(MobileSpace mobileSpace) {
        MobileSpaceMemberSaveVO mobileSpaceMemberSaveVO = new MobileSpaceMemberSaveVO();
        mobileSpaceMemberSaveVO.setSpaceId(mobileSpace.getId());
        mobileSpaceMemberSaveVO.setMemberId(mobileSpace.getCreatedBy());
        mobileSpaceMemberSaveVO.setMemberType(SpaceMemberTypeEnum.OWNER.getValue());
        mobileSpaceMemberSaveVO.setCreatedOrgId(mobileSpace.getCreatedOrgId());
        // 空间人员表添加数据
        mobileSpaceMemberService.saveMobileSpaceMember(mobileSpaceMemberSaveVO);
    }

    private void checkedMobileSpaceSaveVO(MobileSpaceSaveVO saveVO) {
        ArgumentAssert.notBlank(saveVO.getSpaceName(), "spaceName Cannot be null");
        // 根据用户ID和空间ID查询空间名称是否存在
        MobileSpacePageQuery query = new MobileSpacePageQuery();
        query.setSpaceName(saveVO.getSpaceName());
        query.setCreatedUserId(ContextUtil.getUserId());
        if (!superManager.getMobileSpaceList(query).isEmpty()) {
            log.warn("Save failed: The space name '{}' already exists", saveVO.getSpaceName());
            throw BizException.wrap("Save failed: The space name already exists");
        }
    }

    private MobileSpace builderMobileSpaceSaveVO(MobileSpaceSaveVO saveVO) {
        MobileSpace mobileSpace = BeanPlusUtil.toBeanIgnoreError(saveVO, MobileSpace.class);
        mobileSpace.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return mobileSpace;
    }


    // 更新
    @Override
    public MobileSpaceUpdateVO updateMobileSpace(MobileSpaceUpdateVO updateVO) {
        // 校验参数
        checkedMobileSpaceUpdateVO(updateVO);
        // 构建参数并更新
        MobileSpace mobileSpace = BeanPlusUtil.toBeanIgnoreError(updateVO, MobileSpace.class);

        superManager.updateById(mobileSpace);
        return updateVO;
    }

    private void checkedMobileSpaceUpdateVO(MobileSpaceUpdateVO updateVO) {
        ArgumentAssert.notBlank(updateVO.getSpaceName(), "spaceName cannot be null");
        ArgumentAssert.notNull(updateVO.getId(), "id Cannot be null");
        MobileSpacePageQuery query = new MobileSpacePageQuery();
        query.setSpaceName(updateVO.getSpaceName());
        // 查询名称是否存在并且不等于当前id
        if (superManager.doesSpaceNameExist(query)) {
            log.warn("Update failed: The space name '{}' already exists", updateVO.getSpaceName());
            throw BizException.wrap("Update failed: The space name already exists");
        }
    }

    /**
     * 删除空间
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    public Boolean deleteMobileSpace(Long id, Long userId) {
        log.info("deleteMobileSpace id:{}", id);
        // 校验参数
        ArgumentAssert.notNull(id, "id Cannot be null");
        // 查询空间信息
        MobileSpace mobileSpace = superManager.getById(id);
        if (null == mobileSpace) {
            throw BizException.wrap("The space does not exist");
        }
        MobileSpaceMemberResultVO mobileSpaceMemberResultVO = mobileSpaceMemberService.findOneByUserIdAndSpaceId(userId, id);
        // 根据用户ID和空间ID判断该空间下该用户是否为管理员、成员
        if (Objects.nonNull(mobileSpaceMemberResultVO) && List.of(SpaceMemberTypeEnum.MEMBER.getValue(), SpaceMemberTypeEnum.ADMIN.getValue()).contains(mobileSpaceMemberResultVO.getMemberType())) {
            // 如果是管理员、成员，则只删除空间绑定关系，不删除空间
            return mobileSpaceMemberService.removeBySpaceIdAndMemberId(id, userId);
        }

        // 判断该空间下是否绑定设备 如有绑定，则不允许删除
        if (mobileSpaceDeviceService.hasDevicesBySpaceId(id)) {
            throw BizException.wrap("此空间已绑定设备，请删除所有设备后重试！");
        }

        if (!mobileSpaceMemberService.removeBySpaceId(id)) {
            throw BizException.wrap("删除空间成员失败！");
        }
        return superManager.removeById(id);
    }


    // 查询根据人员ID 查询空间列表
    @Override
    public List<MobileSpaceResultVO> getMobileSpaceResultVOList(MobileSpacePageQuery query) {
        // 获取空间列表并转换为 MobileSpaceResultVO 列表
        return Optional.ofNullable(mobileSpaceMemberService.selectMobileSpaceByMemberId(query.getCreatedUserId()))
                .filter(CollectionUtils::isNotEmpty)
                .map(superManager::getSpaceInfoByIdList)
                .filter(CollectionUtils::isNotEmpty)
                .map(list -> BeanPlusUtil.toBeanList(list, MobileSpaceResultVO.class))
                .orElse(Collections.emptyList());
    }


    /**
     * 查询空间详情
     *
     * @param spaceId 空间ID
     * @return {@link MobileSpaceDetailsResultVO} 空间详情VO
     */
    @Override
    public MobileSpaceDetailsResultVO getMobileSpaceById(Long spaceId) {
        // 校验参数
        ArgumentAssert.notNull(spaceId, "spaceId Cannot be null");

        MobileSpaceDetailsResultVO mobileSpaceDetailsResultVO = new MobileSpaceDetailsResultVO();

        // 查询空间信息
        MobileSpace mobileSpace = Optional.ofNullable(superManager.getById(spaceId))
                .orElseThrow(() -> BizException.wrap("The space does not exist"));
        BeanPlusUtil.copyProperties(mobileSpace, mobileSpaceDetailsResultVO);

        // 查询该空间下成员信息
        List<MobileSpaceMemberResultVO> mobileSpaceMemberResultVOList = mobileSpaceMemberService
                .selectMobileSpaceMemberBySpaceId(spaceId);

        // 提取成员信息中的 userid，获取用户信息
        Map<Long, MobileSpaceMemberResultVO> spaceMemberResultVOMap = mobileSpaceMemberResultVOList.stream()
                .collect(Collectors.toMap(MobileSpaceMemberResultVO::getMemberId, Function.identity()));

        // 调用 api 获取用户信息
        R<List<DefUserDetailsResultVO>> defUserResult = defUserApi.getDefUserByIds(new ArrayList<>(spaceMemberResultVOMap.keySet()));

        Optional.ofNullable(defUserResult)
                .filter(R::getIsSuccess)
                .map(R::getData)
                .ifPresent(userList -> userList.forEach(user ->
                        Optional.ofNullable(spaceMemberResultVOMap.get(user.getId()))
                                .ifPresent(memberResultVO ->
                                        memberResultVO.setDefUserResultVO(BeanPlusUtil.toBeanIgnoreError(user, MobileSpaceDefUserResultVO.class))
                                )
                ));

        mobileSpaceDetailsResultVO.setSpaceMemberResultVOList(mobileSpaceMemberResultVOList);

        // 查询设备信息
        MobileSpaceDevicePageQuery query = MobileSpaceDevicePageQuery.builder()
                .spaceId(spaceId)
                .build();

        List<MobileSpaceDeviceResultVO> spaceDeviceResultVOS = mobileSpaceDeviceService.getMobileSpaceDeviceList(query);

        List<String> deviceIdentifications = spaceDeviceResultVOS.stream()
                .map(MobileSpaceDeviceResultVO::getDeviceIdentification)
                .distinct()
                .toList();

        // 调用 api 获取设备详情
        R<List<DeviceDetailsResultVO>> deviceDetailsResultVOR = deviceApi.getDeviceDetailsByIdentifications(deviceIdentifications);

        Optional.ofNullable(deviceDetailsResultVOR)
                .filter(R::getIsSuccess)
                .map(R::getData)
                .ifPresentOrElse(deviceDetailsResultVOList -> {
                    // 按照设备详情中的产品标识聚合设备信息，使用 parallelStream 提升性能
                    Map<String, List<DeviceDetailsResultVO>> productToDeviceMap = deviceDetailsResultVOList.parallelStream()
                            .collect(Collectors.groupingBy(DeviceDetailsResultVO::getProductIdentification));

                    // 将设备详情转换并封装到 MobileSpaceProductDetailsResultVO
                    List<MobileSpaceProductDetailsResultVO> productDetailsResultVOS = productToDeviceMap.entrySet().parallelStream()
                            .map(entry -> {
                                List<DeviceDetailsResultVO> deviceDetailsResultVOS = entry.getValue();
                                ProductResultVO productResultVO = deviceDetailsResultVOS.get(0).getProductResultVO();

                                // 创建并填充 MobileSpaceProductDetailsResultVO 对象
                                MobileSpaceProductDetailsResultVO productDetailsResultVO = BeanPlusUtil.toBean(productResultVO, MobileSpaceProductDetailsResultVO.class);

                                // 设置设备详情
                                productDetailsResultVO.setSpaceDeviceDetailsResultVOList(
                                        BeanPlusUtil.toBeanList(deviceDetailsResultVOS, MobileSpaceDeviceDetailsResultVO.class)
                                );
                                return productDetailsResultVO;
                            })
                            .collect(Collectors.toList());

                    // 设置产品详情集合
                    mobileSpaceDetailsResultVO.setSpaceProductDetailsResultVOList(productDetailsResultVOS);
                }, () -> {
                    log.warn("getMobileSpaceById 获取设备详情失败或结果为空 deviceIdentifications:{}", deviceIdentifications);
                });


        return mobileSpaceDetailsResultVO;
    }


    @Override
    public MobileSpaceMemberSaveVO saveMobileSpaceMember(MobileSpaceMemberSaveVO saveVO) {
        // 校验空间是否存在
        List<MobileSpace> mobileSpaces = superManager.getSpaceInfoByIdList(Collections.singletonList(saveVO.getSpaceId()));
        ArgumentAssert.notNull(mobileSpaces, "space does not exist");

        MobileSpaceMemberResultVO mobileSpaceMemberResultVO = mobileSpaceMemberService.findOneByUserIdAndSpaceId(saveVO.getMemberId(), saveVO.getSpaceId());
        ArgumentAssert.isNull(mobileSpaceMemberResultVO, "This space is bound");

        Boolean saveFlag = mobileSpaceMemberService.saveMobileSpaceMember(saveVO);
        if (!saveFlag) {
            throw BizException.wrap("Failed to add space staff");
        }
        return BeanPlusUtil.toBean(saveVO, MobileSpaceMemberSaveVO.class);
    }

    @Override
    public MobileSpaceDeviceSaveVO updateMobileSpaceDevice(MobileSpaceDeviceSaveVO saveVO) {
        MobileSpaceDeviceSaveVO mobileSpaceDeviceSaveVO = mobileSpaceDeviceService.saveMobileSpaceDevice(saveVO);
        if (Objects.isNull(mobileSpaceDeviceSaveVO)) {
            throw BizException.wrap("绑定设备失败");
        }
        return mobileSpaceDeviceSaveVO;
    }


}
