package cn.johnyu.filter;

import cn.johnyu.cache.UserMemCache;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 引入自定义的Filter完成对token的利用
 */
@Component
public class TokenProcessFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从request中获取token,从全局的cache中获取用户对象，并存入SecurityContext中，用于授权处理（）
        String token=request.getHeader("token");
        //请求中没有token,则放行，并使用return 保证filter的后半部分不会继续执行
        if(ObjectUtils.isEmpty(token)){
            //放行的意义在于：让后面的安全过滤工作，要么根据授权配置放行，要么产生异常，并进行相应的处理
            filterChain.doFilter(request,response);
            return;
        }
        User user= UserMemCache.findUser(token);
        //在全局Cache中查不到用户，则直接由"异常处理过滤器处理"
        if(user==null){
            throw new BadCredentialsException("令牌错误或失效");
        }

        //必须使用三个参数的构造器，保证其状态为: authenticated
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        //最终将认证信息存入到SecurityContext中,并由最终的"授权拦截器"进行"授权的处理"
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
