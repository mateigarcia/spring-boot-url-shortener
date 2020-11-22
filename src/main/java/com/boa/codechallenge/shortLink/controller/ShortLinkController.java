package com.boa.codechallenge.shortLink.controller;

import com.boa.codechallenge.shortLink.request.ApiRequest;
import com.boa.codechallenge.shortLink.response.ApiResponse;
import com.boa.codechallenge.shortLink.services.UrlShorteningService;
import com.boa.codechallenge.shortLink.services.ValidationService;
import org.apache.commons.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortLinkController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/link")
    public ResponseEntity createShortLink(@RequestBody final ApiRequest request) {
        String shortLink;
        try {
            if (!validationService.validateUrl(request.getLink())) {
                throw new ValidatorException("Given Url is Invalid");
            }
            shortLink = urlShorteningService.generateShortLinkFromUrl(request.getLink());
        } catch (Exception ex) {
            ApiResponse response = ApiResponse.builder().error("Error " + ex.getMessage()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        ApiResponse response = ApiResponse.builder().shortUrl(shortLink).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/link")
    public ResponseEntity redirectToLink(@RequestParam final String shortUrl) {
        String destinationUrl = urlShorteningService.getUrlFromShortLink(shortUrl);
        if (destinationUrl == null) {
            ApiResponse response = ApiResponse.builder().error("Error recevied short link is invalid or expired").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", destinationUrl);
        return new ResponseEntity<String>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
