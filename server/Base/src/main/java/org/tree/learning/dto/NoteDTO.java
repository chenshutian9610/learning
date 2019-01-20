package org.tree.learning.dto;

import org.tree.commons.annotation.Comment;
import org.tree.learning.model.Note;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author er_dong_chen
 * @date 2019/1/18
 */
public class NoteDTO {
    private Long id;

    @Comment("标签")
    private List<String> tags;

    @Comment("标题")
    private String title;

    @Comment("内容")
    private String content;

    public NoteDTO() {
    }

    public NoteDTO(Note note) {
        id = note.getId();
        title = note.getTitle();
        content = note.getContent();
        tags = new LinkedList<>();
        Collections.addAll(tags, note.getTags().substring(1, note.getTags().length() - 1).split("\\)\\("));
    }

    public Note toNote() {
        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setContent(content);
        tags.sort(String::compareTo);
        note.setTags(tags.size() == 0 ? "" : "(" + String.join(")(", tags) + ")");
        return note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
