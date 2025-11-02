package com.example.hire.enums;

public enum EvaluationOperator {
    EQUALS,                 // Eşittir (==)
    NOT_EQUALS,            // Eşit değildir (!=)
    GREATER_THAN,          // Büyüktür (>)
    GREATER_THAN_OR_EQUAL, // Büyük veya eşit (>=)
    LESS_THAN,             // Küçüktür (<)
    LESS_THAN_OR_EQUAL,    // Küçük veya eşit (<=)
    IN,                    // Listeden biri (Örn: "Lisans", "Y. Lisans")
    NOT_IN,                // Listede değil
    CONTAINS,              // İçerir (String için)
    NOT_CONTAINS,          // İçermez (String için)
    BEFORE_DATE,           // Tarihten önce
    AFTER_DATE,            // Tarihten sonra
    IS_TRUE,               // Boolean true
    IS_FALSE               // Boolean false
}

