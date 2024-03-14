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
							@RequestParam(name="onePageViewCnt", defaultValue = "10") int onePageViewCnt,//페이지당 보여줄 게시물 수
							@RequestParam(name="currentPageNumber", defaultValue = "1") int currentPageNumber ) //현재 페이지 번호
							 {
		
		//페이징 로직
		//입력된 searchKeyword , searchWord 을 hashmap으로 저장
		  Map<String, String> searchNewMap = new HashMap<String,String>();
		  //put - hashmap의 key에 value 을 할당한다
		  searchNewMap.put("searchKeyword", searchKeyword);
		  searchNewMap.put("searchWord", searchWord);
		  
		  // 전체 게시글의 수 : 입력된 값에 따라 게시판의 글의 갯수를 가지고온다.
		  int allBoardCnt = boardAdvanceService.getAllViewCnt(searchNewMap);
		  
		  //전체 페이지의 수 :  게시글의 갯수/하나의페이지에보여지는 게시글의 개수
		  int allPageCnt = allBoardCnt / onePageViewCnt;
		  // 나머지가 0이 아니면, 그다음페이지로 넘어간다.
		  if(allBoardCnt % onePageViewCnt != 0) {
			  allPageCnt ++;
		  }
		  
		  //시작페이지 계산하기
		  int startPage = (currentPageNumber-1) / 10 * 10 +1;
		  if(startPage == 0) startPage = 1;
		  
		  //끝 페이지 설정하기
		  int endPage = startPage + 9;
		  if(endPage > allPageCnt) endPage=allPageCnt;
		  if(endPage ==0) endPage = 1;
		  
		  //시작하는 게시물의 번호 계산하기
		  int startBoardIdx = (currentPageNumber-1) * onePageViewCnt + 1;
		  
		  //hashmap으로 객체화해준다. 게시물의 입력값들을 넣어서 게시물list 생성
		  // searchWord,searchKeyword, onePageViewCnt, 
		  Map<String, Object> searchMap = new HashMap<String, Object>();
		  searchMap.put("startBoardIdx", startBoardIdx); // 시작하는 게시물의 인덱스
		  searchMap.put("searchWord", searchWord);
		  searchMap.put("searchWord", searchWord);
		  searchMap.put("onePageViewCnt", onePageViewCnt);
		  model.addAttribute("boardList", boardAdvanceService.getBoardList(searchMap));
		  
		  
		  model.addAttribute("startPage", startPage);
		  model.addAttribute("endPage", endPage);
		  model.addAttribute("allBoardCnt", allBoardCnt);
		  model.addAttribute("allPageCnt", allPageCnt);
		  model.addAttribute("onePageViewCnt", onePageViewCnt);
		  model.addAttribute("currentPageNumber", currentPageNumber);
		  model.addAttribute("startBoardIdx", startBoardIdx);
		  model.addAttribute("searchKeyword", searchKeyword);
		  model.addAttribute("searchWord", searchWord);
		  
		  
		  
		return "boardAdvance/boardList";
	}
//	
//	@PostMapping("/boardList")
//	public String boardList() {
//		
//	}
	
	
	
	

}
