package com.cmu.edu.ebiz.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.cmu.edu.ebiz.pojo.User;

/**
 * This is a class of filter to manage the authority of the system.
 * @author kewei wang
 *
 */
public class AuthorizationFilter implements Filter {

	public static final String REDIRECT_URL = "redirectUrl";
	public static final String LOGIN_PAGE = "/index";
	public static final String LOGIN_ACTION = "/login";
	public static final String DENY_PAGE = "/noauth";
	public static final String ERROR_PAGE = "/error";
	public static final String STUDENT_INDEX_PAGE = "/student/";
	public static final String ADMIN_INDEX_PAGE = "/admin/welcome";

	private static final String HTTP = "http://";
	private static final String HOST = "Host";
	private static final String SEG = ";";

	/*Exception pages' url*/
	private static final String KEY_EXCEPTION_PAGE = "/noauth.action;/noauth;/error.action;/error;/logout.action;/logout;/style/*;/images/*;/js/*;/codebase/*;/register/*";

	private String[] exceptionUrls;
	private boolean enable;

	/**
	 * prepare the filter
	 */
	public void init(FilterConfig config) throws ServletException {
		this.exceptionUrls = KEY_EXCEPTION_PAGE.split(SEG);
		String str = config.getInitParameter("enable");
		if (str != null && str.equalsIgnoreCase("true")) {
			this.enable = true;
		} else {
			this.enable = false;
		}
	}

	public void destroy() {
		// empty
	}

	/**
	 * Logic for filter
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String redirectURL = req.getRequestURI();
		HttpSession session = req.getSession(false);
		String path = HTTP + req.getHeader(HOST) + req.getContextPath();

		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Cache-Control", "no-store");
		res.setDateHeader("Expires", 0);

		try {
			if (this.enable) {
				if (session == null && redirectURL.endsWith(LOGIN_PAGE)) {
					req.setAttribute("timeout", true);
					chain.doFilter(request, response);
					return;
				}
				session = req.getSession(true);
				Status flag = Status.ALLOW;
				if (isExceptionUrl(redirectURL)) {
					chain.doFilter(request, response);
				} else {
					User user = (User) session
							.getAttribute(LoginController.USER);
					if (null == user) {
						flag = Status.LOGIN;
					} else if (!isAllow(user, redirectURL)) {
						flag = Status.DENY;
					} else if (redirectURL.equals(LOGIN_PAGE) || redirectURL.equals(LOGIN_ACTION)) {
						if (user.getType() == User.USER_ADMIN) {
							res.sendRedirect(path + ADMIN_INDEX_PAGE);
						} else {
							res.sendRedirect(path + STUDENT_INDEX_PAGE);
						}
						return;
					}

					switch (flag) {
					case LOGIN: {
						if (redirectURL.endsWith(LOGIN_ACTION) || redirectURL.endsWith(LOGIN_PAGE)) {
							chain.doFilter(request, response);
						} else {
							res.sendRedirect(path + LOGIN_PAGE);
						}
						break;
					}
					case DENY: {
						res.sendRedirect(path + DENY_PAGE);
						break;
					}
					case ALLOW: {
						chain.doFilter(request, response);
						break;
					}
					default: {
						break;
					}
					}
				}
			} else {
				chain.doFilter(request, response);
			}

		} catch (Exception e) {
			res.sendRedirect(path + ERROR_PAGE);
			e.printStackTrace();
		}
	}

	private boolean isAllow(User user, String redirectURL) {
		if (redirectURL.equals(LOGIN_PAGE) || redirectURL.equals(LOGIN_ACTION)) {
			return true;
		}
		if (user.getType() == User.USER_STUDENT
				&& redirectURL.startsWith("/student/")) {
			return true;
		} else if (user.getType() == User.USER_ADMIN
				&& redirectURL.startsWith("/admin/")) {
			return true;
		}
		return false;
	}

	private boolean isExceptionUrl(String url) {
		boolean flag = false;
		if (contains(exceptionUrls, url)) {
			flag = true;
		} else {
			for (String eUrl : exceptionUrls) {
				if (eUrl.matches(".*/*.*")) {
					if (url.matches(".*" + eUrl + ".*")) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	private boolean contains(String[] list, String str) {
		boolean flag = false;
		for (String s : list) {
			if (s.equals(str)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	private enum Status {
		ALLOW, LOGIN, DENY;
	}
	
	public static String MD5(String plainText) {
		String str = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			// empty
		}
		return str;
	}
}