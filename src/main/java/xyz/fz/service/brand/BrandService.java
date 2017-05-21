package xyz.fz.service.brand;

import org.springframework.data.domain.Page;
import xyz.fz.domain.brand.TBrand;

/**
 * Created by fz on 2016/9/19.
 */
public interface BrandService {

    Page<TBrand> brandPageList(String brandName, int isActivity, int curPage, int pageSize);

    TBrand saveBrand(TBrand brand);

    void toggle(Long id);
}
