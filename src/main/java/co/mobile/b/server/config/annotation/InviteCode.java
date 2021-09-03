package co.mobile.b.server.config.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.FIELD;

@Constraint(validatedBy = InviteCode.CodeValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface InviteCode {

    String message() default "초대링크는 숫자 또는 알파벳, 한글로만 가능합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    class CodeValidator implements ConstraintValidator<InviteCode, String> {
        private final String REGEX_CODE = "^[0-9a-zA-Z가-힣]*$";
        public Pattern code = Pattern.compile(REGEX_CODE);

        @Override
        public boolean isValid(String code, ConstraintValidatorContext context) {
            if(StringUtils.isEmpty(code)) {
                return true; // null 처리는 @NotEmpty로 검사
            } else {
                return this.code.matcher(code).matches();
            }
        }

    }
}

