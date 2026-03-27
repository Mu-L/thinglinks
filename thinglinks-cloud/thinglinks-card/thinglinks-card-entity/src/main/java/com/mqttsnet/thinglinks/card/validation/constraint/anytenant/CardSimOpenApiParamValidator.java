package com.mqttsnet.thinglinks.card.validation.constraint.anytenant;

import com.mqttsnet.thinglinks.card.vo.param.CardSimOpenApiParam;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

/**
 * -----------------------------------------------------------------------------
 * File Name: CardSimOpenApiParamValidator
 * -----------------------------------------------------------------------------
 * Description:
 * <p>
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/16       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/16 10:53
 */
public class CardSimOpenApiParamValidator implements ConstraintValidator<ValidCardSimOpenApiParam, CardSimOpenApiParam> {

    @Override
    public void initialize(ValidCardSimOpenApiParam constraintAnnotation) {
    }

    @Override
    public boolean isValid(CardSimOpenApiParam param, ConstraintValidatorContext context) {
        if (param == null) {
            context.buildConstraintViolationWithTemplate("参数不能为空")
                    .addConstraintViolation();
            return false;
        }

        boolean tenantIdPresent = Optional.ofNullable(param.getTenantId()).filter(s -> !s.isEmpty()).isPresent();
        boolean iccidPresent = Optional.ofNullable(param.getIccid()).filter(s -> !s.isEmpty()).isPresent();
        boolean cardNumberPresent = Optional.ofNullable(param.getCardNumber()).filter(s -> !s.isEmpty()).isPresent();

        if (!tenantIdPresent) {
            context.buildConstraintViolationWithTemplate("租户ID不能为空")
                    .addPropertyNode("tenantId")
                    .addConstraintViolation();
        }

        if (!iccidPresent && !cardNumberPresent) {
            context.buildConstraintViolationWithTemplate("ICCID和卡号必须存在一个")
                    .addPropertyNode("iccid")
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate("ICCID和卡号必须存在一个")
                    .addPropertyNode("cardNumber")
                    .addConstraintViolation();
        }

        // 禁用默认的错误消息
        context.disableDefaultConstraintViolation();

        return tenantIdPresent && (iccidPresent || cardNumberPresent);
    }
}
