package com.icthh.xm.ms.mstemplate.config.domainevent;//package com.icthh.xm.ms.mstemplate.config.domainevent;
//
//import com.icthh.xm.commons.lep.api.LepManagementService;
//import com.icthh.xm.commons.tenant.TenantContextHolder;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
//@EnableAsync
//@Configuration
//public class DomainEventAsyncConfigurer implements AsyncConfigurer {
//
//    private final TenantContextHolder tenantContextHolder;
//    private final LepManagementService lepManagementService;
//
//    public DomainEventAsyncConfigurer(TenantContextHolder tenantContextHolder,
//                                      LepManagementService lepManagementService) {
//        this.tenantContextHolder = tenantContextHolder;
//        this.lepManagementService = lepManagementService;
//    }
//
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
////        executor.setTaskDecorator(new InheritableTenantContextDecorator(tenantContextHolder, lepManagementService));
//        executor.initialize();
//        return executor;
//    }
//}
