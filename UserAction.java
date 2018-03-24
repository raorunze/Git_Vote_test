package com.action;

import com.entity.VoteUser;
import com.opensymphony.xwork2.ActionSupport;
import com.service.IUserService;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport
		implements ServletRequestAware, ServletResponseAware, SessionAware, ApplicationAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private IUserService service;
	private Map<String, Object> session;
	private Map<String, Object> application;
	
	public Map<String, Object> getApplication() {
		return application;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public IUserService getService() {
		return service;
	}

	@Autowired
	public void setService(IUserService service) {
		this.service = service;
	}

	@Override
	public String execute() throws Exception  {
		PrintWriter out = response.getWriter();

		String userName = request.getParameter("vu_user_id");
		String userpassword = request.getParameter("vu_password");

		Map<String, Object> map = service.selectOne(userName, userpassword);
		String message = (String) map.get("str");
		VoteUser user = (VoteUser) map.get("user");
		if (message.equals("登录成功")) {
			System.out.println("adasdasd");
			session.put("username", userName);
			session.put("user", user);
		}
		out.print(message);
		out.flush();
		out.close();
		return super.execute();
	}
	/*
	 * public void validate() {
	 * 
	 * if (user.getVuUserName() == null ||
	 * user.getVuUserName().trim().equals("")) { addFieldError("name",
	 * "用户名不能为空!!!"); }
	 * 
	 * if (user.getVuPassword() == null ||
	 * user.getVuPassword().trim().equals("")) { addFieldError("password",
	 * "密码不能为空!!!"); }
	 * 
	 * addActionError("test ActionError");
	 * addActionMessage("test ActionMessage");
	 * 
	 * System.out.println("-------------------");
	 * 
	 * }
	 */

	/*
	 * 依赖注入 DI
	 * 
	 * @see
	 * org.apache.struts2.interceptor.RequestAware#setRequest(java.util.Map)
	 */

	public void setSession(Map<String, Object> session) {

		this.session = session;
	}

	public void setApplication(Map<String, Object> application) {

		this.application = application;
	}

	

}
