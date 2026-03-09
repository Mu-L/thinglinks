package com.mqttsnet.thinglinks.card.validation.constraint.anytenant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * -----------------------------------------------------------------------------
 * File Name: ValidCardSimOpenApiParam
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
 * @date 2024/7/16 10:52
 */
@Constraint(validatedBy = CardSimOpenApiParamValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCardSimOpenApiParam {
    String message() default "租户ID必传，ICCID和卡号必须存在一个";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
