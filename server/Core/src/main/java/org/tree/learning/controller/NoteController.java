package org.tree.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tree.commons.support.controller.Result;
import org.tree.commons.support.mapper.MapperTemplate;
import org.tree.learning.dto.NoteDTO;
import org.tree.learning.model.*;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author er_dong_chen
 * @date 2019/1/14
 */
@RestController
@RequestMapping("/note")
@Transactional
public class NoteController {
    @Autowired
    private MapperTemplate mapper;

    private List<String> tagList;

    @RequestMapping("/getTags.do")
    public Result getTags() {
        tagList = initTagList(true);
        Result result = new Result(true);
        result.addResponseData("tags", tagList);
        return result;
    }

    @RequestMapping("/select.do")
    public Result select(String[] tags, Boolean refresh) {
        NoteExample example = new NoteExample();
        if (tags != null && tags.length != 0) {
            Arrays.sort(tags);
            example.createCriteria().andTagsLike("%(" + String.join(")(", tags) + ")%");
        }
        List<NoteDTO> notes = mapper.selectByExample(example).stream().map(NoteDTO::new).collect(toList());

        Result result = new Result(true);
        result.addResponseData("notes", notes);
        if (refresh != null && refresh)
            result.addResponseData("tags", tagList);
        return result;
    }

    @RequestMapping("/insert.do")
    public Result insert(NoteDTO note) {
        boolean success = mapper.insertSelective(note.toNote()) == 1;
        if (success)
            dealTags(note);
        return select(null, true);
    }

    @RequestMapping("/update.do")
    public Result update(NoteDTO note) {
        boolean success = mapper.updateByPrimaryKey(note.toNote()) == 1;
        if (success)
            dealTags(note);
        return success ? select(null, true) : new Result();
    }

    @RequestMapping("/delete.do")
    public Result delete(Long id) {
        if (id == null)
            return new Result(false, "缺少参数 id");
        return mapper.deleteByPrimaryKey(Note.class, id) == 1 ? select(null, false) : new Result();
    }

    private void dealTags(NoteDTO note) {
        /* 初始化 tags 和 tagList */
        List<String> tags = note.getTags();
        if (tagList == null || tagList.size() == 0)
            tagList = initTagList(false);

        /* 比对 tags 和 tagList，查找新增标签 */
        tagList.forEach(tag -> tags.remove(tag));
        tags.forEach(tag -> {
            if (tag.length() == 0)
                tags.remove(tag);
        });

        /* 将新增标签插入数据库并更新 tagList */
        if (tags.size() != 0) {
            List<Tag> beInserting = tags.stream().map(tag -> {
                Tag temp = new Tag();
                temp.setTagName(tag);
                return temp;
            }).collect(toList());
            if (mapper.batchInsert(beInserting) != 0) {
                tagList.clear();
                tagList.addAll(initTagList(false));
            }
        }
    }

    private List<String> initTagList(boolean delete) {
        if (delete) {
            tagList = mapper.queryByExample(new NoteArgs().setTags(true), new NoteExample()).stream()
                    .map(note -> note.getTags().substring(1, note.getTags().length() - 1).split("\\)\\("))
                    .flatMap(Arrays::stream).distinct().collect(toList());
            if (tagList.size() != 0) {
                TagExample example = new TagExample();
                example.createCriteria().andTagNameNotIn(tagList);
                mapper.deleteByExample(example);
            }
        } else {
            tagList = mapper.selectByExample(new TagExample()).stream()
                    .map(Tag::getTagName).distinct().sorted().collect(toList());
        }

        return tagList;
    }
}
