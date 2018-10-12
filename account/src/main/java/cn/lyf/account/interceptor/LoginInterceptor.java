package cn.lyf.account.interceptor;

import cn.lyf.account.bean.SessionData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static cn.lyf.account.util.Constants.*;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    //private RedisUtil redisUtils;
    private final static String SESSION_KEY_PREFIX = "session:";
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }
       // handlerSession(request);

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();
        final Class<?> clazz = method.getDeclaringClass();
        //clazz.isAnnotationPresent(Auth.class判断类里面是否存在Auth注解
        if (clazz.isAnnotationPresent(Auth.class) ||
                method.isAnnotationPresent(Auth.class)) {
            if(request.getSession().getAttribute(USER_SESSION_KEY) == null){
                log.info("用户未登录，请登录");
                response.sendRedirect("/package/index.html");
                return false;
            }else{
                log.info("用户已经登录");
                return true;
            }
        }

        return true;

    }


    public void handlerSession(HttpServletRequest request) {
        String sessionId = request.getHeader(SESSION_KEY);
        log.info("请求到此！handlerSession");
        if(StringUtils.isBlank(sessionId)){
            sessionId=(String) request.getSession().getAttribute(SESSION_KEY);
        }
        log.info("获取到的sessionID="+sessionId);
        if (StringUtils.isNotBlank(sessionId)) {
            SessionData model = null;//(SessionData) redisUtils.get(SESSION_KEY_PREFIX+sessionId);
            if (model == null) {
                return ;
            }
            request.setAttribute(SESSION_KEY,sessionId);
            String userName = model.getUserName();
            if (userName != null) {
                request.setAttribute(USER_NAME_SESSION_KEY, userName);
            }
            String account = model.getAccount();
            if (account != null) {
                request.setAttribute(USER_SESSION_KEY, account);
            }
        }
        return ;
    }
}
