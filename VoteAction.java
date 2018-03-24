package com.action;

import com.entity.VoteOption;
import com.entity.VoteSubject;
import com.opensymphony.xwork2.ActionSupport;
import com.service.*;
import java.util.*;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@Controller("voteAction")
@Scope("prototype")
public class VoteAction extends ActionSupport implements RequestAware {
	private ISubjectService service;
	private Map<String, Object> request;
	private Long vsId;
	String message = null;
	
	public Long getVsId() {
		return vsId;
	}

	public void setVsId(Long vsId) {
		this.vsId = vsId;
	}

	public ISubjectService getService() {
		return service;
	}

	@Autowired
	public void setService(ISubjectService service) {
		this.service = service;
	}
	
	//总方法
	@Override
	public String execute() throws Exception {
		VoteSubject vote = service.selectSubject(vsId);		
		Set<VoteOption> set = vote.getVoteOptions();
		TreeSet<VoteOption> options = new TreeSet<VoteOption>(set);
		request.put("options",options);
		request.put("vote", vote);
		
		//判断单选多选
		if (vote.getVsType()==1) {
			message = "simp";
		} else if(vote.getVsType()==2) {
			message = "mult";
		}
		
		return message;
		
	}
	
	public String fix() throws Exception {
		System.out.println("维护中------");
		VoteSubject vote = service.selectSubject(vsId);		
		Set<VoteOption> set = vote.getVoteOptions();
		TreeSet<VoteOption> options = new TreeSet<VoteOption>(set);
		request.put("options",options);
		request.put("vote", vote);
		return "fix";
	}

	//框架依赖注入
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
}
