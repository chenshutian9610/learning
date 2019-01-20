package org.tree.learning.model;

import org.tree.commons.annotation.Comment;

@Comment("tag")
public class Tag {
    @Comment("标签名")
    private String tagName;

    public static final String TABLE = "tag";

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tagName=").append(tagName);
        sb.append("]");
        return sb.toString();
    }
}