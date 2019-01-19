package org.tree.learning.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.tree.commons.support.mapper.BaseMapper;
import org.tree.learning.model.Tag;
import org.tree.learning.model.TagExample;

/* 该表没有主键，或数据库驱动版本错误 */
public interface TagMapper extends BaseMapper<Tag> {
    long countByExample(TagExample example);

    int deleteByExample(TagExample example);

    int insert(Tag record);

    int insertSelective(Tag record);

    List<Tag> selectByExample(TagExample example);

    int updateByExampleSelective(@Param("record") Tag record, @Param("example") TagExample example);

    int updateByExample(@Param("record") Tag record, @Param("example") TagExample example);

    List<Tag> querySelective(@Param("sql") String sql);

    List<Tag> querySelectiveByExample(@Param("example") TagExample example);

    int insertBatchSelective(List<Tag> records);
}