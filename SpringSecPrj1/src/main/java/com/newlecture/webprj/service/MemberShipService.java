package com.newlecture.webprj.service;

import java.sql.SQLException;

import com.newlecture.webprj.vo.Notice;

public interface MemberShipService { 
	public void insertAndPointUpOfMember(Notice n, String uid) throws ClassNotFoundException, SQLException;
}
