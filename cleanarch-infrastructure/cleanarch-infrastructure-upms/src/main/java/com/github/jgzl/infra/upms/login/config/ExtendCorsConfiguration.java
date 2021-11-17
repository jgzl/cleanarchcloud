//package com.github.jgzl.infra.upms.login.config;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///**
// * 跨域配置
// *
// * @author lihaifeng
// * @date 2020/8/9 15:59
// */
//@Configuration
//public class ExtendCorsConfiguration {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin(org.springframework.web.cors.CorsConfiguration.ALL);
//        config.setAllowCredentials(true);
//        config.addAllowedHeader(org.springframework.web.cors.CorsConfiguration.ALL);
//        config.addAllowedMethod(org.springframework.web.cors.CorsConfiguration.ALL);
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//
//    /**
//     * 配置过滤器
//     */
//    @Bean
//    public FilterRegistrationBean someFilterRegistration() {
//        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(corsFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("corsFilter");
//        registration.setOrder(Integer.MIN_VALUE);
//        return registration;
//    }
//}
