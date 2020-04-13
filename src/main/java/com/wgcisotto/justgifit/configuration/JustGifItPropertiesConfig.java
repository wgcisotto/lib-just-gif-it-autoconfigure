package com.wgcisotto.justgifit.configuration;

import com.wgcisotto.justgifit.properties.JustGifItProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties(JustGifItProperties.class)
public class JustGifItPropertiesConfig {

//    I could have used in both ways... @EnableConfigurationProperties or creating a @Bean
    @Bean
    public JustGifItProperties justGifItProperties(){
        return new JustGifItProperties();
    }


}
