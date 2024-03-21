package com.application.practice2Ver1.boardAdvance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.practice2Ver1.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver1.boardAdvance.dto.ReplyDTO;

@Mapper
public interface BoardAdvanceDAO {

	public int getAllViewCnt(Map<String, String> searchNewMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);

	public void createBoard(MainBoardDTO mainBoardDTO);

	public void updateReadCnt(long boardId);

	public MainBoardDTO getBoardDetail(long boardId);

	public int getAllReplyCnt(long boardId);

	public List<ReplyDTO> getReplyList(long boardId);

	public String getEncodedPasswd(long boardId);

	public void updateBoard(MainBoardDTO mainBoardDTO);

	public void deleteBoard(long boardId);

	public void createReply(ReplyDTO replyDTO);

	public ReplyDTO getReplyDetail(long replyId);

	public void updateReply(ReplyDTO replyDTO);

	public void deleteReply(long replyId);

	public void createDummy(List<MainBoardDTO> dummyBoardList);

	//public void createDummy(List<MainBoardDTO> dummyBoardList);

}
