package com.wuzh.myzongce.filter;

import com.alibaba.fastjson.JSON;
import com.wuzh.myzongce.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther :Wuzh
 * @date :2022/12/24/19:12
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        String[] urls = new String[]{
                "/user/login",
                "/user/logout",
                "/add/photo/",
                "/add/book",
                "/add/info",
                "/file/img/*",
                "/testUpload/",
                "/user/infos",
                "/user/update",
                "/people/getall",
                "/add/infoall",
                "/add/updatestatus",
                "/permissions/**",
                "/prize/**"
        };

        System.out.println("放的session");
        System.out.println(request.getSession().getAttribute("user"));
        boolean check = check(urls, requestURI);
        if (check) {
            log.info("本次请求不处理");
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户名不为空");
            filterChain.doFilter(request, response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.success("notlogin")));
        log.info("拦截到请求：{}", request.getRequestURI());
        return;


    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }

        }
        return false;
    }
}
