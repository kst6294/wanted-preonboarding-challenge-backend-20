package com.api.jellomarket.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class JelloUtils {
    fun getCurrentDateTime(): String {
        return Instant.now().atZone(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}