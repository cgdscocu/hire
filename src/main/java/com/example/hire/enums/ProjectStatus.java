package com.example.hire.enums;

public enum ProjectStatus {
    ACTIVE("Aktif"),
    INACTIVE("Pasif"),
    CANCELLED("İptal Edildi");

    private final String displayName;

    ProjectStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
