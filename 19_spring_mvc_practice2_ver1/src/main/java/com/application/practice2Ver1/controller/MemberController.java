package com.application.practice2Ver1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String registerMember(@RequestParam("uploadProfile") String uploadProfile, @ModelAttribute MemberDTO memberDTO) {
		//memberService.createMember(uploadProfile,memberDTO);
		return "redirect:mainMember"; // redirect:member/mainMember
		
	}
	
	

}
