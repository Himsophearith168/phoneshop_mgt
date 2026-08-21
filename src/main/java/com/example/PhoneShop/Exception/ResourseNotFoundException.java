package com.example.PhoneShop.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourseNotFoundException extends ResourceNotFoundException {

    public ResourseNotFoundException(String message) {
        super(message);
    }

    public ResourseNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(resourceName, fieldName, fieldValue);
    }
}
