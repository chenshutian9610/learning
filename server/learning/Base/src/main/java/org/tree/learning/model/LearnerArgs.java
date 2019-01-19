package org.tree.learning.model;

import org.tree.commons.support.mapper.Args;

public class LearnerArgs extends Args {
    private Arg id = new Arg("id");

    private Arg username = new Arg("username");

    private Arg password = new Arg("password");

    private Arg admin = new Arg("admin");

    {
        args.add(id);
        args.add(username);
        args.add(password);
        args.add(admin);
    }

    public LearnerArgs setId(boolean contained) {
        this.id.setContained(contained);
        return this;
    }

    public LearnerArgs setUsername(boolean contained) {
        this.username.setContained(contained);
        return this;
    }

    public LearnerArgs setPassword(boolean contained) {
        this.password.setContained(contained);
        return this;
    }

    public LearnerArgs setAdmin(boolean contained) {
        this.admin.setContained(contained);
        return this;
    }

    public LearnerArgs setAllTrue() {
        for (Arg arg : args)
        	arg.setContained(true);
        return this;
    }
}