package com.action;

import com.entity.VoteUser;
import com.opensymphony.xwork2.ActionSupport;
import com.service.IUserService;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@Controller("registerAction")
@Scope("prototype")
public class RegisterAction extends ActionSupport implements ServletRequestAware  {
	private String name;
	private String password;
	private IUserService service;
	private HttpServletRequest request;
	private String re_password;
	
	public String getRe_password() {
		return re_password;
	}

	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}
	
	@Override
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String execute() throws Exception {
		VoteUser user = new VoteUser();
		user.setVuUserName(name);
		user.setVuPassword(password);
		System.out.println("自动id");
		Long id = service.selectMaxUserId();
		Long vuUserId = id + 1;
		user.setVuUserId(vuUserId);
		user.setVuStatus(Long.valueOf(2));
		System.out.println(user.toString());
		service.insertUser(user);
		request.setAttribute("regInfo", "注册成功,进入登录页面");
		return LOGIN;
	}

	public void validate() {
		if (name == null || name.trim().equals("")) {
			addFieldError("vuUserName2", "用户名不能为空!!!");
		}else if (password == null || password.trim().equals("")) {
			addFieldError("vuPassword2", "密码不能为空!!!");
		}else if (!password.equals(re_password)) {
			addFieldError("rePassword2", "重复密码应当相同!!!");
		}

		System.out.println("-------------------");

	}
}
