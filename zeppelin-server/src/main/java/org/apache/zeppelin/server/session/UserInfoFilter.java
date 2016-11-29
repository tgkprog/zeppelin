package org.apache.zeppelin.server.session;

import java.io.IOException;

import javax.servlet.Filter;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;

/*public class UserInfoFilter implements HttpSessionListener{

 @Override
 public void sessionCreated(HttpSessionEvent se) {
 // TODO Auto-generated method stub

 }

 @Override
 public void sessionDestroyed(HttpSessionEvent se) {
 // TODO Auto-generated method stub

 }

 }
 */

public class UserInfoFilter implements Filter {

	public class UserInfo {
		private String userName;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

	}

	private final ThreadLocal<UserInfo> user = new ThreadLocal<UserInfo>() {
		@Override
		protected UserInfo initialValue() {
			return new UserInfo();
		}
	};
	
	


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
			if(request instanceof HttpServletRequest){
				HttpServletRequest req = (HttpServletRequest) request;
				String u = req.getParameter("_userId");
				if(u == null || u.trim().length() == 0)u = req.getParameter("_userName");
				if(u == null || u.trim().length() == 0) u = "anon1";
				user.get().userName = u;
				
			}
			chain.doFilter(request, response);
	}
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// noOp for now

	}

	@Override
	public void destroy() {
		// noOp

	}
}