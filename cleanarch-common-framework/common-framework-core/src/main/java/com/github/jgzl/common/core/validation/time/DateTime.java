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
@Constraint(validatedBy = {DateTimeValidator.class})
public @interface DateTime {


	String message() default "格式错误";

	String format() default "yyyy-MM-dd";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
