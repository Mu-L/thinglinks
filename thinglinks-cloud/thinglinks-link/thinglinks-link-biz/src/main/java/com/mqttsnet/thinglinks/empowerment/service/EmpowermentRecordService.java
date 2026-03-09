package com.mqttsnet.thinglinks.empowerment.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.empowerment.entity.EmpowermentRecord;
import com.mqttsnet.thinglinks.empowerment.vo.result.EmpowermentRecordResultVO;
import com.mqttsnet.thinglinks.empowerment.vo.save.EmpowermentRecordSaveVO;
import com.mqttsnet.thinglinks.empowerment.vo.save.ProductEmpowermentSaveVO;
import com.mqttsnet.thinglinks.empowerment.vo.update.EmpowermentRecordUpdateVO;


/**
 * <p>
 * 业务接口
 * 赋能记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-15 17:20:27
 * @create [2023-09-15 17:20:27] [mqttsnet]
 */
public interface EmpowermentRecordService extends SuperService<Long, EmpowermentRecord> {

    /**
     * 根据赋能标识和赋能类型查询最新赋能信息
     *
     * @param empowermentIdentification 赋能标识
     * @param empowermentType           赋能类型
     * @return {@link EmpowermentRecordResultVO} 最新赋能记录
     */
    EmpowermentRecordResultVO selectLatestEmpowerment(String empowermentIdentification, Integer empowermentType);

    /**
     * 保存赋能记录
     *
     * @param saveVO 保存参数
     * @return {@link EmpowermentRecordSaveVO} 实体
     */
    EmpowermentRecordSaveVO saveEmpowermentRecord(EmpowermentRecordSaveVO saveVO);


    /**
     * 修改赋能记录
     *
     * @param updateVO 更新参数
     * @return {@link EmpowermentRecordUpdateVO} 更新结果
     */
    EmpowermentRecordUpdateVO updateEmpowermentRecord(EmpowermentRecordUpdateVO updateVO);

    /**
     * 保存产品赋能记录
     *
     * @param saveVO 产品赋能参数
     * @return {@link EmpowermentRecordSaveVO} 产品实体
     */
    EmpowermentRecordSaveVO saveProductEmpowerment(ProductEmpowermentSaveVO saveVO);
}


