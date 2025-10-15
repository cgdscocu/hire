package com.example.hire.enums;

public enum ProcessStepStatus {
    PENDING("Beklemede"),
    IN_PROGRESS("Devam Ediyor"),
    COMPLETED("Tamamlandı"),
    
    private final String displayName;
    
    ProcessStepStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
