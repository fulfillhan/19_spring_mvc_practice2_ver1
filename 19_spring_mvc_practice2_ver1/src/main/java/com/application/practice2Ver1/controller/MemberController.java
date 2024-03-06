package com.application.practice2Ver1.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.practice2Ver1.dto.MemberDTO;
import com.application.practice2Ver1.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	public String registerMember(@RequestParam("uploadProfile") MultipartFile uploadProfile,
			@ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		memberService.createMember(uploadProfile, memberDTO);
		return "redirect:mainMember"; // redirect:/member/mainMember 이렇게도 가능하다.
	}

	// AJAX의 validId 아이디 유효성 로직 작성하기
	@PostMapping("/validId")
	@ResponseBody // AJAX 에서 @GET일때가 아닌 @POST일때도 사용되는 이유는?
	public String validId(@RequestParam("memberId") String memberId) {// 단일 데이터 받기
		return memberService.checkValidId(memberId);

	}

	@GetMapping("/loginMember")
	public String loginMember() {
		return "member/loginMember";
	}

	
	  // AJAX로 아이디 확인하는 로직 작성하기
	  
	  @PostMapping("/loginMember") 
	  @ResponseBody
	  public String loginMember(@RequestBody MemberDTO memberDTO, HttpServletRequest request ) { 
	  
	  String isValidMember = "n";
	  if(memberService.login(memberDTO)) {// 로그인 여부 false,true로 반환 -> 로그인 true 라면
		  
		  // 세션화 객체 호출후 memberId로 저장하기
		  HttpSession session = request.getSession();
		  session.setAttribute("memberId", memberDTO.getMemberId());
		  
		  isValidMember = "y";
		  
	  }
	  return isValidMember;
  }
	  
	  @GetMapping("/logoutMember")
	  public String logoutMember(HttpServletRequest request) {
		  // 세션 객체 호출하여 전체 속성 제거
		  HttpSession session = request.getSession();// session호출하기
		  session.invalidate();
		  
		  return "redirect:/member/mainMember";
	  }
	 
}
