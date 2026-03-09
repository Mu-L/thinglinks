package com.mqttsnet.thinglinks.base.controller.user;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * HibernateValidate 校验工具类
 */
public class ValidatorUtils {

    private static Validator VALIDATOR_FAST = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
    private static Validator VALIDATOR_ALL = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

    /**
     * 校验遇到第一个不合法的字段直接返回不合法字段，后续字段不再校验
     * @Time 2020年6月22日 上午11:36:13
     * @param <T>
     * @param domain
     * @return
     * @throws Exception
     */
    public static <T> Set<ConstraintViolation<T>> validateFast(T domain) {
        Set<ConstraintViolation<T>> validateResult = VALIDATOR_FAST.validate(domain);
        if (!validateResult.isEmpty()) {
            System.out.println(validateResult.iterator().next().getPropertyPath() + "：" + validateResult.iterator().next().getMessage());
        }
        return validateResult;
    }

    /**
     * 校验所有字段并返回不合法字段
     * @Time 2020年6月22日 上午11:36:55
     * @param <T>
     * @param domain
     * @return
     * @throws Exception
     */
    public static <T> Set<ConstraintViolation<T>> validateAll(T domain) {
        Set<ConstraintViolation<T>> validateResult = VALIDATOR_ALL.validate(domain);
        if (!validateResult.isEmpty()) {
            Iterator<ConstraintViolation<T>> it = validateResult.iterator();
            while (it.hasNext()) {
                ConstraintViolation<T> cv = it.next();

                Field field = ReflectUtil.getField(cv.getRootBeanClass(), cv.getPropertyPath().toString());
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String name = "";
                if (excelProperty != null) {
                    Schema apiModelProperty = field.getAnnotation(Schema.class);
                    name = apiModelProperty != null ? apiModelProperty.description() : "";
                } else {
                    name = StrUtil.join(".", excelProperty.value());
                }

                System.out.println(name + ": " + cv.getPropertyPath() + "：" + cv.getMessage());

            }
        }
        return validateResult;
    }


    public static <T> String validateAll(List<T> domains, int headRow) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < domains.size(); i++) {
            T domain = domains.get(i);
            Set<ConstraintViolation<T>> validateResult = VALIDATOR_ALL.validate(domain);
            if (!validateResult.isEmpty()) {
                Iterator<ConstraintViolation<T>> it = validateResult.iterator();
                while (it.hasNext()) {
                    ConstraintViolation<T> cv = it.next();

                    Field field = ReflectUtil.getField(cv.getRootBeanClass(), cv.getPropertyPath().toString());
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    String name = "";
                    if (excelProperty != null) {
                        Schema apiModelProperty = field.getAnnotation(Schema.class);
                        name = apiModelProperty != null ? apiModelProperty.description() : "";
                    } else {
                        name = StrUtil.join(".", excelProperty.value());
                    }
                    sb.append(StrUtil.format("第{}行，{}: {}<br/>", (headRow + i + 1), name, cv.getMessage()));
                }
            }
        }

        return sb.toString();
    }

}
