package com.healthcare.appointment.config;

import org.springframework.stereotype.Component;

@Component
public class MaintenanceConfig {
    private boolean maintenanceMode = false;

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public void setMaintenanceMode(boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }
}
