package com.innercircle.surveryproject.modules.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * 응답 시간
     */
    @CreatedDate
    private LocalDateTime createTime;

    /**
     * 수정 시간
     */
    @LastModifiedDate
    private LocalDateTime updateTime;

}
