package com.healthcare.appointment.service;

import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {
    private boolean maintenanceMode = false;

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public void enableMaintenance() {
        this.maintenanceMode = true;
    }

    public void disableMaintenance() {
        this.maintenanceMode = false;
    }
}
