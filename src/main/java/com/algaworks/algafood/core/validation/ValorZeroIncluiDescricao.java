package com.algaworks.algafood.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
public @interface ValorZeroIncluiDescricao {

    String message() default "descrição obrigatória inválida";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String valorField();

    String descricaoField();

    String descricaoObrigatoria();
}
