package com.github.jgzl.common.core.validation.time;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lihaifeng
 * @since 2020/6/6
 */
@Target({
		METHOD,
		FIELD,
		ANNOTATION_TYPE,
		CONSTRUCTOR,
		PARAMETER
})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {LocalDateTimeValidator.class})
public @interface LocalDateTime {


	String message() default "格式错误";

	String format() default "yyyy-MM-dd HH:mm:ss";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
