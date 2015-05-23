package com.newlecture.webprj.dao;

import java.sql.SQLException;

import com.newlecture.webprj.vo.Member;

public interface MemberDao {
	public Member getMember(String uid) throws ClassNotFoundException, SQLException;	
	public int insert(Member member) throws ClassNotFoundException, SQLException;
}
