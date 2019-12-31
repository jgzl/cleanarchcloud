package com.gitee.common.core.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gitee.common.core.util.WebUtils;

/**
 * @author: lihaifeng
 * @version: 2019-07-01 17:55
 * 上下文添加过滤器
 **/
public class WebContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
        WebUtils.bindContext((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        try {
            chain.doFilter(servletRequest, servletResponse);
        } finally {
            WebUtils.clearContext();
        }
    }

    @Override
    public void destroy() {

    }
}
