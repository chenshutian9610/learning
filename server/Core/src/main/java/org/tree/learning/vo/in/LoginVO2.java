package org.tree.learning.vo.in;

import org.hibernate.validator.constraints.NotBlank;
import org.tree.commons.annotation.Comment;

/**
 * @author er_dong_chen
 * @date 2019/1/19
 *
 * <p>通过验证码登陆
 */
public class LoginVO2 {
    @NotBlank
    @Comment("用户名")
    private String username;

    @NotBlank
    @Comment("验证码")
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
