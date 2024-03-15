package com.application.practice2Ver1.boardAdvance.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.practice2Ver1.boardAdvance.dao.BoardAdvanceDAO;
import com.application.practice2Ver1.boardAdvance.dto.MainBoardDTO;

@Service
public class BoardAdvanceServiceImpl implements BoardAdvanceService {
	
	@Autowired
	private BoardAdvanceDAO boardAdvanceDAO;
	
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
		boardAdvanceDAO.createBoard(mainBoardDTO);
	}

	
	@Override
	@Transactional
	public MainBoardDTO getBoardDetail(long boardId, boolean isIncreaseReadCnt) {
		// 상세 게시글 가져오기 + 조회수 증가하기
		
//		if(isIncreaseReadCnt) {
//			boardAdvanceDAO.updateReadCnt();
//		}
		return null;
	}
	
	

}
