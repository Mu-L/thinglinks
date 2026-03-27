package com.mqttsnet.thinglinks.generator.utils;

import com.mqttsnet.basic.database.properties.DatabaseProperties;
import com.mqttsnet.thinglinks.generator.enumeration.ProjectTypeEnum;
import com.mqttsnet.thinglinks.generator.vo.save.ProjectGeneratorVO;

/**
 * @author mqttsnet
 * @version v1.0
 * @date 2022/4/5 5:54 PM
 * @create [2022/4/5 5:54 PM ] [mqttsnet] [初始创建]
 */
public class ProjectUtilsTest {
    public static void main(String[] args) {
        ProjectGeneratorVO vo = new ProjectGeneratorVO();
        vo.setProjectPrefix("thinglinks");
        vo.setOutputDir("/Users/mqttsnet/gitlab/thinglinks-cloud-pro-datasource-column");
        vo.setType(ProjectTypeEnum.CLOUD);
        vo.setAuthor("MqttsNet");
        vo.setServiceName("test");
        vo.setModuleName("test");
        vo.setParent("com.mqttsnet.thinglinks");
        vo.setGroupId("com.mqttsnet.thinglinks");
        vo.setUtilParent("com.mqttsnet.basic");
        vo.setVersion("1.0.3");
        vo.setDescription("测试服务");
        vo.setServerPort(8080);
        ProjectUtils.generator(vo, new DatabaseProperties());
    }
}
