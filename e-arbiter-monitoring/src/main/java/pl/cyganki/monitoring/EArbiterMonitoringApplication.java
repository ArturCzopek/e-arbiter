package pl.cyganki.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import pl.cyganki.utils.annotation.EnableArbiterAdminServiceData;

@EnableArbiterAdminServiceData
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
public class EArbiterMonitoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(EArbiterMonitoringApplication.class, args);
    }
}
