package com.boa.codechallenge.shortLink.services;

import com.boa.codechallenge.shortLink.config.PropertiesReader;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlShorteningServiceTest extends TestCase {

    @Mock
    private PropertiesReader propertiesReader;

    @InjectMocks
    UrlShorteningService urlShorteningService = new UrlShorteningService();

    @Test
    public void testGenerateShortLinkFromUrl_Success() throws MalformedURLException {
        String result = urlShorteningService.generateShortLinkFromUrl("https://url.com/mylink");
        assertEquals("url.com/1", result);
    }

    @Test(expected = MalformedURLException.class)
    public void testGenerateShortLinkFromUrl_Error() throws MalformedURLException {
        String result = urlShorteningService.generateShortLinkFromUrl("hht:malformed/mylink");
    }

    @Test
    public void testGetUrlFromShortLink_Success() throws MalformedURLException {
        when(propertiesReader.getTimeToLiveSeconds()).thenReturn(100L);
        String result = urlShorteningService.generateShortLinkFromUrl("https://url.com/mylink");
        String originalLink = urlShorteningService.getUrlFromShortLink(result);
        assertEquals("https://url.com/mylink", originalLink);
    }

    @Test
    public void testGetUrlFromShortLink_Expired() throws MalformedURLException, InterruptedException {
        when(propertiesReader.getTimeToLiveSeconds()).thenReturn(1L);
        String result = urlShorteningService.generateShortLinkFromUrl("https://url.com/mylink");
        Thread.sleep(1001);
        String originalLink = urlShorteningService.getUrlFromShortLink(result);
        assertNull(originalLink);
    }

    @Test
    public void testGetUrlFromShortLink_Error() {
        String result = urlShorteningService.getUrlFromShortLink("nonExisting");
        assertNull(result);
    }
}