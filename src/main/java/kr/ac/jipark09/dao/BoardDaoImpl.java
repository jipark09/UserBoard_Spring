package kr.ac.jipark09.dao;

import kr.ac.jipark09.domain.BoardDto;
import kr.ac.jipark09.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    SqlSession session;
    String namespace = "kr.ac.jipark09.dao.BoardMapper.";

    @Override
    public BoardDto select(Integer bno) throws Exception {
        return session.selectOne(namespace + "select", bno);
    }

    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace + "selectAll");
    }

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace + "count");
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace + "deleteAll");

    }

    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap<>();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace + "delete", map);
    }

    @Override
    public int deleteForAdim() throws Exception {
        return session.delete(namespace + "deleteForAim");
    }

    @Override
    public int insert(BoardDto dto) throws Exception {
        return session.insert(namespace + "insert", dto);
    }

    @Override
    public int selectFromBoard() throws Exception {
        return session.selectOne(namespace + "selectFromBoard");
    }

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace + "selectPage", map);
    }

    @Override
    public int update(BoardDto dto) throws Exception {
        return session.update(namespace + "update", dto);
    }

    @Override
    public int updateCommentCnt(Integer cnt, Integer bno) throws Exception {
        Map map = new HashMap();
        map.put("cnt", cnt);
        map.put("bno", bno);
        return session.update(namespace + "updateCommentCnt", map);
    }

    @Override
    public int increaseViewCnt(Integer bno) throws Exception {
        return session.update(namespace + "increaseViewCnt", bno);
    }

    @Override
    public int searchResultCnt(SearchCondition sc) throws Exception {
        return session.selectOne(namespace + "searchResultCnt", sc);
    }

    @Override
    public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchSelectPage", sc);
    }
}
