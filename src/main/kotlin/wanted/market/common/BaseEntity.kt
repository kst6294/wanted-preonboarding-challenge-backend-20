package wanted.market.common

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    var createdDate: LocalDateTime? = LocalDateTime.now() // entity가 아님 -> final 변수이기에 초기화 필요

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = LocalDateTime.now()
}