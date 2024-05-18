package com.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.domain.Reply;
import com.web.persistence.ReplyRepository;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyRepository replyRepo;
	

	// 댓글 목록
	@Override
	public void replyList(Model model, Long boardSeq) {
		// 댓글 객체에 저장된 게시판seq를 이용하여 조회 후 list에 담기
		List<Reply> replyList = replyRepo.findByBoardSeqOrderByReplySeqDesc(boardSeq);
		model.addAttribute("replyList",replyList);
	}
	
    @Override
    public List<Reply> getCommentsByBoardSeq(Long boardSeq) {
        return replyRepo.findByBoardSeqOrderByReplySeqDesc(boardSeq);
    }
 
	// 댓글 등록  
//    @Override
//    public void replyWrite(Reply reply, Long boardSeq, String replyWriter) {
//        // 작성자 정보 설정
//        reply.setReplyWriter(replyWriter);
//        System.out.println("저장이되는거니 아니되는거니########" + replyWriter);
//        // 댓글 저장
//        replyRepo.save(reply);
//    }
    
 // 댓글 등록
    @Override
    public void replyWrite(Long boardSeq, String replyContent, String replyWriter) {
    	Reply reply = new Reply();
    	reply.setBoardSeq(boardSeq);
    	reply.setReplyContent(replyContent);
    	reply.setReplyWriter(replyWriter);
    	System.out.println(replyWriter);
    	
    	replyRepo.save(reply);
    }
    
 // 댓글 수정 폼에 정보 띄우기
    @Override
    public void replyModify(Long replySeq, Model model) {
        // 댓글 번호로 조회하여 폼에 띄우기
        Reply reply = replyRepo.findById(replySeq).orElse(null);
        model.addAttribute("reply", reply); // "reply"라는 이름으로 reply 객체를 model에 추가
    }
	
 // 댓글 수정 완료시 객체정보를 넘겨서 수정
    @Override
    public void replyModify2(Reply reply) {
        // reply 객체로부터 수정할 내용 가져오기
        String reqplyContent = reply.getReplyContent();
        
        // replySeq에 맞게 댓글을 조회하여 수정하기
        Optional<Reply> optional = replyRepo.findById(reply.getReplySeq());
        if (optional.isPresent()) {
            Reply existingReply = optional.get();
            existingReply.setReplyContent(reqplyContent);
            replyRepo.save(existingReply); // 수정된 댓글 저장
        } else {
            System.out.println("수정 실패: 해당 댓글을 찾을 수 없습니다.");
        }
    }
	// 댓글 삭제
//	@Override
//	public void replyDelete(Long replySeq, RedirectAttributes ra) {
//		// 댓글 seq로 조회 후 댓글 객체 삭제
//		Optional<Reply> optional = replyRepo.findById(replySeq);
//		Reply reply = new Reply();
//		if(optional != null) {
//			reply = optional.get();
//		} else {
//			reply = null;
//		}
//		ra.addAttribute("boardSeq", reply.getBoardSeq());
//		replyRepo.deleteById(replySeq);
//	}
	
	// 댓글 삭제
	@Override
    public void replyDelete(Long replySeq, RedirectAttributes ra) {
        // 댓글 seq로 조회 후 댓글 객체 삭제
        Optional<Reply> optional = replyRepo.findById(replySeq);
        if (optional.isPresent()) {
            Reply reply = optional.get();
            ra.addAttribute("boardSeq", reply.getBoardSeq());
            replyRepo.deleteById(replySeq);
        }
    }

	
}


