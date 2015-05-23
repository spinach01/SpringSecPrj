package com.newlecture.webprj.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newlecture.webprj.dao.MemberDao;
import com.newlecture.webprj.vo.Member;

@Controller
@RequestMapping("/joinus/*")
public class JoinusController {
	
	private MemberDao memberDao;
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@RequestMapping(value={"join.htm"}, method=RequestMethod.GET)
	public String join()
	{
		return "joinus.join";
	}
	
	@RequestMapping(value={"join.htm"}, method=RequestMethod.POST)
	public String join(Member member) throws ClassNotFoundException, SQLException, IOException
	{
		memberDao.insert(member);
		
		return "redirect:../index.htm";
	}
	
	@RequestMapping(value={"login.htm"}, method=RequestMethod.GET)
	public String login()
	{
		return "joinus.login";
	}
	
}
