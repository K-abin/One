package com.spring.vhrserve.service;

import com.spring.vhrserve.bean.Hr;
import com.spring.vhrserve.mapper.HrMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author CXB
 * @ClassName HrService
 * @date 2022/9/18 12:35
 * @Description TODO
 */
@Service
public class HrService implements UserDetailsService {

    @Resource
    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.loginUsername(s);

        if (hr==null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        return hr;
    }
}
