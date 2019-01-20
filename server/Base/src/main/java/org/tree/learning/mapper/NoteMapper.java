package org.tree.learning.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.tree.commons.support.mapper.BaseMapper;
import org.tree.learning.model.Note;
import org.tree.learning.model.NoteExample;

public interface NoteMapper extends BaseMapper<Note> {
    long countByExample(NoteExample example);

    int deleteByExample(NoteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Note record);

    int insertSelective(Note record);

    List<Note> selectByExample(NoteExample example);

    Note selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByExample(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKey(Note record);

    List<Note> querySelective(@Param("sql") String sql);

    List<Note> querySelectiveByExample(@Param("example") NoteExample example);

    int insertSelectiveWithGeneratedKey(Note note);

    int insertBatchSelective(List<Note> records);
}