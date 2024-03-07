package com.application.practice2Ver1.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.application.practice2Ver1.dto.MemberDTO;



public interface MemberService {

	public void createMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException;

	public String checkValidId(String memberId);

	public boolean login(MemberDTO memberDTO);

	public MemberDTO getMemberDetail(String memberId);

	public void updateMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException;

	public void updateInactiveMember(String memberId);
	
	//오늘의 가입한회원숫자 스케쥴링으로 업데이트
	public void getTodayNewMemberCnt();
	//스케쥴링 삭제하기
	public void deleteMemberScheduler();



}
