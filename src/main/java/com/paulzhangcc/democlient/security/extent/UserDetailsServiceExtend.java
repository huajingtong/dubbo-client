package com.paulzhangcc.democlient.security.extent;

import com.paulzhangcc.democlient.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库获取用户信息
 */
@Component
public class UserDetailsServiceExtend implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceExtend.class);

    public static final ConcurrentHashMap<String,String> cache = new ConcurrentHashMap();
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        return User.withUsername(loginName).password(cache.get(loginName)).authorities(new ArrayList<>()).build();
    }

    public boolean insert(String username,String password){
        if (!cache.containsKey(username)) {
            cache.put(username, SecurityConfig.passwordEncoder.encode(password));
            return true;
        }
        return false;

    }
}
