package kr.ac.jipark09.valid;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import kr.ac.jipark09.domain.User;


public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz); // 해당 자손까지 검사
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String id = user.getId();

        // errors 객체에다가 필드는 id로 에러코드를 required로 저장해라
        ValidationUtils.rejectIfEmpty(errors, "id", "required");
        ValidationUtils.rejectIfEmpty(errors, "pwd", "required");

        if(id == null || id.length() < 5 || id.length() > 12) {
            // errors에 필드를 id로 invalidLength 에러코드를 저장
            errors.rejectValue("id", "invalidLength");
        }
    }
}
