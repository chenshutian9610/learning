package generate.model;

import org.tree.commons.generate.annotation.Column;
import org.tree.commons.generate.annotation.Table;

/**
 * @author er_dong_chen
 * @date 2019/1/14
 */
@Table(comment = "学习者")
public class Learner {
    @Column(id = true)
    long id;

    @Column(length = 20, comment = "用户名")
    String username;

    @Column(comment = "密码")
    String password;

    @Column(comment = "是否拥有管理员权限")
    boolean admin;
}
