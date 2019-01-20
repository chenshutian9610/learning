package org.tree.learning.model;

import org.tree.commons.annotation.Comment;

@Comment("note")
public class Note {
    private Long id;

    @Comment("标签")
    private String tags;

    @Comment("标题")
    private String title;

    @Comment("内容")
    private String content;

    public static final String TABLE = "note";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tags=").append(tags);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}