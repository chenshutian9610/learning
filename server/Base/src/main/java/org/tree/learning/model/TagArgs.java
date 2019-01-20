package org.tree.learning.model;

import org.tree.commons.support.mapper.Args;

public class TagArgs extends Args {
    private Arg tagName = new Arg("tag_name");

    {
        args.add(tagName);
    }

    public TagArgs setTagName(boolean contained) {
        this.tagName.setContained(contained);
        return this;
    }

    public TagArgs setAllTrue() {
        for (Arg arg : args)
        	arg.setContained(true);
        return this;
    }
}