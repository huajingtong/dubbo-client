package com.paulzhangcc.democlient.security.extent;

import com.paulzhangcc.democlient.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.paulzhangcc.democlient.security.filter.JwtAuthenticationTokenFilter.TOKEN_HEAD;
import static com.paulzhangcc.democlient.security.filter.JwtAuthenticationTokenFilter.TOKEN_HEADER;

/**
 * @author paul
 * @date 2017/11/30
 */
@Component
public class AuthenticationSuccessHandlerExtend implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerExtend.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        httpServletResponse.setHeader(TOKEN_HEADER,TOKEN_HEAD+jwtTokenUtil.generate((User) authentication.getPrincipal()));
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().println("{\"code\":1,\"message\":\"登录成功\"}");
        httpServletResponse.getWriter().flush();
    }
}
