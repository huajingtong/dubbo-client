package com.paulzhangcc.democlient.security.extent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author paul
 * @date 2017/11/30
 */
@Component
public class AccessDeniedHandlerExtend implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerExtend.class);
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.sendError(401, "权限不足.");
    }
}
