package com.newlecture.webprj.dao;

import java.sql.SQLException;
import java.util.List;

import com.newlecture.webprj.vo.Notice;
public interface NoticeDao {

	public int getCount(String field, String query) throws ClassNotFoundException, SQLException;
	public List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException;
	public int delete(String seq) throws ClassNotFoundException, SQLException;
	public int update(Notice notice) throws ClassNotFoundException, SQLException;	
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException;	
	public int insert(final Notice n) throws ClassNotFoundException, SQLException ;
	public void hitUp(String seq);	
	public int getHit(String seq);
}
