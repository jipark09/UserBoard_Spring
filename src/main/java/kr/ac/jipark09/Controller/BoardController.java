package kr.ac.jipark09.Controller;

import kr.ac.jipark09.domain.*;
import kr.ac.jipark09.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.*;
import java.util.*;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/commentsTest")
    public String test() {
        return "comments";
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model model) {
        try {
            System.out.println("bno=" + bno);
            BoardDto boardDto = boardService.read(bno);
            model.addAttribute(boardDto); // 앞글자가 소문자인 애로 key로 담아줌
            model.addAttribute("page", page);
            model.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board";
    }

    // 먼저 기본적으로 로그인 체크를 해준다. 누가 게시물을 썼는지 확인을 하기 위해
    @GetMapping("/list")
    public String list(SearchCondition sc, Model model, HttpServletRequest request) {
        if(!loginCheck(request)) {
            return "redirect:/login/login?toURL=" + request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동
        }
        try {
            // pageHandler 활용
            int totalCnt = boardService.getSearchResultCnt(sc);
            model.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchSelectPage(sc);
            model.addAttribute("list", list); // jsp로 보냄
            model.addAttribute("ph", pageHandler); // jsp에서 pageHandler를 가지고 페이지를 나타냄
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model model, HttpSession session, RedirectAttributes rettr) {
        String writer = (String) session.getAttribute("id");
        try {
            model.addAttribute("page", page);
            model.addAttribute("pageSize", pageSize);
            //redirect:/board/list?page=..&pageSize=.. 와 마찬가지

            int rowcount = boardService.remove(bno, writer);
            // 잘 삭제 되었으면 delect ok 메세지 보냄
            if(rowcount == 1) {
                rettr.addFlashAttribute("msg", "DEL_OK");
                return "redirect:/board/list";

            } else {
                rettr.addFlashAttribute("msg", "NO_MATCH");
                return "redirect:/board/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rettr.addFlashAttribute("msg", "DEL_ERR");
        }

        return "redirect:/index";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("mode", "new");
        return "board"; //  읽기와 쓰기에 사용. 쓰기에 사용할 때 mode=new

    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, Model model, HttpSession session, RedirectAttributes rttr) {
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int result = boardService.write(boardDto);
            if(result != 1) {
                throw new Exception("Write failed");
            }
            rttr.addFlashAttribute("msg", "WRT_OK");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(boardDto);
            rttr.addFlashAttribute("msg", "WRT_ERR");
            return "board";
        }
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Model model, Integer page, Integer pageSize, HttpSession session, RedirectAttributes rttr) {
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            rttr.addAttribute("page", page);
            rttr.addAttribute("pageSize", pageSize);
            int result = boardService.modify(boardDto);
            if(result != 1) {
                throw new Exception("Modify failed");
            }
            rttr.addFlashAttribute("msg", "MOD_OK");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(boardDto);
            rttr.addFlashAttribute("msg", "MOD_ERR");
            return "board";
        }
//        return "redirect:/board/list?page=" + page + "&pageSize=" + pageSize;
        return "redirect:/board/list";
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id") != null;
    }
}