package com.boa.codechallenge.shortLink.controller;

import com.boa.codechallenge.shortLink.request.ApiRequest;
import com.boa.codechallenge.shortLink.response.ApiResponse;
import com.boa.codechallenge.shortLink.services.UrlShorteningService;
import com.boa.codechallenge.shortLink.services.ValidationService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortLinkControllerTest extends TestCase {
    @Mock
    private UrlShorteningService urlShorteningService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ShortLinkController shortLinkController;

    @Test
    public void testCreateShortLink_Succes() throws MalformedURLException {
        when(urlShorteningService.generateShortLinkFromUrl("https://test.com/mytest")).thenReturn("test.com/1");
        when(validationService.validateUrl("https://test.com/mytest")).thenReturn(true);
        ApiRequest request = new ApiRequest();
        request.setLink("https://test.com/mytest");
        ResponseEntity response = shortLinkController.createShortLink(request);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("test.com/1", ((ApiResponse)response.getBody()).getShortUrl());
    }

    @Test
    public void testCreateShortLink_Error() {
        when(validationService.validateUrl("https://test.com/mytest")).thenReturn(false);
        ApiRequest request = new ApiRequest();
        request.setLink("https://test.com/mytest");
        ResponseEntity response = shortLinkController.createShortLink(request);
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error Given Url is Invalid", ((ApiResponse)response.getBody()).getError());
    }

    @Test
    public void testRedirectToLink_Success() {
        when(urlShorteningService.getUrlFromShortLink("short/1")).thenReturn("http://fullurl.com");
        ResponseEntity response = shortLinkController.redirectToLink("short/1");
        assertEquals(301, response.getStatusCode().value());
        assertTrue(response.getHeaders().get("Location").contains("http://fullurl.com"));
    }

    @Test
    public void testRedirectToLink_Error() {
        when(urlShorteningService.getUrlFromShortLink("short/1")).thenReturn("http://fullurl.com");
        ResponseEntity response = shortLinkController.redirectToLink("short/1");
        assertEquals(301, response.getStatusCode().value());
        assertTrue(response.getHeaders().get("Location").contains("http://fullurl.com"));
    }

}