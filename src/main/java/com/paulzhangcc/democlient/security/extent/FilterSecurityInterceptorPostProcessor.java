package com.paulzhangcc.democlient.security.extent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paul
 * @date 2017/11/30
 */
@Component
public class FilterSecurityInterceptorPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {

    @Autowired
    private FilterInvocationSecurityMetadataSourceExtend filterInvocationSecurityMetadataSourceExtend;
    @Override
    public <T extends FilterSecurityInterceptor> T postProcess(T filterSecurityInterceptor) {
        List<AccessDecisionVoter<? extends Object>> list = new ArrayList<>();
        list.add(new AuthenticatedVoter());
        list.add(new RoleVoter());
        AffirmativeBased affirmativeBased = new AffirmativeBased(list);
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased); //权限决策处理类
        filterSecurityInterceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSourceExtend); //路径（资源）拦截处理
        return filterSecurityInterceptor;
    }
}
