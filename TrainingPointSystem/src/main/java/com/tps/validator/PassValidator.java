package com.tps.validator;

import com.tps.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PassValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);    }

    @Override
    public void validate(Object target, Errors errors) {
        User u = (User) target;
        if (!u.getPassword().trim()
                .equals(u.getConfirmPassword().trim()))
            errors.rejectValue("password",
                    "user.password.error.notMatchMsg");
    }
}
