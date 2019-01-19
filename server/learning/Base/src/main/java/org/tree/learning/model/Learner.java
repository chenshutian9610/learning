package org.tree.learning.model;

import org.tree.commons.annotation.Comment;

@Comment("learner")
public class Learner {
    private Long id;

    @Comment("用户名")
    private String username;

    @Comment("密码")
    private String password;

    @Comment("是否拥有管理员权限")
    private Boolean admin;

    public static final String TABLE = "learner";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", admin=").append(admin);
        sb.append("]");
        return sb.toString();
    }
}