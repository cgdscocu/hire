package com.example.hire.enums;

public enum ProcessType {
    APPLICATION("Başvuru"),
    ASSESSMENT("Ölçme Değerlendirme"),
    OFFER("Teklif"),
    ONBOARDING("Onboarding");
    
    private final String displayName;
    
    ProcessType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
