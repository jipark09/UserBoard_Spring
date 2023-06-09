package kr.ac.jipark09.dao;

import kr.ac.jipark09.domain.BoardDto;
import kr.ac.jipark09.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardDao {
    BoardDto select(Integer bno) throws Exception;

    List<BoardDto> selectAll() throws Exception;

    int count() throws Exception;

    int deleteAll() throws Exception;

    int delete(Integer bno, String writer) throws Exception;

    int deleteForAdim() throws Exception;

    int insert(BoardDto dto) throws Exception;

    int selectFromBoard() throws Exception;

    List<BoardDto> selectPage(Map map) throws Exception;

    int update(BoardDto dto) throws Exception;

    int updateCommentCnt(Integer cnt, Integer bno) throws Exception;

    int increaseViewCnt(Integer bno) throws Exception;

    int searchResultCnt(SearchCondition sc) throws Exception;

    List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception;
}
