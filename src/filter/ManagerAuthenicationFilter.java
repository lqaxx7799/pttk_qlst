package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class ManagerAuthenicationFilter
 */
@WebFilter({ "/ReportMenu", "/ItemReport", "/ItemReportDetail" })
public class ManagerAuthenicationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ManagerAuthenicationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		boolean isLoggedIn = (session != null && session.getAttribute("email") != null);

		boolean isAdmin = (session != null && session.getAttribute("role") != null
				&& session.getAttribute("role").equals("manager"));

		String loginURI = httpRequest.getContextPath() + "/LogIn";

		boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

		boolean isLoginPage = httpRequest.getRequestURI().endsWith("LogIn");

		if (isLoggedIn && isAdmin && (isLoginRequest || isLoginPage)) {
			// the admin is already logged in and he's trying to login again
			// then forwards to the admin's homepage
			httpResponse.sendRedirect("./Index");

		} else if ((isLoggedIn && isAdmin) || isLoginRequest) {
			// continues the filter chain
			// allows the request to reach the destination
			chain.doFilter(request, response);

		} else {
			// the admin is not logged in, so authentication is required
			// forwards to the home page
			httpResponse.sendRedirect("./LogIn");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
