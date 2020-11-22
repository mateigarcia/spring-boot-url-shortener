package com.boa.codechallenge.shortLink.services;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class ValidationService implements IUrlValidationService{
    UrlValidator urlValidator;

    public ValidationService(){
        this.urlValidator = new UrlValidator();
    }

    @Override
    public boolean validateUrl(String url) {
        if (urlValidator.isValid(url)) {
            return true;
        }
        return false;
    }
}
