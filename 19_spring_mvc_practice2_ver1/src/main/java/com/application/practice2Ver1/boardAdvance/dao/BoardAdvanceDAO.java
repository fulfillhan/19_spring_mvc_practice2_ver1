package com.application.practice2Ver1.boardAdvance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.practice2Ver1.boardAdvance.dto.MainBoardDTO;

@Mapper
public interface BoardAdvanceDAO {

	public int getAllViewCnt(Map<String, String> searchNewMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);

}
