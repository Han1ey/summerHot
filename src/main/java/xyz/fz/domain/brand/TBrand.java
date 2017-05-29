package xyz.fz.domain.brand;

import javax.persistence.*;

/**
 * Created by fz on 2016/9/11.
 */
@Entity()
@Table(name = "t_brand")
public class TBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String brandName;

    @Column(nullable = false, columnDefinition = "tinyint")
    private Integer isActivity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(Integer isActivity) {
        this.isActivity = isActivity;
    }
}
