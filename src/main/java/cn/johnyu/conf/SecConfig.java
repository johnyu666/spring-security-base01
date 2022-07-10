package cn.johnyu.conf;

import cn.johnyu.controller.AuthenController;
import cn.johnyu.filter.TokenProcessFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecConfig extends WebSecurityConfigurerAdapter {
    @Autowired private TokenProcessFilter tokenProcessFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/books/find").hasAuthority("USER")
                .antMatchers("/books/del").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        http.addFilterBefore(tokenProcessFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 向IOC 注入 AuthenticationManager 对象，用于登录时的认证
     * @see AuthenController#login(String, String) 
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
