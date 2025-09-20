package com.expensemanager.configs;


import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpyPrettySqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {

        if (sql == null || sql.trim().isEmpty()) {
            return "";
        }

        if ("statement".equalsIgnoreCase(category)) {
            return String.format("🔵 SQL | %d ms | %s", elapsed, sql.trim().replaceAll("\\s+", " "));
        }

        if ("result".equalsIgnoreCase(category)) {
            return String.format("🟢 RESULT | %s", sql.trim());
        }

        return String.format("⚪ %s | %s", category.toUpperCase(), sql.trim());
    }
}