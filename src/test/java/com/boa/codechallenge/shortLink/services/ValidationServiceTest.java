package com.boa.codechallenge.shortLink.services;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest extends TestCase {

    private ValidationService validationService = new ValidationService();

    @Test
    public void testValidateUrl_Success() {
        assertTrue(validationService.validateUrl("https://niceUrl.com"));
    }

    @Test
    public void testValidateUrl_Error() {
        assertFalse(validationService.validateUrl("htts://malformed.com"));
    }
}