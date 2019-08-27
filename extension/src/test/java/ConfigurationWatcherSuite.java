import com.hframework.common.frame.ServiceFactory;
import com.hframework.datacenter.myna.ConfigurationWatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class ConfigurationWatcherSuite {

    @Autowired
    protected ApplicationContext ctx;
    @Before
    public void init() throws Exception {
        ServiceFactory.initContext(ctx);
    }
    @Test
    public void test() throws Exception {
        ConfigurationWatcher watcher = ConfigurationWatcher.getInstance();
        watcher.start();
        System.out.println(watcher);
    }
}
