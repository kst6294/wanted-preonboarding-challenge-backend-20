//package com.example.wanted_market.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//@EnableWebMvc
//@RequiredArgsConstructor
//public class WebMvcConfig implements WebMvcConfigurer {
////    private final UserIdResolver userIdResolver;
////
////    @Override
////    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
////        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
////        resolvers.add(userIdResolver);
////    }
//
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(new UserIdInterceptor())
////                .addPathPatterns("/**")
////                .excludePathPatterns(Constant.NO_NEED_AUTH_URLS)
////        ;
////    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/")
//                .setCachePeriod(20)
//        ;
//    }
//}
