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
}
