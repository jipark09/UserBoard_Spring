package kr.ac.jipark09.Controller;

import kr.ac.jipark09.domain.CommentDto;
import kr.ac.jipark09.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController // = @Controller + @ResponseBody
public class CommentController {
    @Autowired
    CommentService commentService;

    // 게시물 번호를 받으면 그 게시물에 달린 모든 댓글을 반환하는 메서드
    @RequestMapping("/comments") // comments?bno=1080
    public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;

        try {
             list = commentService.getList(bno);
             return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 200
        } catch (Exception e) {
           e.printStackTrace();
           // 사용자가 잘못 요청해서 에러나니까 400번대를 날린다.
           return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400
        }
    }

    // 댓글 삭제하는 메서드
    // 맵핑된 url(cno)을 읽어올 때는 @PathVariable을 붙여줘야 한다.
    @DeleteMapping("/comments/{cno}") // comments/1?bno=1 <-- 삭제할 댓글 번호
    public ResponseEntity<String> remove(HttpSession session, @PathVariable Integer cno, Integer bno) {
//        String commenter = "jipark09";
        String commenter = (String)session.getAttribute("id");
        try {
            int rowCnt = commentService.remove(cno, bno, commenter);

            if(rowCnt != 1) {
                throw new Exception("DELETE Failed");
            }
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 댓글 저장하는 메서드
    @PostMapping("/comments") // comments?bno=1 POST
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
        String commenter = (String) session.getAttribute("id");
        // 로그인을 안해주고 있기 때문에 일일이 넣어준 것!
//        String commenter = "jipark09";
        dto.setCommenter(commenter);
        dto.setBno(bno);
        System.out.println("dto=" + dto);

        try {
            int result = commentService.write(dto);

            if(result != 1) {
                throw new Exception("Write Failed");
            }

            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 댓글을 수정하는 메서드
    @PatchMapping("/comments/{cno}") // 수정할 댓글 번호를 적어준다.
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session) {
        String commenter = (String) session.getAttribute("id");
//        String commenter = "jipark09";
        dto.setCommenter(commenter);
        dto.setCno(cno);

        try {
            int result = commentService.modify(dto);

            if(result != 1) {
                throw new Exception("Modify Failed!");
            }
            return new ResponseEntity<String>("MOD_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }
}
