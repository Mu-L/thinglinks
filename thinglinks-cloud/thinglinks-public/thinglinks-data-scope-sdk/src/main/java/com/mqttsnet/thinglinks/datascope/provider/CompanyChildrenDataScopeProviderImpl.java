package com.mqttsnet.thinglinks.datascope.provider;

import cn.hutool.core.collection.CollUtil;
import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.datascope.model.DataFieldProperty;
import com.mqttsnet.thinglinks.datascope.service.OrgHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 本单位及子级
 *
 * @author mqttsnet
 * @date 2022/1/9 23:29
 */
@Slf4j
@RequiredArgsConstructor
@Component("DATA_SCOPE_02")
public class CompanyChildrenDataScopeProviderImpl implements DataScopeProvider {
    @Autowired
    private OrgHelperService orgHelperService;

    @Override
    public List<DataFieldProperty> findDataFieldProperty(List<DataFieldProperty> fsp) {
        List<Long> employeeIdList = orgHelperService.findCompanyAndChildrenIdByEmployeeId(ContextUtil.getEmployeeId());
        if (CollUtil.isEmpty(employeeIdList)) {
//            throw BizException.wrap("请您先设置一个主部门");
            return Collections.emptyList();
        }
        fsp.forEach(item -> {
            item.setField(SuperEntity.CREATED_ORG_ID_FIELD);
            item.setValues(employeeIdList);
        });
        return fsp;
    }
}
