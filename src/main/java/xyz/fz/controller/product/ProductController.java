package xyz.fz.controller.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.fz.domain.product.TProduct;
import xyz.fz.service.product.ProductService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping("/main")
    public String main() {
        return "product/main";
    }

    @RequestMapping("/productPageList")
    @ResponseBody
    public Map<String, Object> productPageList(@RequestParam(value = "draw", required = false, defaultValue = "0") int draw,
                                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                               @RequestParam(value = "length", required = false, defaultValue = "20") int length,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "number", required = false) String number,
                                               @RequestParam(value = "brandId", required = false, defaultValue = "0") Long brandId,
                                               @RequestParam(value = "isActivity", required = false) Integer isActivity) {
        int curPage = (start / length);
        Map<String, Object> result = new HashMap<>();
        Page<TProduct> page = productService.productPageList(name, number, brandId, isActivity, curPage, length);
        result.put("draw", draw);
        result.put("recordsTotal", page.getTotalElements());
        result.put("recordsFiltered", page.getTotalElements());
        result.put("data", page.getContent());
        return result;
    }

    @RequestMapping("/saveProduct")
    @ResponseBody
    public Map<String, Object> saveProduct(TProduct product) {

        Map<String, Object> result = new HashMap<>();
        try {
            product.setIsActivity(1);
            productService.saveProduct(product);
            result.put("success", true);
        } catch (DataIntegrityViolationException e) {
            result.put("success", false);
            result.put("message", "商品名称或商品编码存在重复");
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
            productService.toggle(id);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

}
