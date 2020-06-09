package com.ihrm.system;

import com.ihrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author jack hao
 * @createTime 2020-06-09-23:30
 */
@SpringBootApplication(scanBasePackages = "com.ihrm")
@EntityScan(value="com.ihrm.domain.system")
public class SystemAppication {
    public static void main(String[] args) {
        SpringApplication.run(SystemAppication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}

