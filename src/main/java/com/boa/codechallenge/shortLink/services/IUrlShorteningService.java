package com.boa.codechallenge.shortLink.services;

import java.net.MalformedURLException;

public interface IUrlShorteningService {
    String generateShortLinkFromUrl(String url) throws MalformedURLException;
    String getUrlFromShortLink(String shortLink);
}
