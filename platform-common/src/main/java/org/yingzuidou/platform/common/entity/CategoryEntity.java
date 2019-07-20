package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/3
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "category", schema = "cms_web", catalog = "")
public class CategoryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "category_name")
    @Length(max = 20, message = "分类名不能超过20个字符")
    private String categoryName;

    @Basic
    @Column(name = "in_use", insertable = false)
    private String inUse;

    @Basic
    @Column(name = "category_desc")
    @Length(max = 20, message = "分类描述不能超过50个字符")
    private String categoryDesc;

    @Basic
    @Column(name = "sort")
    private Integer sort;

    @Basic
    @Column(name = "is_delete", insertable = false)
    private String isDelete;

    @Basic
    @Column(name = "creator")
    private Integer creator;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "updator")
    private Integer updator;

    @Basic
    @Column(name = "update_time")
    private Date updateTime;

}
