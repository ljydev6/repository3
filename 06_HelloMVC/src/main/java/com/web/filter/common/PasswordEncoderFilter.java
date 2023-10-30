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

import com.web.common.security.PasswordEncoder;

/**
 * Servlet Filter implementation class PasswordEncoderFilter
 */
@WebFilter(servletNames = {"enrollMember","login","updateMember","updatePassword"})
public class PasswordEncoderFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 601656505059537957L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public PasswordEncoderFilter() {
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
		
		PasswordEncoder pe = new PasswordEncoder((HttpServletRequest)request);
		
		chain.doFilter(pe, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
