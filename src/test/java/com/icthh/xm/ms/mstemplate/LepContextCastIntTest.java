package com.icthh.xm.ms.mstemplate;

import com.icthh.xm.commons.lep.XmLepScriptConfigServerResourceLoader;
import com.icthh.xm.commons.security.XmAuthenticationContextHolder;
import com.icthh.xm.commons.tenant.TenantContextHolder;
import com.icthh.xm.commons.tenant.TenantContextUtils;
import com.icthh.xm.lep.api.LepManager;
import com.icthh.xm.ms.mstemplate.config.TestLepConfiguration.TestLepService;
import com.icthh.xm.ms.mstemplate.config.lep.LepContext;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.icthh.xm.commons.lep.XmLepConstants.THREAD_CONTEXT_KEY_AUTH_CONTEXT;
import static com.icthh.xm.commons.lep.XmLepConstants.THREAD_CONTEXT_KEY_TENANT_CONTEXT;
import static org.junit.Assert.assertTrue;

/**
 * Should be in EVERY XM MICROSERVICE, THAT USE LEP.
 * When you add new field to lepContext this test help to not forget add it to class LepContext.
 */
@Slf4j
public class LepContextCastIntTest extends AbstractSpringBootTest {

    @Autowired
    private TenantContextHolder tenantContextHolder;

    @Autowired
    private XmAuthenticationContextHolder authContextHolder;

    @Autowired
    private LepManager lepManager;

    @Autowired
    private XmLepScriptConfigServerResourceLoader leps;

    @Autowired
    private TestLepService testLepService;

    @SneakyThrows
    @Before
    public void setup() {
        TenantContextUtils.setTenant(tenantContextHolder, "TEST_TENANT");

        lepManager.beginThreadContext(ctx -> {
            ctx.setValue(THREAD_CONTEXT_KEY_TENANT_CONTEXT, tenantContextHolder.getContext());
            ctx.setValue(THREAD_CONTEXT_KEY_AUTH_CONTEXT, authContextHolder.getContext());
        });
    }

    @After
    public void tearDown() {
        lepManager.endThreadContext();
        tenantContextHolder.getPrivilegedContext().destroyCurrentContext();
    }

    @Test
    @SneakyThrows
    public void testLepContextCast() {
        String prefix = "/config/tenants/TEST_TENANT/mstemplate/lep/test/";
        String key = prefix + "ScriptWithAround$$around.groovy";
        String body = "import com.icthh.xm.ms.mstemplate.config.lep.LepContext;\nLepContext context = lepContext\nreturn ['context':context]";
        leps.onRefresh(key, body);
        Map<String, Object> result = testLepService.sayHello();
        assertTrue(result.get("context") instanceof LepContext);
        leps.onRefresh(key, null);
    }

}
