package wanted.market.api.common

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils
import java.util.*

@Configuration
class P6SpySqlFormatConfig : MessageFormattingStrategy {

    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = this.javaClass.name
    }

    override fun formatMessage(connectionId: Int, now: String, elapsed: Long, category: String, prepared: String, sql: String, url: String): String {
        var sql = sql
        sql = applySqlFormat(category, sql)

        return if (StringUtils.hasText(sql)) buildFormattedMessage(
            sql,
            connectionId,
            elapsed
        ) else ""
    }

    private fun applySqlFormat(category: String, sql: String): String {
        if (!StringUtils.hasText(sql) || Category.STATEMENT.name != category) {
            return sql
        }

        val trimmedSQL = sql.trim { it <= ' ' }.lowercase(Locale.ROOT)
        return if (trimmedSQL.startsWith(CREATE) || trimmedSQL.startsWith(
                ALTER
            ) || trimmedSQL.startsWith(COMMENT)
        ) {
            FormatStyle.DDL.formatter.format(sql)
        } else {
            FormatStyle.BASIC.formatter.format(sql)
        }
    }

    private fun buildFormattedMessage(
        formattedSql: String,
        connectionId: Int,
        elapsed: Long
    ): String {
        return String.format("%s%s", formattedSql, formatConnectionInfo(connectionId, elapsed))
    }

    private fun formatConnectionInfo(connectionId: Int, elapsed: Long): String {
        return String.format(
            """


                ${'\t'}Connection ID: %d | Execution Time: %d ms

                ===========================================================================
                
                """.trimIndent(), connectionId, elapsed
        )
    }

    companion object {
        private const val CREATE = "create"
        private const val ALTER = "alter"
        private const val COMMENT = "comment"
    }
}
