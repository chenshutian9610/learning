package org.tree.learning.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.tree.commons.support.mapper.BaseMapper;
import org.tree.learning.model.Learner;
import org.tree.learning.model.LearnerExample;

public interface LearnerMapper extends BaseMapper<Learner> {
    long countByExample(LearnerExample example);

    int deleteByExample(LearnerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Learner record);

    int insertSelective(Learner record);

    List<Learner> selectByExample(LearnerExample example);

    Learner selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Learner record, @Param("example") LearnerExample example);

    int updateByExample(@Param("record") Learner record, @Param("example") LearnerExample example);

    int updateByPrimaryKeySelective(Learner record);

    int updateByPrimaryKey(Learner record);

    List<Learner> querySelective(@Param("sql") String sql);

    List<Learner> querySelectiveByExample(@Param("example") LearnerExample example);

    int insertSelectiveWithGeneratedKey(Learner learner);

    int insertBatchSelective(List<Learner> records);
}