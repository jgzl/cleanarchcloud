package com.github.jgzl.common.core.validation.name;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 名称格式校验
 *
 * @author lihaifeng
 * @version 1.0.0
 * @since 2020-06-06
 */
public class NameValidator implements ConstraintValidator<Name, String> {

	private Name name;

	@Override
	public void initialize(Name name) {
		this.name = name;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
		if (StrUtil.isBlank(value)) {
			return true;
		}
		return Pattern.compile(name.regexp()).matcher(value).matches();
	}
}