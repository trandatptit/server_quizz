//package com.trandat.btl_ltw.component;
//
//import com.trandat.btl_ltw.service.DeThiService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class SetStatus {
//    DeThiService deThiService;
//
//    @Scheduled(cron = "*/30 * * * * *")
//    public void checkAndOpenExam(){
//        deThiService.openExamsIfNecessary();
//    }
//}
