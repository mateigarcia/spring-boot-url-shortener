package com.boa.codechallenge.shortLink.services;

import com.boa.codechallenge.shortLink.config.PropertiesReader;
import com.boa.codechallenge.shortLink.entity.TimedUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;

@Component
public class UrlShorteningService implements IUrlShorteningService {

    @Autowired
    private PropertiesReader propertiesReader;

    private HashMap<String, TimedUrl> urlTables;
    long counter;

    public UrlShorteningService(){
        urlTables = new HashMap<>();
        counter = 0;
    }

    @Override
    public String generateShortLinkFromUrl(String url) throws MalformedURLException {
        counter = counter + 1;
        URL urlLoc = new URL(url);
        StringBuilder sb = new StringBuilder();
        sb.append(urlLoc.getHost());
        sb.append("/");
        sb.append(counter);

        TimedUrl timedUrl = new TimedUrl(url, LocalTime.now());
        urlTables.put(sb.toString(), timedUrl);
        return sb.toString();
    }

    @Override
    public String getUrlFromShortLink(String shortLink) {
        TimedUrl url = urlTables.get(shortLink);
        LocalTime beforeThan = LocalTime.now().minusSeconds(propertiesReader.getTimeToLiveSeconds());

        if(url == null || url.getTime().isBefore(beforeThan)){
            return null;
        }
        return url.getUrl();
    }
}
