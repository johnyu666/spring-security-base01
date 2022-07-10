package cn.johnyu.controller;


import cn.johnyu.cache.UserMemCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class AuthenController {
    @Autowired private AuthenticationManager authenticationManager;
    @PostMapping("/user/login")
    public String login(String username,String password){
        //封装成为一个"未认证的Authentication",由AuthenticationManager,交给IOC中的DetaileService对象处理
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);

        //经认证处理器认证后，返回一个已经认证的"认证对象"，失败时将会有一个AuthenticationException（RunTime类型）
        Authentication authentication=  authentication = authenticationManager.authenticate(authenticationToken);
        //以上认证处理如果出现异常，程序到此即终止，并转交给"异常过滤器进行处理，返回403"
        //从UserDetailService中获取一个UserDetail对象
        User principal = (User) authentication.getPrincipal();

        //存入到全局的Cache中
        String token= UUID.randomUUID().toString();
        UserMemCache.addUser(token,principal);
        //返回的token，将来做授权的依据
        return token;
    }


    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request){
        String token=request.getHeader("token");
        //此资源也受到了保护，可以保证可以从request中获取token
        UserMemCache.deleteUser(token);
        return "注销成功";
    }
}
