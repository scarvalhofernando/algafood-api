package com.algaworks.algafood.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
public @interface Multiplo {

    String message() default "multiplo inválido";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    int numero();
}
