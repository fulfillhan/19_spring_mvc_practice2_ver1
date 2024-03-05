package com.application.practice2Ver1.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.application.practice2Ver1.dto.MemberDTO;
import com.application.practice2Ver1.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("mainMember")
	public String mainMember() {
		return "member/mainMember";// 연결 ok
	}
	
	@GetMapping("registerMember")
	public String registerMember() {
		return "member/registerMember";
	}
	
	@PostMapping("registerMember")
	public String registerMember(@RequestParam("uploadProfile") MultipartFile uploadProfile, @ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		memberService.createMember(uploadProfile,memberDTO);
		return "redirect:mainMember"; // redirect:/member/mainMember 이렇게도 가능하다.
	}
	
	// AJAX의 validId 아이디 유효성 로직 작성하기
	//@PostMapping("/validId")
	
	@GetMapping("/loginMember")
	public String loginMember () {
		return "member/loginMember";
	}
	
	// AJAX로 아이디 확인하는 로직 작성하기
//	@PostMapping("/loginMember")
//	public String loginMember() {
//		
//	}	
}

