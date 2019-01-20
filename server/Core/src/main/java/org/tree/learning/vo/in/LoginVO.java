package org.tree.learning.vo.in;

import org.hibernate.validator.constraints.NotBlank;
import org.tree.commons.annotation.Comment;
import org.tree.commons.utils.PasswordUtils;

/**
 * @author er_dong_chen
 * @date 2019/1/14
 *
 * <p>正常登陆
 */
public class LoginVO {
    @NotBlank
    @Comment("用户名")
    private String username;

    @NotBlank
    @Comment("密码")
    private String password;

    @NotBlank
    @Comment("图片验证码")
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password == null ? null : PasswordUtils.deal(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
