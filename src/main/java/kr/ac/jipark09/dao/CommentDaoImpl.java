package kr.ac.jipark09.dao;

import kr.ac.jipark09.domain.CommentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "kr.ac.jipark09.dao.CommentMapper.";

    @Override
    public int deleteAll(Integer bno) {
        return session.delete(namespace + "deleteAll", bno);
    }

    @Override
    public int delete(Integer cno, String commenter) throws Exception {
        Map map = new HashMap();
        map.put("cno", cno);
        map.put("commenter", commenter);
        return session.delete(namespace + "delete", map);
    }

    @Override
    public int insert(CommentDto commentDto) throws Exception {
        return session.insert(namespace + "insert", commentDto);

    }

    @Override
    public List<CommentDto> selectAll(Integer bno) throws Exception {
        return session.selectList(namespace + "selectAll", bno);
    }

    @Override
    public CommentDto select(Integer cno) throws Exception {
        return session.selectOne(namespace + "select", cno);
    }

    @Override
    public int count(int bno) throws Exception {
        return session.selectOne(namespace + "count", bno);
    }

    @Override
    public int update(CommentDto commentDto) throws Exception {
        return session.update(namespace + "update", commentDto);

    }
}
