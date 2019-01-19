package generate.model;

import org.tree.commons.generate.annotation.Column;
import org.tree.commons.generate.annotation.Table;

/**
 * @author er_dong_chen
 * @date 2019/1/15
 */
@Table(comment = "标签")
public class Tag {
    @Column(comment = "标签名", length = 20)
    String tag_name;
}
