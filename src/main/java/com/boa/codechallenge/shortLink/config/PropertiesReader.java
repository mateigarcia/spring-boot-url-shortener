package com.boa.codechallenge.shortLink.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties
@PropertySource(value = {"classpath:application.properties"})
@Getter
@Setter
public class PropertiesReader {
    @Value("${shortLinkApplication.linkTimeToLiveSeconds}")
    private long timeToLiveSeconds;
}
