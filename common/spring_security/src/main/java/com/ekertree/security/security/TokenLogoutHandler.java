package com.ekertree.security.security;

import com.ekertree.servicebase.utils.R;
import com.ekertree.servicebase.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: TokenLogouthandler
 * Description:
 * date: 2022/5/25 12:26
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class TokenLogoutHandler implements LogoutHandler {

    private  TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //从header里面获取token
        String token = request.getHeader("token");
        //token不为空 移除token 从redis删除token
        if(token != null){
            //移除
            tokenManager.removeToken(token);
            //从token中获取用户名
            String username = tokenManager.getUserInfoFromToken(token);
            redisTemplate.delete(username);
        }
        ResponseUtil.out(response, R.ok());
    }
}
