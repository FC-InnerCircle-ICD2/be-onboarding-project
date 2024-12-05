package org.icd.surveycore.domain.support

import jakarta.persistence.EntityListeners
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime


@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    val createdAt: OffsetDateTime = OffsetDateTime.now()

    @LastModifiedDate
    var updatedAt: OffsetDateTime? = null
}
