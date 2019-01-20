package org.tree.learning.mapper;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tree.commons.support.mapper.MapperMap;

@Component
public class LocalMapperMap extends MapperMap {
    @Autowired
    TagMapper tagMapper;

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    LearnerMapper learnerMapper;

    @PostConstruct
    public void init() {
        put("Tag", tagMapper);
        put("Note", noteMapper);
        put("Learner", learnerMapper);
    }
}