package com.wantedalways.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取访问路径
        String path = request.getServletPath();

        // 登录界面则放行
        if ("/login.jsp".equals(path)) {

            filterChain.doFilter(request, response);

        } else {

            // 验证登录令牌
            if (request.getSession().getAttribute("user") == null) {

                response.sendRedirect(request.getContextPath() + "/login.jsp");

            } else {

                filterChain.doFilter(request, response);

            }
        }
    }


    @Override
    public void destroy() {

    }
}
