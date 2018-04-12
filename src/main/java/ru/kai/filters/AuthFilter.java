package ru.kai.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 14.03.2018
 * AuthFilter
 * <p>
 * Класс-фильтр, выполняет обработку запроса до того, как он будет обработан сервлетами.
 *
 * @version v1.0
 */
@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    // на вход фильтр получает запрос, ответ, а также цепочку фильтров, которым следует отдать запрос далее.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        // выполняем преобразование Servlet-запросов-ответов в HTTP-запросы-ответы
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";
        String signUpURI = request.getContextPath() + "/signUp";

        boolean loggedIn = session != null && session.getAttribute("user_id") != null;

        boolean isLoginRequest = request.getRequestURI().equals(loginURI);
        boolean isSignUpRequest = request.getRequestURI().equals(signUpURI);


        if (loggedIn) {
            if (isLoginRequest || isSignUpRequest) {
                servletRequest.getServletContext().getRequestDispatcher("/").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (!(isLoginRequest || isSignUpRequest)) {
                response.sendRedirect(loginURI);
            } else {
                chain.doFilter(request, response);
            }
        }


    }

    @Override
    public void destroy() {

    }
}
