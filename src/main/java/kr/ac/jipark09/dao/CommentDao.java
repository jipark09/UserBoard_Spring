package kr.ac.jipark09.dao;

import kr.ac.jipark09.domain.CommentDto;

import java.util.List;

public interface CommentDao {
    int deleteAll(Integer bno);

    int delete(Integer cno, String commenter) throws Exception;

    int insert(CommentDto commentDto) throws Exception;

    List<CommentDto> selectAll(Integer bno) throws Exception;

    CommentDto select(Integer cno) throws Exception;

    int count(int bno) throws Exception;

    int update(CommentDto commentDto) throws Exception;
}
