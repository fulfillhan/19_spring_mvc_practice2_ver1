package com.application.practice2Ver1.boardAdvance.service;

import java.util.List;
import java.util.Map;

import com.application.practice2Ver1.boardAdvance.dto.MainBoardDTO;

public interface BoardAdvanceService {

	 public int getAllViewCnt(Map<String, String> searchNewMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);

}
