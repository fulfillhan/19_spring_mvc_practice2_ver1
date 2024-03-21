package com.application.practice2Ver1.boardAdvance.service;

import java.util.List;
import java.util.Map;

import com.application.practice2Ver1.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver1.boardAdvance.dto.ReplyDTO;

public interface BoardAdvanceService {
	

	public int getAllViewCnt(Map<String, String> searchNewMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);

	public void createBoard(MainBoardDTO mainBoardDTO);

	public MainBoardDTO getBoardDetail(long boardId, boolean isIncreaseReadCnt);

	public boolean isAuthorized(MainBoardDTO mainBoardDTO);

	public void updateBoard(MainBoardDTO mainBoardDTO);

	public void deleteBoard(long boardId);

	public void createBoardDummy();
	

	public int allReplyCnt(long boardId);
	
	public List<ReplyDTO> getReplyList(long boardId);
	
	public void createReply(ReplyDTO replyDTO);

	public ReplyDTO getReplyDetail(long replyId);

	public boolean udpateReply(ReplyDTO replyDTO);

	public boolean deleteReply(ReplyDTO replyDTO);
}
