package com.wgcisotto.justgifit.configuration;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.wgcisotto.justgifit.properties.JustGifItProperties;
import com.wgcisotto.justgifit.services.ConverterService;
import com.wgcisotto.justgifit.services.GifEncoderService;
import com.wgcisotto.justgifit.services.VideoDecoderService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnClass({FFmpegFrameGrabber.class, AnimatedGifEncoder.class})
public class JustGifItConfig {

    @Autowired
    private JustGifItProperties properties;

    @Bean
    @ConditionalOnProperty(prefix = "com.justgifit", name = "create-result-dir")
    public Boolean createResultDir() {
        if (!properties.getGifLocation().exists()) {
            properties.getGifLocation().mkdir();
        }
        return true;
    }

    @Bean
    @ConditionalOnMissingBean(VideoDecoderService.class)
    public VideoDecoderService videoDecoderService(){
        return new VideoDecoderService();
    }

    @Bean
    @ConditionalOnMissingBean(GifEncoderService.class)
    public GifEncoderService gifEncoderService(){
        return new GifEncoderService();
    }

    @Bean
    @ConditionalOnMissingBean(ConverterService.class)
    public ConverterService converterService(){
        return new ConverterService();
    }

    @Configuration
    @ConditionalOnWebApplication
    public static class WebConfiguration implements WebMvcConfigurer {

        @Value("${multipart.location}/gif/")
        private String gifLocation;

//        @Bean
//        @ConditionalOnProperty(prefix = "com.justgifit", name = "optimize")
//        public FilterRegistrationBean deRegisterHiddenHttpMehodFilter (HiddenHttpMethodFilter hiddenHttpMethodFilter){
//            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(hiddenHttpMethodFilter);
//            filterRegistrationBean.setEnabled(false);
//            return filterRegistrationBean;
//        }

//        @Bean
//        @ConditionalOnProperty(prefix = "com.justgifit", name = "optimize")
//        public FilterRegistrationBean deRegisterHttpPutFormContent(HttpPutFormContentFilter httpPutFormContentFilter){
//            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(httpPutFormContentFilter);
//            filterRegistrationBean.setEnabled(false);
//            return filterRegistrationBean;
//        }

        @Bean
        @ConditionalOnProperty(prefix = "com.justgifit", name = "optimize")
        public FilterRegistrationBean deRegisterRequestContextFilter(RequestContextFilter requestContextFilter){
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(requestContextFilter);
            filterRegistrationBean.setEnabled(false);
            return filterRegistrationBean;
        }



        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/gif/**")
                    .addResourceLocations("file:" + gifLocation);
        }

    }

}
