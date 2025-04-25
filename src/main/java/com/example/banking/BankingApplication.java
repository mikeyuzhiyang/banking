package com.example.banking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
@EnableCaching
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner openBrowserOnStartup() {
        return args -> {
            // 等待应用完全启动
            Thread.sleep(1000);

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI("http://localhost:8080"));
            } else {
                // 对于无GUI环境，可以尝试使用命令行打开
                Runtime runtime = Runtime.getRuntime();
                 runtime.exec("cmd /c start http://localhost:8080");
            }
        };
    }
}
