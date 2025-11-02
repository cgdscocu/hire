package com.example.hire.service;

import com.example.hire.enums.EvaluationOperator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RuleEvaluator {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Rule'u değerlendirir ve true/false döner
     * @param operator Evaluation operator (EQUALS, IN, GREATER_THAN_OR_EQUAL, vb.)
     * @param answerValue Kullanıcının verdiği cevap
     * @param expectedValue Beklenen değer (hedef değer)
     * @return Rule geçiyorsa true, aksi halde false
     */
    public boolean evaluate(EvaluationOperator operator, Object answerValue, Object expectedValue) {
        if (answerValue == null || expectedValue == null) {
            return false;
        }

        try {
            return switch (operator) {
                case EQUALS -> answerValue.equals(expectedValue) || 
                              String.valueOf(answerValue).equalsIgnoreCase(String.valueOf(expectedValue));
                
                case NOT_EQUALS -> !answerValue.equals(expectedValue) && 
                                  !String.valueOf(answerValue).equalsIgnoreCase(String.valueOf(expectedValue));
                
                case GREATER_THAN -> compareNumbers(answerValue, expectedValue) > 0;
                
                case GREATER_THAN_OR_EQUAL -> compareNumbers(answerValue, expectedValue) >= 0;
                
                case LESS_THAN -> compareNumbers(answerValue, expectedValue) < 0;
                
                case LESS_THAN_OR_EQUAL -> compareNumbers(answerValue, expectedValue) <= 0;
                
                case IN -> checkInList(answerValue, expectedValue);
                
                case NOT_IN -> !checkInList(answerValue, expectedValue);
                
                case CONTAINS -> String.valueOf(answerValue).toLowerCase()
                        .contains(String.valueOf(expectedValue).toLowerCase());
                
                case NOT_CONTAINS -> !String.valueOf(answerValue).toLowerCase()
                        .contains(String.valueOf(expectedValue).toLowerCase());
                
                case BEFORE_DATE -> compareDates(answerValue, expectedValue) < 0;
                
                case AFTER_DATE -> compareDates(answerValue, expectedValue) > 0;
                
                case IS_TRUE -> Boolean.TRUE.equals(answerValue) || 
                               "true".equalsIgnoreCase(String.valueOf(answerValue));
                
                case IS_FALSE -> Boolean.FALSE.equals(answerValue) || 
                                "false".equalsIgnoreCase(String.valueOf(answerValue));
            };
        } catch (Exception e) {
            // Evaluation sırasında hata olursa false döner
            return false;
        }
    }

    /**
     * Sayısal karşılaştırma
     */
    private int compareNumbers(Object answerValue, Object expectedValue) {
        try {
            double answer = parseNumber(answerValue);
            double expected = parseNumber(expectedValue);
            return Double.compare(answer, expected);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Sayısal değere parse et
     */
    private double parseNumber(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return Double.parseDouble(String.valueOf(value));
    }

    /**
     * Tarih karşılaştırma
     */
    private int compareDates(Object answerValue, Object expectedValue) {
        try {
            LocalDate answerDate = parseDate(answerValue);
            LocalDate expectedDate = parseDate(expectedValue);
            return answerDate.compareTo(expectedDate);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Tarih parse et
     */
    private LocalDate parseDate(Object value) {
        if (value instanceof LocalDate) {
            return (LocalDate) value;
        }
        if (value instanceof String) {
            try {
                // ISO format (yyyy-MM-dd)
                return LocalDate.parse((String) value);
            } catch (Exception e) {
                // Diğer formatları deneyebilirsin
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return LocalDate.parse((String) value, formatter);
            }
        }
        throw new IllegalArgumentException("Cannot parse date from: " + value);
    }

    /**
     * IN operatörü için liste kontrolü
     * expectedValue JSON array veya comma-separated string olabilir
     */
    private boolean checkInList(Object answerValue, Object expectedValue) {
        String answer = String.valueOf(answerValue).trim();
        String expected = String.valueOf(expectedValue).trim();
        
        // JSON array formatında mı?
        if (expected.startsWith("[") && expected.endsWith("]")) {
            try {
                List<String> values = objectMapper.readValue(expected, new TypeReference<List<String>>() {});
                return values.contains(answer);
            } catch (Exception e) {
                // JSON parse hatası
            }
        }
        
        // Comma-separated string
        String[] values = expected.split(",");
        for (String val : values) {
            if (val.trim().equalsIgnoreCase(answer)) {
                return true;
            }
        }
        return false;
    }
}

