package com.boa.codechallenge.shortLink;

import com.boa.codechallenge.shortLink.config.PropertiesReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(PropertiesReader.class)
public class ShortLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortLinkApplication.class, args);
	}

}
