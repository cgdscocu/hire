package com.example.hire.enums;

public enum ProcessStatus {
    PENDING("Beklemede"),
    IN_PROGRESS("Devam Ediyor"),
    COMPLETED("Tamamlandı");
    
    private final String displayName;
    
    ProcessStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
