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
 * Servlet Filter implementation class LoginCheckAdminFilter
 */
@WebFilter(servletNames = {"deleteNotice","noticeWrite","noticeWriteEnd","noticeUpdate","noticeUpdateEnd"})
public class LoginCheck2AdminFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 8332895359214282629L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public LoginCheck2AdminFilter() {
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
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Member mem = (Member)session.getAttribute("loginMember");
		if(!mem.getUserid().equals("admin")) throw new BadAccessException("권한이 충분하지 않습니다.");
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
