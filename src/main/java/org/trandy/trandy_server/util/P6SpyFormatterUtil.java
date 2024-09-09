package org.trandy.trandy_server.util;

import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class P6SpyFormatterUtil implements MessageFormattingStrategy {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(category).append(" ").append(elapsed).append("ms").append("\n");
        sb.append("====================================================================");
        if (StringUtils.hasText(sql)) {
            sb.append(highlight(format(sql))).append("\n");
        }
        sb.append("======================================================================").append("\n");
        return sb.toString();
    }

    private String format(String sql) {
        if (isDDL(sql)) {
            return FormatStyle.DDL.getFormatter().format(sql);
        } else if (isBasic(sql)) {
            return FormatStyle.BASIC.getFormatter().format(sql);
        }
        return sql;
    }

    private String highlight(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

    private boolean isDDL(String sql) {
        return sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment");
    }

    private boolean isBasic(String sql) {
        return sql.startsWith("select") || sql.startsWith("insert") || sql.startsWith("update") || sql.startsWith("delete");
    }
}

