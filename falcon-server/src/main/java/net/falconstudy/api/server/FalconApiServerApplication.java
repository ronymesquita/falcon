package net.falconstudy.api.server;

import java.time.ZoneId;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FalconApiServerApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(
                ZoneId.of("America/Sao_Paulo")));
    }

    public static void main(String[] args) {
        System.setProperty("hibernate.dialect.storage_engine", "innodb");

        SpringApplication.run(FalconApiServerApplication.class, args);
    }

}
