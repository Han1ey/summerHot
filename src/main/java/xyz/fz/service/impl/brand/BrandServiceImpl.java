package xyz.fz.service.impl.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import xyz.fz.dao.brand.BrandDao;
import xyz.fz.domain.brand.TBrand;
import xyz.fz.service.brand.BrandService;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

/**
 * Created by fz on 2016/9/19.
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public Page<TBrand> brandPageList(String brandName, int isActivity, int curPage, int pageSize) {

        Pageable pageable = new PageRequest(curPage, pageSize);
        return brandDao.findByBrandName(brandName, isActivity, pageable);
    }

    @Override
    @CacheEvict(value = {"allBrand"}, allEntries = true)
    public TBrand saveBrand(TBrand brand) {
        return brandDao.save(brand);
    }

    @Override
    @CacheEvict(value = {"allBrand"}, allEntries = true)
    public void toggle(Long id) {
        TBrand brand = brandDao.findOne(id);
        if (brand.getIsActivity() == 0) {
            brand.setIsActivity(1);
        } else {
            brand.setIsActivity(0);
        }
        brandDao.save(brand);
    }

    @Override
    @Cacheable(value = "allBrand", keyGenerator = "myRedisKeyGenerator")
    public List<TBrand> brandList() {
        TBrand brand = new TBrand();
        brand.setIsActivity(1);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("id", contains().exact());
        Example<TBrand> brandExample = Example.of(brand, matcher);
        return brandDao.findAll(brandExample);
    }
}
