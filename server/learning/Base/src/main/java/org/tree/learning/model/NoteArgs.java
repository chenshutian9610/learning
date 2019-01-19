package org.tree.learning.model;

import org.tree.commons.support.mapper.Args;

public class NoteArgs extends Args {
    private Arg id = new Arg("id");

    private Arg tags = new Arg("tags");

    private Arg title = new Arg("title");

    private Arg content = new Arg("content");

    {
        args.add(id);
        args.add(tags);
        args.add(title);
        args.add(content);
    }

    public NoteArgs setId(boolean contained) {
        this.id.setContained(contained);
        return this;
    }

    public NoteArgs setTags(boolean contained) {
        this.tags.setContained(contained);
        return this;
    }

    public NoteArgs setTitle(boolean contained) {
        this.title.setContained(contained);
        return this;
    }

    public NoteArgs setContent(boolean contained) {
        this.content.setContained(contained);
        return this;
    }

    public NoteArgs setAllTrue() {
        for (Arg arg : args)
        	arg.setContained(true);
        return this;
    }
}