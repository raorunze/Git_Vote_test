package com.action;

import com.opensymphony.xwork2.ActionSupport;
import com.entity.*;
import com.service.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@Controller("voteAddAction")
@Scope("prototype")
public class VoteAddAction extends ActionSupport {
	private VoteSubject vote;
	private ArrayList<VoteOption> option;
	private ISubjectService service;
	private IOptionService optionService;

	public ISubjectService getService() {
		return service;
	}

	@Autowired
	public void setService(ISubjectService service) {
		this.service = service;
	}

	public IOptionService getOptionService() {
		return optionService;
	}

	@Autowired
	public void setOptionService(IOptionService optionService) {
		this.optionService = optionService;
	}

	public VoteSubject getVote() {
		return vote;
	}

	public void setVote(VoteSubject vote) {
		this.vote = vote;
	}

	public ArrayList<VoteOption> getOption() {
		return option;
	}

	public void setOption(ArrayList<VoteOption> option) {
		this.option = option;
	}

	@Override
	public String execute() throws Exception {
		Long subId = service.selectMaxSubId();
		Long opId = optionService.selectMaxOpId();
		vote.setVsId(subId);
		for (int i = 0; i < option.size(); i++) {
			option.get(i).setVoOrder(Long.valueOf(i+1));
			option.get(i).setVoteSubject(vote);
			option.get(i).setVoId(opId+i);
			System.out.println(option.get(i));
		}
		Set<VoteOption> op = new HashSet<VoteOption>(option);
		vote.setVoteOptions(op);
		service.insertVote(vote);
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		Long order = null;
		ArrayList<VoteOption> newOption = new ArrayList<VoteOption>();
		Long opId = optionService.selectMaxOpId();
		System.out.println(option.size());
		for (int i = 0; i < option.size()/2; i++) {
			order = option.get(i+(option.size()/2)).getVoOrder();
			option.get(i).setVoOrder(order);
			option.get(i).setVoId(opId+i);
			option.get(i).setVoteSubject(vote);
			newOption.add(option.get(i));
		}
		System.out.println(newOption.size());
		optionService.insertNewOp(newOption);
		return "edit";
	}
}
