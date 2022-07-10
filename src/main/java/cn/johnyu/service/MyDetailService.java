package cn.johnyu.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        switch (username){
            case "john":
                return new User(username,"123", AuthorityUtils.createAuthorityList("ADMIN","USER"));
            case "tom":
                return new User(username,"123", AuthorityUtils.createAuthorityList("USER"));
            default:
                throw new UsernameNotFoundException("用户名或密码错");
        }

    }
}
