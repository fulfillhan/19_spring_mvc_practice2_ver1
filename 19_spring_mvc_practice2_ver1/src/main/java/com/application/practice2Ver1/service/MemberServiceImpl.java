package com.application.practice2Ver1.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.practice2Ver1.dao.MemberDAO;
import com.application.practice2Ver1.dto.MemberDTO;



@Service
public class MemberServiceImpl implements MemberService {
	
	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Override
	public void createMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException {
		
		if(!uploadProfile.isEmpty()) {
			//원본 파일이름
			String originalFilename = uploadProfile.getOriginalFilename();
			memberDTO.setProfileOriginalName(originalFilename);// ProfileOriginalName 해당데이터에 파일이름을 매개변수로 넣고 memberDTO에 보낸다.
			//UUID생성
			UUID uuid = UUID.randomUUID();
			//확장자 추출
			String extenstion = originalFilename.substring(originalFilename.indexOf("."));
			//업로드 파일 이름 수정하여 ProfileUUID로 dto전송한다.
			String uploadNewFile = uuid + extenstion;
			memberDTO.setProfileUUID(uploadNewFile);
			//파일 업로드 구현
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadNewFile));//예외처리
		}
		//smsstsYn,emailstsYn 없다면 'n'이다로 지정해주기
		if(memberDTO.getSmsstsYn() == null) memberDTO.setSmsstsYn("n");
		if(memberDTO.getEmailstsYn() == null) memberDTO.setEmailstsYn("n");
		
		//패스워드 암호화
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		
		//memberDAO로 전달할 메서드 만들기
		memberDAO.createMember(memberDTO);
		
	}

	@Override
	public String checkValidId(String memberId) {
		// 기본값
		String isVlidId = "y";// 유효한 경우
		if(memberDAO.checkValidId(memberId) != null) {// 데이터에 있는 아이디가 빈값이 아니라면 즉 아이디가 하나라도 있는경우
			isVlidId = "n";
		}
		
		return isVlidId;
	}

	@Override
	public boolean login(MemberDTO memberDTO) {
		
		// 아이디를 매개변수로 넣어서 암호화된 비번과 활동상태여부를 가지고온다. BoardDTO로 가져오기
		 MemberDTO validateData= memberDAO.login(memberDTO.getMemberId());
		
		// 데이터베이스에서 가지고온 패스워드의 암호화를 비교한다.(null 이아니여야 한다)
		 if(validateData != null) {
			 // 비교가 참/거짓인지 확인후 반환한다.
			 if(passwordEncoder.matches(memberDTO.getPasswd(), validateData.getPasswd()) && validateData.getActiveYn().equals("y")) {
				 return true;
			 }
		 }
		 
		return false;
	}
	

}
