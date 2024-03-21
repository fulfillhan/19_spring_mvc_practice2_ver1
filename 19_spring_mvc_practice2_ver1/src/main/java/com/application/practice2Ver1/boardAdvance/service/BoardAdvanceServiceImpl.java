package com.application.practice2Ver1.boardAdvance.service;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.practice2Ver1.boardAdvance.dao.BoardAdvanceDAO;
import com.application.practice2Ver1.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver1.boardAdvance.dto.ReplyDTO;

@Service
public class BoardAdvanceServiceImpl implements BoardAdvanceService {
	
	@Autowired
	private BoardAdvanceDAO boardAdvanceDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(BoardAdvanceServiceImpl.class);

	@Override
	public int getAllViewCnt(Map<String, String> searchNewMap) {
		
		return boardAdvanceDAO.getAllViewCnt(searchNewMap);
	}

	@Override
	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap) {
		
		return boardAdvanceDAO.getBoardList(searchMap);
	}

	@Override
	public void createBoard(MainBoardDTO mainBoardDTO) {
		String encodingPasswd = passwordEncoder.encode(mainBoardDTO.getPasswd());
		mainBoardDTO.setPasswd(encodingPasswd);
		boardAdvanceDAO.createBoard(mainBoardDTO);
	}

	
	@Override
	//@Transactional
	public MainBoardDTO getBoardDetail(long boardId, boolean isIncreaseReadCnt) {
		// 상세 게시글 가져오기 + 조회수 증가하기
		
		if(isIncreaseReadCnt) {
			boardAdvanceDAO.updateReadCnt(boardId);
		}
		
		return boardAdvanceDAO.getBoardDetail(boardId);
	}

	@Override
	public int allReplyCnt(long boardId) {
		
		return boardAdvanceDAO.getAllReplyCnt(boardId);
	}

	@Override
	public List<ReplyDTO> getReplyList(long boardId) {
		
		return boardAdvanceDAO.getReplyList(boardId);
	}

	@Override
	public boolean isAuthorized(MainBoardDTO mainBoardDTO) {
		boolean checkAuthorized = false;
		//패스워드 암호화 풀기
		String encodedPasswd = boardAdvanceDAO.getEncodedPasswd(mainBoardDTO.getBoardId());
		
		if(passwordEncoder.matches(mainBoardDTO.getPasswd(),encodedPasswd)) {
			checkAuthorized = true;
		}
		return checkAuthorized;
	}

	@Override
	public void updateBoard(MainBoardDTO mainBoardDTO) {
		
		boardAdvanceDAO.updateBoard(mainBoardDTO);
	}

	@Override
	public void deleteBoard(long boardId) {
		boardAdvanceDAO.deleteBoard(boardId);
	}

	@Override
	public void createBoardDummy() {
		Random ran = new Random();
		
		 List<MainBoardDTO> dummyBoardList = new ArrayList<MainBoardDTO>();
		 
		 String[] word = {"가","나","다","라","마","바","사","아","자","차","카","타","파","하"};
		 for (int i = 1001; i < 1301; i++) {
			
			 String writer="";
			 String subject="";
			 String content="";
			 for (int j = 0; j < 7; j++) {
				 //ran.nextInt(word.length)
				 writer += word[ran.nextInt(word.length)];
				 subject += word[ran.nextInt(word.length)];
				 content += word[ran.nextInt(word.length)];
			 }
			 MainBoardDTO temp = new MainBoardDTO();
			 temp.setBoardId(i);
			 temp.setSubject(subject);
			 temp.setWriter(writer);
			 temp.setContent(content);
			 temp.setPasswd(passwordEncoder.encode("1111"));
			 
			 dummyBoardList.add(temp);
		}
		 boardAdvanceDAO.createDummy(dummyBoardList);
		  
		
	}

	@Override
	public void createReply(ReplyDTO replyDTO) {
		replyDTO.setPasswd(passwordEncoder.encode(replyDTO.getPasswd()));
		boardAdvanceDAO.createReply(replyDTO);
		
	}

	@Override
	public ReplyDTO getReplyDetail(long replyId) {
		
		return boardAdvanceDAO.getReplyDetail(replyId);
	}

	@Override
	public boolean udpateReply(ReplyDTO replyDTO) {
		boolean isCheck = false;
		String encodedPasswd = boardAdvanceDAO.getEncodedPasswd(replyDTO.getReplyId());
		
		if(passwordEncoder.matches(replyDTO.getPasswd(), encodedPasswd)) {
			boardAdvanceDAO.updateReply(replyDTO);
			isCheck = true;
		}
		return isCheck;
	}

	@Override
	public boolean deleteReply(ReplyDTO replyDTO) {
		boolean isCheck = false;
		
		if(passwordEncoder.matches(replyDTO.getPasswd(), boardAdvanceDAO.getEncodedPasswd(replyDTO.getReplyId()))) {
			boardAdvanceDAO.deleteReply(replyDTO.getReplyId());
			isCheck = true;
		}
		return isCheck;
	}
	
	

}
