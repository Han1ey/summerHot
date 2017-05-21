package xyz.fz.controller.brand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.fz.domain.brand.TBrand;
import xyz.fz.service.brand.BrandService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/21.
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    @RequestMapping("/main")
    public String main() {
        return "brand/main";
    }

    @RequestMapping("/brandPageList")
    @ResponseBody
    public Map<String, Object> brandPageList(@RequestParam(value = "draw", required = false, defaultValue = "0") int draw,
                                             @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                             @RequestParam(value = "length", required = false, defaultValue = "20") int length,
                                             @RequestParam("name") String name,
                                             @RequestParam("isActivity") Integer isActivity) {
        int curPage = (start / length);
        Map<String, Object> result = new HashMap<>();
        Page<TBrand> page = brandService.brandPageList(name, isActivity, curPage, length);
        result.put("draw", draw);
        result.put("recordsTotal", page.getTotalElements());
        result.put("recordsFiltered", page.getTotalElements());
        result.put("data", page.getContent());
        return result;
    }

    @RequestMapping("/saveBrand")
    @ResponseBody
    public Map<String, Object> saveBrand(TBrand brand) {

        Map<String, Object> result = new HashMap<>();
        try {
            brand.setIsActivity(1);
            brandService.saveBrand(brand);
            result.put("success", true);
        } catch (DataIntegrityViolationException e) {
            result.put("success", false);
            result.put("message", "品牌名称重复");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping("/toggle")
    @ResponseBody
    public Map<String, Object> toggle(Long id) {

        Map<String, Object> result = new HashMap<>();
        try {
            brandService.toggle(id);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
