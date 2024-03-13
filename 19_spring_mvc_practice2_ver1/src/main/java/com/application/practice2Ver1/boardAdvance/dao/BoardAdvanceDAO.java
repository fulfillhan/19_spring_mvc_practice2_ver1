package com.application.practice2Ver1.boardAdvance.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardAdvanceDAO {

	public int getAllViewCnt(Map<String, String> searchNewMap);

}
