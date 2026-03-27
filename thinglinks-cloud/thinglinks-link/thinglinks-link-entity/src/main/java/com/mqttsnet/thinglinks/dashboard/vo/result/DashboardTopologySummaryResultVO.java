package com.mqttsnet.thinglinks.dashboard.vo.result;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author xiaonannet
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(title = "DashboardTopologySummaryResultVO", description = "拓扑数据统计信息统计VO")
public class DashboardTopologySummaryResultVO extends Entity<Long> implements Serializable, EchoVO {
    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "上行数据总量")
    private Long totalUplinkData;

    @Schema(description = "下行数据总量")
    private Long totalDownlinkData;

    @Schema(description = "上行下行数据详细统计")
    private DashboardDetailsResultVO dashboardDetailsResultVo;

}
