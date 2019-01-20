package generate.model;

import org.tree.commons.generate.annotation.Column;
import org.tree.commons.generate.annotation.Table;

/**
 * @author er_dong_chen
 * @date 2019/1/14
 */
@Table(comment = "便签")
public class Note {
    @Column(id = true)
    long id;

    @Column(length = 255, comment = "标签")
    String tags;

    @Column(length = 20, comment = "标题")
    String title;

    @Column(length = 1024, comment = "内容")
    String content;
}
