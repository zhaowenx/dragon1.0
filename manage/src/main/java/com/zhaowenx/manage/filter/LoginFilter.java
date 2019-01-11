package com.zhaowenx.manage.filter;

import com.zhaowenx.manage.constant.CookieNameConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaowenx on 2018/8/23.
 */
@Component
public class LoginFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private static final String SESSION_ID = "sessionId";

    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();

    static {
        patterns.add(Pattern.compile(".*/static/.*"));
        patterns.add(Pattern.compile(".*/login/.*"));
        patterns.add(Pattern.compile(".*/favicon.ico"));
        patterns.add(Pattern.compile(".*/toIndex.html"));
        patterns.add(Pattern.compile(".*/html/login.html"));
        patterns.add(Pattern.compile(".*/api/customer/.*"));
        patterns.add(Pattern.compile(".*/user/.*"));
        patterns.add(Pattern.compile(".*/html/error.html"));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("==>LoginFilter初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将请求转换成HttpServletRequest 请求
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        getUrl(req);
        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (isInclude(url)){
            filterChain.doFilter(req, resp);
            return;
        } else {
            HttpSession session = req.getSession(true);
            Object sessionId = session.getAttribute(SESSION_ID);
            logger.info("filter拦截器：sessionId:"+sessionId);
            if (sessionId == null) {
//                req.setAttribute("flag","0");
//                req.setAttribute("msg","登录超时或未登录");
//                req.setAttribute("code","50   0");
                resp.setHeader("Cache-Control", "no-store");
                resp.setDateHeader("Expires", 0);
                resp.setHeader("Pragma", "no-cache");
                //此处设置了访问静态资源类
                String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
                resp.sendRedirect(basePath + "/html/login.html");
//                req.getRequestDispatcher("login.html").forward(req, resp);
            } else {
                // Filter 只是链式处理，请求依然转发到目的地址。
                filterChain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 日志打印每一次请求后台的地址
     */
    public void getUrl(HttpServletRequest request){
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String url = request.getRequestURI().substring(request.getContextPath().length());
        if(!url.contains("static")){
            logger.info("******URL******: [ "+basePath+url+" ]");
        }
    }

    public boolean checkCookie(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return false;
        }
        String redisKey = "";
        for (Cookie cookie : cookies) {
            // 找到匹配的COOKIE信息
            if (CookieNameConstant.LOGIN_COOKIE.equals(cookie.getName())) {
                redisKey = cookie.getValue();
            }
        }
        logger.info("LoginFilter|checkCookie|redisKey:"+redisKey);
        if(StringUtils.isBlank(redisKey)){
            return false;
        }else{
            return true;
        }
    }

}
