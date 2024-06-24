//package com.icthh.xm.ms.mstemplate.listeners;
//
//import com.icthh.xm.commons.topic.service.DynamicConsumerConfigurationService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class ApplicationStartup1 implements ApplicationListener<ApplicationReadyEvent> {
//
//    @Value("${server.port}")
//    private String serverPort;
//    private final DynamicConsumerConfigurationService dynamicConsumerConfigurationService;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        if (serverPort.equals("8082")) {
//            dynamicConsumerConfigurationService.refreshDynamicConsumers("OM");
//        }
//    }
//}
