package com.mqttsnet.thinglinks.datascope.provider;

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
 * 本单位
 *
 * @author mqttsnet
 * @date 2022/1/9 23:29
 */
@Slf4j
@RequiredArgsConstructor
@Component("DATA_SCOPE_03")
public class CompanyDataScopeProviderImpl implements DataScopeProvider {

    @Autowired
    private OrgHelperService orgHelperService;

    @Override
    public List<DataFieldProperty> findDataFieldProperty(List<DataFieldProperty> fsp) {
        Long mainCompanyId = orgHelperService.getMainCompanyIdByEmployeeId(ContextUtil.getEmployeeId());
        if (mainCompanyId == null) {
//            throw BizException.wrap("请您先设置一个主部门");
            return Collections.emptyList();
        }
        List<Long> employeeIdList = Collections.singletonList(mainCompanyId);
        fsp.forEach(item -> {
            item.setField(SuperEntity.CREATED_ORG_ID_FIELD);
            item.setValues(employeeIdList);
        });
        return fsp;
    }
}
