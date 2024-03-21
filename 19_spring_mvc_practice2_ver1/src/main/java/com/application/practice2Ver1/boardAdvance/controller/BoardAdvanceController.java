package com.application.practice2Ver1.boardAdvance.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.practice2Ver1.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver1.boardAdvance.dto.ReplyDTO;
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
		  searchMap.put("searchKeyword", searchKeyword);
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
		  
		  
		  
		return "boardAdvance/board/boardList";
	}
	
	@GetMapping("/createBoard")
	public String createBoard() {
		return "boardAdvance/board/createBoard";
	}
	
	@PostMapping("/createBoard")
	public String createBoard(@ModelAttribute MainBoardDTO mainBoardDTO) {
		boardAdvanceService.createBoard(mainBoardDTO);
		return "redirect:/boardAdvance/boardList";
	}

	
	  @GetMapping("/boardDetail") 
	  public String boardDetail(Model model, @RequestParam("boardId") long boardId) { 
	 // boardAdvanceService.getBoardDetail(boardId); 
		  
	  model.addAttribute("mainBoardDTO", boardAdvanceService.getBoardDetail(boardId, true));  // 게시글상세 가져오기
	  // 상세게시글 안에 전체 리뷰수 가져오기, 리뷰 목록 가져오기
	  model.addAttribute("allReplyCnt", boardAdvanceService.allReplyCnt(boardId));
	  model.addAttribute("replyList", boardAdvanceService.getReplyList(boardId));
	  
	  return "boardAdvance/board/boardDetail";
	  }
	
	  @GetMapping("/boardAuthentication")
	  public String boardAuthentication(Model model, @RequestParam("boardId") long boardId, @RequestParam("menu") String menu) {
		  //boardAdvanceService.getBoardDetail(boardId, false);
		  model.addAttribute("mainBoardDTO",boardAdvanceService.getBoardDetail(boardId, false));
		  model.addAttribute("menu", menu);
		  return "boardAdvance/board/authentication";
	  }
	  
	  @PostMapping("/boardAuthentication")
	  @ResponseBody
	  public String boardAuthentication(@ModelAttribute MainBoardDTO mainBoardDTO, @RequestParam("menu") String menu) {
		  //boardAdvanceService.isAuthorized(mainBoardDTO);
		  
		  String jString = "";
		  if(boardAdvanceService.isAuthorized(mainBoardDTO)) {
			  if(menu.equals("update")) {
				  jString = "<script>";
				  jString += "alert('인증되었습니다.');";
				  jString += "location.href='/boardAdvance/board/updateBoard?boardId="+mainBoardDTO.getBoardId()+"';";
				  jString += "</script>";
			  }else if (menu.equals("delete")) {
				  jString = "<script>";
				  jString += "alert('인증되었습니다.');";
				  jString += "location.href='/boardAdvance/board/deleteBoard?boardId="+mainBoardDTO.getBoardId()+"';";
				  jString += "</script>";
			}
		  }
		  else {
			jString="""
					<script> 
				   alert('아이디와 패스워드를 확인하세요');
				   history.go(-1);
					 </script>
					""";
		}
		  return jString;
	  }
	  
	  @GetMapping("/updateBoard")
	  public String updateBoard(Model model, @RequestParam("boardId") long boardId) {
		  model.addAttribute("mainBoardDTO", boardAdvanceService.getBoardDetail(boardId, false));
		  return "boardAdvance/board/updateBoard";
	  }
	  
	  @PostMapping("/updateBoard")
	  public String updateBoard(@ModelAttribute MainBoardDTO mainBoardDTO) {
		  boardAdvanceService.updateBoard(mainBoardDTO);
		  return "boardAdvance/board/boardList";
	  }
	  
	  @GetMapping("/deleteBoard")
	  public String deleteBoard(@RequestParam("boardId") long boardId, Model model) {
		  model.addAttribute("boardId", boardId);
		  return "boardAdvance/board/deleteBoard";
	  }
	  
	  @PostMapping("/deleteBoard")
	  public String deleteBoard(@RequestParam("boardId")long boardId) {
		  boardAdvanceService.deleteBoard(boardId);
		  return "boardAdvance/board/deleteBoard";
	  }
	  
	  @GetMapping("/createBoardDummy")
	  public String createBoardDummy() {
		  boardAdvanceService.createBoardDummy();
		  return "redirect:/boardAdvance/boardList";
	  }
	  
	  
	  @GetMapping("/createReply")
	  public String createReply(@RequestParam("boardId")long boardId, Model model) {
		model.addAttribute("boardId", boardId);
		return "boardAdvance/reply/createReply";
		  
	  }
	  
	  @PostMapping("/createReply")
	  public String createReply(@ModelAttribute ReplyDTO replyDTO) {
		  boardAdvanceService.createReply(replyDTO);
		  return "redirect:/boardAdvance/boardDetail?boardId="+replyDTO.getBoardId();
	  }
	  
	  @GetMapping("/udpateReply")
	  public String udpateReply(@RequestParam("replyId") long replyId, Model model) {
		  model.addAttribute("replyDTO", boardAdvanceService.getReplyDetail(replyId));
		  return "boardAdvance/reply/updateReply";
	  }
	  
	  // name="passwd name="content" name="write name="boardId" name="replyId
	  @PostMapping("/udpateReply")
	  @ResponseBody
	  public String udpateReply(ReplyDTO replyDTO) {
		  //boardAdvanceService.udpateReply(replyDTO);
		  
		  String jString ="";
		  if(boardAdvanceService.udpateReply(replyDTO)) {
			  jString += "<script>";
			  jString += "location.href='/boardAdavance/boardDetail?boardId='"+replyDTO.getBoardId()+";";
			  jString += "</script>";
		  }
		  jString="""
		  		 <script> 
			   alert('아이디와 비번을 확인해주세요');
			   history.go(-1);
		   </script>
		  		""";
		  
		  return jString;
	  }
	  
	  @GetMapping("/deleteReply")
	  public String deleteReply(@RequestParam("replyId") long replyId, Model model) {
		  model.addAttribute("replyDTO", boardAdvanceService.getReplyDetail(replyId));
		  return "boardAdvance/reply/deleteReply";
	  }
	  
	  @PostMapping("/deleteReply")
	  @ResponseBody
	  public String deleteReply(@ModelAttribute ReplyDTO replyDTO) {
		 boolean deleteReply =  boardAdvanceService.deleteReply(replyDTO);
		 
		 String jString = "";
		if(deleteReply) {
			jString += "<script>";
			jString += "location.href='/boardAdvance/boardDetail?boardId=" + replyDTO.getBoardId() + "';";
			jString += "</script>";
		}
		else {
			jString = """
					   <script> 
						   alert('아이디와 패스워드를 확인하세요!');
						   history.go(-1);
					   </script>""";
		}
		 return jString;
	  }
	  
	  
	  

}
