package com.spring.vhrserve.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.vhrserve.bean.Hr;
import com.spring.vhrserve.bean.RespBean;
import com.spring.vhrserve.service.HrService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author CXB
 * @ClassName SecurityConfig
 * @date 2022/9/18 13:03
 * @Description TODO
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private HrService hrService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        Hr hr = (Hr) authentication.getPrincipal();
                        hr.setPassword("");
                        RespBean respBean = RespBean.ok("????????????", hr);
                        String s = new ObjectMapper().writeValueAsString(respBean);
                        writer.write(s);
                        writer.close();
                        writer.flush();
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                response.setContentType("applicaton/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                RespBean respBean = RespBean.error("????????????");
                if (exception instanceof LockedException){
                    respBean.setMsg("????????????????????????????????????");
                }else if (exception instanceof CredentialsExpiredException){
                    respBean.setMsg("??????????????????????????????");
                }else if (exception instanceof AccountExpiredException){
                    respBean.setMsg("??????????????????????????????");
                }else if (exception instanceof DisabledException){
                    respBean.setMsg("?????????????????????????????????");
                }else if (exception instanceof BadCredentialsException){
                    respBean.setMsg("??????????????????????????????");
                }
                String string = new ObjectMapper().writeValueAsString(respBean);
                out.write(string);
                out.flush();
                out.close();
            }
        })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest rquest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charaset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(new ObjectMapper().writeValueAsString("????????????"));
                        writer.flush();
                        writer.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}
