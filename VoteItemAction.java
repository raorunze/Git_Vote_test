package com.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.entity.*;
import com.opensymphony.xwork2.ActionSupport;
import com.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@Controller("voteItemAction")
@Scope("prototype")
public class VoteItemAction extends ActionSupport {

	private Long optionRadio;
	private String optionCheck;
	private Long userId;
	private Long vsId;
	private IItemService service;
	VoteOption option = new VoteOption();
	VoteSubject subject = new VoteSubject();
	VoteItem item = new VoteItem();
	VoteUser user = new VoteUser();
	
	public Long getVsId() {
		return vsId;
	}

	public void setVsId(Long vsId) {
		this.vsId = vsId;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getOptionRadio() {
		return optionRadio;
	}
	
	public void setOptionRadio(Long optionRadio) {
		this.optionRadio = optionRadio;
	}
	
	public String getOptionCheck() {
		return optionCheck;
	}
	
	public void setOptionCheck(String optionCheck) {
		this.optionCheck = optionCheck;
	}
	
	public IItemService getService() {
		return service;
	}
	
	@Autowired
	public void setService(IItemService service) {
		this.service = service;
	}
	
	@Override
	public String execute() throws Exception {
		Long viId = service.selectMaxItId();
		subject.setVsId(vsId);
		user.setVuUserId(userId);
		JSONArray options = JSON.parseArray(optionCheck);
		for (int i = 0; i < options.size(); i++) {
			Long voId = Long.valueOf(options.getString(i));
			option.setVoId(voId);
			item.setViId(viId+i);
			item.setVoteOption(option);
			item.setVoteSubject(subject);
			item.setVoteUser(user);
			service.insertItem(item);
		}
		return "mult";
	}
	
	public String simp() throws Exception {
		Long viId = service.selectMaxItId();
		subject.setVsId(vsId);
		user.setVuUserId(userId);
		option.setVoId(optionRadio);
		item.setViId(viId);
		item.setVoteOption(option);
		item.setVoteSubject(subject);
		item.setVoteUser(user);
		System.out.println(item.getViId());
		service.insertItem(item);
		return "simp";
	}

	
}
