package com.action;

import com.entity.VoteSubject;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ISubjectService;

import java.util.*;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@Controller("voteListAction")
@Scope("prototype")
public class VoteListAction extends ActionSupport implements RequestAware {
	
	private ISubjectService service;
	private Map<String, Object> request;

	public ISubjectService getService() {
		return service;
	}

	@Autowired
	public void setService(ISubjectService service) {
		this.service = service;
	}

	@Override
	public String execute() throws Exception {
		List<VoteSubject> list = service.selectSubjects();
		System.out.println(list.size());
		request.put("voteList",list);
		return INPUT;
	}
	
	public String fix() throws Exception{
		List<VoteSubject> list = service.selectSubjects();
		System.out.println(list.size());
		request.put("voteList",list);
		return "fix";
	}

	//框架依赖注入
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
}
