import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.fz.Application;
import xyz.fz.dao.brand.BrandDao;
import xyz.fz.domain.brand.TBrand;
import xyz.fz.domain.role.TRoleAuth;
import xyz.fz.service.role.RoleAuthService;

import java.util.List;

/**
 * Created by fz on 2016/9/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleAuthService roleAuthService;

    @Autowired
    private BrandDao brandDao;

    @Test
    public void roleAddTest() {
        for (int i=0; i<100; i++) {
            jdbcTemplate.execute("insert into t_role(role_name, is_activity) values('客服" + i + "', 1)");
        }
    }

    @Test
    public void menuAddTest() {
        for (int i=0; i<100; i++) {
            jdbcTemplate.execute("insert into t_menu(menu_name, menu_path, sort, is_activity) values('菜单" + i + "', '/menu" + i + "', " + i + ", 1)");
        }
    }

    @Test
    public void roleAuthMenuAddTest() {
        TRoleAuth roleAuth = new TRoleAuth();
        roleAuth.setRoleId(105L);
        roleAuth.setMenuId(1L);
        roleAuth.setAuthId(0L);
        roleAuthService.saveRoleAuth(roleAuth);
    }

    @Test
    public void brandBatchAddTest() {
        List<TBrand> list = Lists.newArrayList();
        String baseName = "brand-";
        for (int i=0; i<1000; i++) {
            TBrand brand = new TBrand();
            brand.setIsActivity(1);
            brand.setBrandName(baseName + i);
            list.add(brand);
        }
        brandDao.save(list);
    }
}
