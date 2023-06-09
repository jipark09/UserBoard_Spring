package kr.ac.jipark09.service;

import kr.ac.jipark09.dao.BoardDao;
import kr.ac.jipark09.dao.CommentDao;
import kr.ac.jipark09.domain.CommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private BoardDao boardDao;
    private CommentDao commentDao;

    // @Autowired 없어도 자동으로 알아서 주입해 준다.
    // 생성자가 하나밖에 없을 때 가능하다.
    public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }

    @Override
    public int getCount(Integer bno) throws Exception {
        return commentDao.count(bno);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 예외가 발생하면 롤백
    public int remove(Integer cno, Integer bno, String commenter) throws Exception {
        // 게시판의 댓글 갯수 하나 줄음
        int rowCnt = boardDao.updateCommentCnt(-1, bno);
        System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
        // throw new Exception("test") // 예외가 잘 뜨는지 확인

        // 댓글 하나 삭제
        rowCnt = commentDao.delete(cno, commenter);
        System.out.println("rowCnt = " + rowCnt);

        return rowCnt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardDao.updateCommentCnt(1, commentDto.getBno());
        return commentDao.insert(commentDto);
    }

    @Override
    public List<CommentDto> getList(Integer bno) throws Exception {
        return commentDao.selectAll(bno);
    }

    @Override
    public CommentDto read(Integer cno) throws Exception{
        return commentDao.select(cno);
    }

    @Override
    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }
}
