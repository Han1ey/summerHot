package xyz.fz.service.product;

import org.springframework.data.domain.Page;
import xyz.fz.domain.product.TProduct;

/**
 * Created by Administrator on 2017/5/25.
 */
public interface ProductService {
    Page<TProduct> productPageList(String productName, String productNumber, long brandId, int isActivity, int curPage, int pageSize);

    TProduct saveProduct(TProduct product);

    void toggle(Long id);
}
