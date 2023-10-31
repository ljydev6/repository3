package com.web.filter.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.common.exception.BadAccessException;
import com.web.member.model.dto.Member;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */
@WebFilter(servletNames = {"updateMember","memberView","updatePassword","memberList","searchMember",
							"deleteNotice","noticeWrite","noticeWriteEnd","noticeUpdate","noticeUpdateEnd"})
public class LoginCheck1Filter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 8389636738956999604L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public LoginCheck1Filter() {
        super();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		if(loginMember == null) {
			throw new BadAccessException("로그인이 필요한 페이지입니다.");
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
