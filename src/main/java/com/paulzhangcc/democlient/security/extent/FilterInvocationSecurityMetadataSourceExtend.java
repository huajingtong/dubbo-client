package com.paulzhangcc.democlient.security.extent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author paul
 * @date 2017/11/30
 */
@Component
public class FilterInvocationSecurityMetadataSourceExtend implements FilterInvocationSecurityMetadataSource {
    private static final Logger logger = LoggerFactory.getLogger(FilterInvocationSecurityMetadataSourceExtend.class);

    /**
     * return null 表示允许访问，不做拦截 返回一个数据库不存在的角色是拒绝访问
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object; //当前请求对象
        //允许请求的列表
        if (isMatcherAllowedRequest(fi)) {
            return null;
        }
        List<ConfigAttribute> configAttributes = getMatcherConfigAttribute(fi.getRequestUrl());
        return (configAttributes!=null && configAttributes.size() > 0) ? configAttributes : null; //返回匹配当前路径所需角色，如果返回Collections.singletonList(new SecurityConfig("ROLE_DENIED"))则拒绝访问
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    /**
     * 获取当前路径所需要的角色
     *
     * @param url 当前路径
     * @return 所需角色集合
     */
    private List<ConfigAttribute> getMatcherConfigAttribute(String url) {

        return "/get".equals(url) ? Collections.singletonList(new SecurityConfig("ROLE_A")) : null;
    }

    /**
     * 判断当前请求是否在允许请求的范围内
     *
     * @param fi 当前请求
     * @return 是否在范围中
     */
    private boolean isMatcherAllowedRequest(FilterInvocation fi) {
        return allowedRequest().stream().map(AntPathRequestMatcher::new)
                .filter(requestMatcher -> requestMatcher.matches(fi.getHttpRequest()))
                .toArray().length > 0;
    }

    /**
     * @return 定义允许请求的列表
     */
    private List<String> allowedRequest() {
        return
                Arrays.asList("/static/**",
                        "/login",
                        "/",
                        "/favicon.ico",
                        "/common/**"
                );
    }

}
