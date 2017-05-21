package xyz.fz.dao.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.fz.domain.brand.TBrand;

/**
 * Created by fz on 2016/9/19.
 */
@Repository
public interface BrandDao extends JpaRepository<TBrand, Long> {

    @Query("select b from TBrand b where b.brandName like concat('', :brandName, '%') and isActivity = :isActivity ")
    Page<TBrand> findByBrandName(@Param("brandName") String brandName, @Param("isActivity") int isActivity, Pageable pageable);
}
