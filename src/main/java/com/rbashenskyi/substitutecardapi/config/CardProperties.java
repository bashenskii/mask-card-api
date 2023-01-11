package com.rbashenskyi.substitutecardapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "card")
@Getter
@Setter
public class CardProperties {

    private String visaRegex;
    private String visaMask;
    private String mastercardRegex;
    private String mastercardMask;
}
