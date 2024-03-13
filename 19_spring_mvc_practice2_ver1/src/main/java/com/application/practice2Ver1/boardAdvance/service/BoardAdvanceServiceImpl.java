package com.application.practice2Ver1.boardAdvance.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.practice2Ver1.boardAdvance.dao.BoardAdvanceDAO;

@Service
public class BoardAdvanceServiceImpl implements BoardAdvanceService {
	
	@Autowired
	private BoardAdvanceDAO boardAdvanceDAO;
	
	private static Logger logger = LoggerFactory.getLogger(BoardAdvanceServiceImpl.class);

	@Override
	public int getAllViewCnt(Map<String, String> searchNewMap) {
		
		return boardAdvanceDAO.getAllViewCnt(searchNewMap);
	}
	
	

}
