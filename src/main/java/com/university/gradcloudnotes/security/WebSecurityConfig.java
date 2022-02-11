package com.university.gradcloudnotes.security;

import com.university.gradcloudnotes.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) /**开启方法级安全验证*/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public CustomUserDetailsService userDatailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 从数据库读取的用户进行身份认证
                .userDetailsService(userDatailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/cloud/user/login").permitAll() /**登录接口不拦截*/
                .antMatchers("/api/cloud/user/register").permitAll() /**注册接口不拦截*/
                .antMatchers("/api/cloud/**").permitAll() /**暂时先全部开放*/
                .anyRequest().authenticated() // 所有请求都需要验证
                .and()
                .csrf().disable();// post请求要关闭csrf验证,不然访问报错；实际开发中开启，需要前端配合传递其他参数
    }
}

