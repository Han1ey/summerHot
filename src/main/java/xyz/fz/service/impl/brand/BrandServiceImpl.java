package xyz.fz.service.impl.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.fz.dao.brand.BrandDao;
import xyz.fz.domain.brand.TBrand;
import xyz.fz.service.brand.BrandService;

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
    public TBrand saveBrand(TBrand brand) {
        return brandDao.save(brand);
    }

    @Override
    public void toggle(Long id) {
        TBrand brand = brandDao.findOne(id);
        if (brand.getIsActivity() == 0) {
            brand.setIsActivity(1);
        } else {
            brand.setIsActivity(0);
        }
        brandDao.save(brand);
    }
}
