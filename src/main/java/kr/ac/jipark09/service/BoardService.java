package kr.ac.jipark09.service;

import kr.ac.jipark09.domain.BoardDto;
import kr.ac.jipark09.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardService {
    int getCount() throws Exception;

    int remove(Integer bno, String writer) throws Exception;

    int write(BoardDto boardDto) throws Exception;

    List<BoardDto> getList() throws Exception;

    BoardDto read(Integer bno) throws Exception;

    List<BoardDto> getPage(Map map) throws Exception;

    int modify(BoardDto boardDto) throws Exception;

    List<BoardDto> getSearchSelectPage(SearchCondition sc) throws Exception;

    int getSearchResultCnt(SearchCondition sc) throws Exception;
}
