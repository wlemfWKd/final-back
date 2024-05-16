package com.web.service;



import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.domain.Reply;


public interface ReplyService {
	
	//댓글 목록
	public void replyList(Model model, Long boardSeq);
	
	// 댓글 쓰기 
	//public void replyWrite(Reply reply, Long boardSeq, String replyWriter);
	
	public void replyWrite(Long boardSeq, String replyContent, String replyWriter);
	
	// 댓글 수정 폼에 값 넣기 
	public void replyModify(Long replySeq, Model model);
	
	// 댓글 삭제 
	public void replyDelete(Long replySeq, RedirectAttributes ra);
	
	// 댓글 수정 
	public void replyModify2(Reply reply);

	public List<Reply> getCommentsByBoardSeq(Long boardSeq);

}
