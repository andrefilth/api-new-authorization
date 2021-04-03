

package com.picpay.api.oauth2.infra.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 06/10/2020
 */
@Pattern(
    regexp = "^\\d+$",
    message = "Senha deve conter apenas números"
)
@NotBlank(message = "Senha não pode estar em branco")
@Size(
    min = 4,
    max = 10
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
    validatedBy = {}
)
public @interface Password {

    String message() default "{javax.validation.constraints.PicpayPassword.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
