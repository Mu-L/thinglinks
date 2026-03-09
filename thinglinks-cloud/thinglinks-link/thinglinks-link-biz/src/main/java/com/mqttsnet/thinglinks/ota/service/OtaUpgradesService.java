package com.mqttsnet.thinglinks.ota.service;

import java.util.List;
import java.util.Optional;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.ota.dto.OtaUpgradesResultDTO;
import com.mqttsnet.thinglinks.ota.entity.OtaUpgrades;
import com.mqttsnet.thinglinks.ota.vo.query.OtaUpgradesPageQuery;
import com.mqttsnet.thinglinks.ota.vo.result.OtaUpgradesDetailsResultVO;
import com.mqttsnet.thinglinks.ota.vo.result.OtaUpgradesResultVO;
import com.mqttsnet.thinglinks.ota.vo.save.OtaUpgradesSaveVO;
import com.mqttsnet.thinglinks.ota.vo.update.OtaUpgradesUpdateVO;


/**
 * <p>
 * 业务接口
 * OTA升级包
 * </p>
 *
 * @author mqttsnet
 * @date 2024-01-12 22:36:27
 * @create [2024-01-12 22:36:27] [mqttsnet]
 */
public interface OtaUpgradesService extends SuperService<Long, OtaUpgrades> {

    /**
     * Save OTA Upgrade Package
     *
     * @param saveVO 保存参数
     * @return {@link OtaUpgradesSaveVO} 返回结果
     */
    OtaUpgradesSaveVO saveUpgradePackage(OtaUpgradesSaveVO saveVO);


    /**
     * Update OTA Upgrade Package
     *
     * @param updateVO 更新参数
     * @return {@link OtaUpgradesUpdateVO} 返回结果
     */
    OtaUpgradesUpdateVO updateUpgradePackage(OtaUpgradesUpdateVO updateVO);

    /**
     * Update OTA Upgrade Package Status
     *
     * @param id     主键
     * @param status 状态
     * @return {@link Boolean} 返回结果
     */
    Boolean updateOtaUpgradeStatus(Long id, Integer status);

    /**
     * Delete OTA Upgrade Package
     *
     * @param id 主键
     * @return {@link Boolean} 返回结果
     */
    Boolean deleteOtaUpgrade(Long id);

    /**
     * Converts OTA upgrades entities to view objects based on specified criteria.
     *
     * @param query The {@link OtaUpgradesPageQuery} object containing the search criteria.
     * @return {@link List<OtaUpgradesResultDTO>} A list of OTA upgrade records that match the given query criteria.
     */
    List<OtaUpgradesResultDTO> getOtaUpgradesResultDTOList(OtaUpgradesPageQuery query);

    /**
     * 根据ID获取升级包Optional
     *
     * @param id 升级包ID
     * @return 升级包Optional
     */
    Optional<OtaUpgradesResultDTO> getByIdOptional(Long id);

    /**
     * 获取OTA升级包详情
     *
     * @param id 升级包ID
     * @return 升级包详情
     */
    OtaUpgradesDetailsResultVO getUpgradePackageDetails(Long id);

    /**
     * 根据ID集合查询升级包信息
     *
     * @param ids 升级包ID集合
     * @return {@link List<OtaUpgradesResultVO>} 升级包信息列表
     */
    List<OtaUpgradesResultVO> selectListByIds(List<Long> ids);
}