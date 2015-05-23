package com.newlecture.webprj.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newlecture.webprj.dao.NoticeDao;
import com.newlecture.webprj.service.MemberShipService;
import com.newlecture.webprj.vo.Notice;

@Controller
@RequestMapping("/customer/*")
public class CustomerController {	
	private NoticeDao noticeDao;	
	private MemberShipService membership;
	
	@Autowired
	public void setMembership(MemberShipService membership) {
		this.membership = membership;
	}

	@Autowired
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@RequestMapping("notice.htm")
	public String notices(String pg, String f, String q, Model model) throws ClassNotFoundException, SQLException
	{
		int page = 1;
		String field = "TITLE";
		String query = "%%";
		
		if(pg != null && !pg.equals(""))
			page = Integer.parseInt(pg);
		
		if(f != null && !f.equals(""))
			field = f;
		
		if(q != null && !q.equals(""))
			query = q;	
				
		//NoticeDao dao = new NoticeDao();
		List<Notice> list = noticeDao.getNotices(page, field, query);
		model.addAttribute("count", noticeDao.getCount(field, query));
		
		//ModelAndView mv = new ModelAndView("notice.jsp");
		model.addAttribute("list", list);	
				
		return "customer.notice";
	}
		
	@RequestMapping("noticeDetail.htm")
	public String noticeDetail(final String seq, Model model) throws ClassNotFoundException, SQLException
	{
		//String seq = request.getParameter("seq");
		
		Notice notice = noticeDao.getNotice(seq);
		/*

		new Thread(new Runnable() {
			
			@Override
			public void run() {			
				
				noticeDao.getHit(seq);
			}
		}, "getHit Thread").start();

		new Thread(new Runnable() {
			
			@Override
			public void run() {					
				noticeDao.hitUp(seq);		
			}
		}, "hitUp Thread").start();*/
		
		//ModelAndView mv = new ModelAndView("noticeDetail.jsp");
		model.addAttribute("notice", notice);
		
		return "customer.noticeDetail";
	}
	
	@RequestMapping(value={"noticeReg.htm"}, method=RequestMethod.GET)
	public String noticeReg()
	{
		return "customer.noticeReg";
	}
	
	@RequestMapping(value={"noticeReg.htm"}, method=RequestMethod.POST)
	public String noticeReg(Notice n, HttpServletRequest request/*String title, String content*/) throws ClassNotFoundException, SQLException, IOException
	{
		if(!n.getFile().isEmpty())
		{
			String fname = n.getFile().getOriginalFilename();
			String path = request.getServletContext().getRealPath("/customer/upload");
			String fpath = path + "\\" + fname;
			
			FileOutputStream fs = new FileOutputStream(fpath);
			fs.write(n.getFile().getBytes());
			fs.close();
			
			n.setFileSrc(fname);
		}			
		
		membership.insertAndPointUpOfMember(n, "newlec");
		
		return "redirect:notice.htm";
	}
	
	@RequestMapping(value={"noticeEdit.htm"}, method=RequestMethod.GET)
	public String noticeEdit(String seq, Model model) throws ClassNotFoundException, SQLException
	{
		Notice notice = noticeDao.getNotice(seq);
		model.addAttribute("notice", notice);
		
		return "customer.noticeEdit";
	}
	
	@RequestMapping(value={"noticeEdit.htm"}, method=RequestMethod.POST)
	public String noticeEdit(Notice n/*String title, String content*/) throws ClassNotFoundException, SQLException
	{
		/*Notice n = new Notice();
		n.setTitle(title);
		n.setContent(content);*/
		
		noticeDao.update(n);
		
		return "redirect:noticeDetail.htm?seq="+n.getSeq();
	}
	
	@RequestMapping("noticeDel.htm")
	public String noticeDel(String seq) throws ClassNotFoundException, SQLException
	{
		noticeDao.delete(seq);

		return "redirect:notice.htm";
	}
	
	@RequestMapping("download.htm")
	public void download(String p, String f, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		System.out.println(f);
		String fname = new String(f.getBytes("ISO8859_1"), "UTF-8");
		System.out.println(fname);
		response.setHeader("Content-Disposition", 
				"attachment;filename="+new String(fname.getBytes(), "ISO8859_1"));
		
		String fullPath = request.getServletContext().getRealPath(p + "/" + fname);
		
		FileInputStream fin = new FileInputStream(fullPath);
		ServletOutputStream sout = response.getOutputStream();
		
		byte[] buf = new byte[1024];
		int size = 0;
		
		while((size = fin.read(buf, 0, 1024)) != -1)
			sout.write(buf, 0, size);
		
		fin.close();
		sout.close();
	}
}
