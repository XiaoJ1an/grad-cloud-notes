package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.jpa.CnUser;
import com.university.gradcloudnotes.repository.CnUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CnUserRepository cnUserRepository;

    /**
     * 需新建配置类注册一个指定的加密方式Bean，或在下一步Security配置类中注册指定
     */
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**通过用户名查询用户信息*/
        CnUser user = cnUserRepository.findAllByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        /**得到用户角色*/
        String role = user.getUserRole();
        /**角色集合*/
        List<GrantedAuthority> authorities = new ArrayList<>();
        /**角色必须以ROLE_开头，数据库中没有，则在这里加*/
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }
}

