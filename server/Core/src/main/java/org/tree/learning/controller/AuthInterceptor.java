package org.tree.learning.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.tree.commons.support.controller.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author er_dong_chen
 * @date 2019/1/14
 */
public class AuthInterceptor implements HandlerInterceptor {
    /* 排除的 uri 是 excludes 和 includes 的差集 */
    private String[] excludes, includes;

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }

    /* exclude 可以是目录路径和全路径，include 只能是全路径 */
    private boolean exclude(String context, String uri) {
        uri = uri.replace(context, "");
        for (String include : includes)
            if (include.equals(uri))
                return false;
        for (String exclude : excludes)
            if ((exclude.endsWith("/") && uri.startsWith(exclude)) || exclude.equals(uri))
                return true;
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String context = httpServletRequest.getContextPath();

        Object uri = httpServletRequest.getRequestURI();
        if (uri != null && exclude(context, (String) uri))
            return true;

        Object login = httpServletRequest.getSession(true).getAttribute("login");
        if (login != null && (boolean) login)
            return true;

        httpServletResponse.getWriter().write(JSON.toJSONString(new Result()));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
