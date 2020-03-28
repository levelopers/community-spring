package com.forum.forum.security.jwt;

import com.forum.forum.mapper.UserMapper;
import com.forum.forum.model.User;
import com.forum.forum.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/22 23:31
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = getToken(request);
        if (StringUtils.isNotBlank(token)) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String user =jwtProvider.getUserAccount(token);
        if (user != null) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(user);
            List<User> users = userMapper.selectByExample(userExample);
//            UserEntity userEntity = userJPA.findByUsername(user);
            // TODO table authories constraints user
//            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(users.get(0).getRole());
            ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
//            list.add(sga);
            UsernamePasswordAuthenticationToken auth
                    = new UsernamePasswordAuthenticationToken(
                    users.get(0).getUsername(),
                    null,
                    list);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return auth;
        }
        return null;
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
