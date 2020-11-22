package com.boa.codechallenge.shortLink.controller;

import com.boa.codechallenge.shortLink.request.ApiRequest;
import com.boa.codechallenge.shortLink.response.ApiResponse;
import com.boa.codechallenge.shortLink.services.UrlShorteningService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortLinkController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @PostMapping("/link")
    public ResponseEntity createShortLink(@RequestBody ApiRequest request){
        String shortLink;
        try {
            shortLink = urlShorteningService.generateShortLinkFromUrl(request.getLink());
        }catch (Exception ex){
            ApiResponse response = ApiResponse.builder().error("Error "+ex.getMessage()).build();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        ApiResponse response = ApiResponse.builder().shortUrl(shortLink).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/link")
    public ResponseEntity getShortLink(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "https://www.google.com");
        return new ResponseEntity<String>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
