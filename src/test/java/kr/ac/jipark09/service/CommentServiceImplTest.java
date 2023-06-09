package kr.ac.jipark09.service;

import kr.ac.jipark09.dao.BoardDao;
import kr.ac.jipark09.dao.CommentDao;
import kr.ac.jipark09.domain.BoardDto;
import kr.ac.jipark09.domain.CommentDto;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class CommentServiceImplTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentDao commentDao;
    @Autowired
    BoardDao boardDao;

    @Test
    @Transactional
    public void remove() throws Exception {
        boardDao.deleteAll();

        BoardDto boardDto = new BoardDto("hello", "hello", "asdf");
        assertTrue(boardDao.insert(boardDto) == 1);
        Integer bno = boardDao.selectAll().get(0).getBno();
        System.out.println("bno = " + bno);

        commentDao.deleteAll(bno);
        CommentDto commentDto = new CommentDto(bno,0,"hi","qwer");

        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
        assertTrue(commentService.write(commentDto)==1);
        assertTrue(boardDao.select(bno).getComment_cnt() == 1);

        Integer cno = commentDao.selectAll(bno).get(0).getCno();

        // 일부러 예외를 발생시키고 Tx가 취소되는지 확인해야.
        int rowCnt = commentService.remove(cno, bno, commentDto.getCommenter());
        assertTrue(rowCnt==1);
        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
    }

    @Test
    @Transactional
    public void write() throws  Exception {
        boardDao.deleteAll();

        BoardDto boardDto = new BoardDto("hello", "hello", "asdf");
        assertTrue(boardDao.insert(boardDto) == 1);
        Integer bno = boardDao.selectAll().get(0).getBno();
        System.out.println("bno = " + bno);

        commentDao.deleteAll(bno);
        CommentDto commentDto = new CommentDto(bno,0,"hi","qwer");

        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
        assertTrue(commentService.write(commentDto) == 1);

        Integer cno = commentDao.selectAll(bno).get(0).getCno();
        assertTrue(boardDao.select(bno).getComment_cnt() == 1);
    }
}