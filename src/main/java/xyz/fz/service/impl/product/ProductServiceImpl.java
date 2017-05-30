package xyz.fz.service.impl.product;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import xyz.fz.dao.product.ProductDao;
import xyz.fz.domain.product.TProduct;
import xyz.fz.service.product.ProductService;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

/**
 * Created by fz on 2016/9/19.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Page<TProduct> productPageList(String productName, String productNumber, long brandId, int isActivity, int curPage, int pageSize) {

        Pageable pageable = new PageRequest(curPage, pageSize);
        TProduct product = new TProduct();
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotBlank(productName)) {
            product.setProductName(productName);
            matcher = matcher.withMatcher("productName", contains().startsWith());
        }
        if (StringUtils.isNotBlank(productNumber)) {
            product.setProductNumber(productNumber);
            matcher = matcher.withMatcher("productNumber", contains().startsWith());
        }
        if (brandId > 0) {
            product.setBrandId(brandId);
            matcher = matcher.withMatcher("brandId", contains().exact());
        }
        product.setIsActivity(isActivity);
        matcher = matcher.withMatcher("isActivity", contains().exact());
        Example<TProduct> productExample = Example.of(product, matcher);
        return productDao.findAll(productExample, pageable);
    }

    @Override
    public TProduct saveProduct(TProduct product) {
        return productDao.save(product);
    }

    @Override
    public void toggle(Long id) {
        TProduct product = productDao.findOne(id);
        if (product.getIsActivity() == 0) {
            product.setIsActivity(1);
        } else {
            product.setIsActivity(0);
        }
        productDao.save(product);
    }

}
