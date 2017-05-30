package xyz.fz.dao.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fz.domain.product.TProduct;

/**
 * Created by fz on 2016/9/19.
 */
@Repository
public interface ProductDao extends JpaRepository<TProduct, Long> {
}
