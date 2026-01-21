package com.healthcare.appointment.interceptor;

import com.healthcare.appointment.service.MaintenanceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Autowired
    private MaintenanceService maintenanceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("Request URI: " + uri + ", Maintenance mode: " + maintenanceService.isMaintenanceMode());

        // Allow admin pages and maintenance page even during maintenance
        if (uri.startsWith("/admin") || uri.equals("/maintenance")) {
            return true;
        }

        if (maintenanceService.isMaintenanceMode()) {
            System.out.println("Redirecting to maintenance");
            response.sendRedirect("/maintenance");
            return false;
        }

        return true;
    }
}
