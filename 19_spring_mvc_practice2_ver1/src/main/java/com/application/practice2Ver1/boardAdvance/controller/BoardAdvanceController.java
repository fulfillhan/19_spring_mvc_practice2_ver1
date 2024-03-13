package com.application.practice2Ver1.boardAdvance.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.practice2Ver1.boardAdvance.service.BoardAdvanceService;

@Controller
@RequestMapping("/boardAdvance")
public class BoardAdvanceController {
	
	@Autowired
	private BoardAdvanceService boardAdvanceService;
	
	@GetMapping("/boardList")
	public String boardList(Model model,
							@RequestParam(name="searchKeyword", defaultValue = "total") String searchKeyword,
							@RequestParam(name="searchWord", defaultValue = "") String searchWord,
							@RequestParam(name="onePageViewCnt", defaultValue = "10") int onePageViewCnt,
							@RequestParam(name="currentPageNumber", defaultValue = "1") int currentPageNumber )
							 {
		//입력된 searchKeyword , searchWord 을 hashmap으로 저장
		  Map<String, String> searchNewMap = new HashMap<String,String>();
		  //put - hashmap의 key에 value 을 할당한다
		  searchNewMap.put("searchKeyword", searchKeyword);
		  searchNewMap.put("searchWord", searchWord);
		  
		  // 입력된 값에 따라 게시판의 글의 갯수를 가지고온다.
		  int allViewCnt = boardAdvanceService.getAllViewCnt(searchNewMap);
		
		  
		  
		return "boardAdvance/boardList";
	}
//	
//	@PostMapping("/boardList")
//	public String boardList() {
//		
//	}
	
	
	
	

}
