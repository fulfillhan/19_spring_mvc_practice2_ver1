package com.application.practice2Ver1.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.practice2Ver1.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	public void createMember(MemberDTO memberDTO);

	public String checkValidId(String memberId);

	public MemberDTO login(String memberId);

	public MemberDTO getMemberDetail(String memberId);

	public void updateMember(MemberDTO memberDTO);

	public void updateInactiveMember(String memberId);// 비활성화 + 시간 수정하기

	public int getTodayNewMemberCnt(String today);
}
