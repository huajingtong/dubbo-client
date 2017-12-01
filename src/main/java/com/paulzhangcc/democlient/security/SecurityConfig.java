package com.paulzhangcc.democlient.security;

import com.paulzhangcc.democlient.security.extent.*;
import com.paulzhangcc.democlient.security.filter.JwtAuthenticationTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;

/**
 * @author paul
 * @date 2017/11/30
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private AccessDeniedHandlerExtend accessDeniedHandlerExtend;
    @Autowired
    private AuthenticationFailureHandlerExtend authenticationFailureHandlerExtend;
    @Autowired
    private AuthenticationSuccessHandlerExtend authenticationSuccessHandlerExtend;
    @Autowired
    private FilterSecurityInterceptorPostProcessor filterSecurityInterceptorPostProcessor;
    @Autowired
    private UserDetailsServiceExtend userDetailsServiceExtend;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * @return 封装身份认证提供者
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceExtend);  //自定义的用户和角色数据提供者
        authenticationProvider.setPasswordEncoder(passwordEncoder); //设置密码加密对象
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider()); //设置身份认证提供者
    }

    @Bean
    public Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .withObjectPostProcessor(filterSecurityInterceptorPostProcessor)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // 默认参数:username password /login POST
                .successHandler(authenticationSuccessHandlerExtend)
                .failureHandler(authenticationFailureHandlerExtend)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerExtend)

        ;
        http.headers().cacheControl();

    }
}
