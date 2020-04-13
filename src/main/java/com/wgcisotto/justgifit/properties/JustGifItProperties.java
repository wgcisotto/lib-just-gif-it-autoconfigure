package com.wgcisotto.justgifit.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Data
@Component
@ConfigurationProperties(prefix = "com.justgifit")
public class JustGifItProperties {

    private File gifLocation;

    private Boolean optimize;

}
