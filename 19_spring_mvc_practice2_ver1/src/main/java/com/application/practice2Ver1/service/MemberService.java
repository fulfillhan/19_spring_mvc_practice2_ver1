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



}
