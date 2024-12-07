package com.survey.www.commons.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
  @Schema(title = "등록일", description = "최초 등록일")
  @CreatedDate
  @Column(name="created_at", updatable = false)
  private LocalDateTime createdAt;

  @Schema(title = "수정일", description = "최근 수정일")
  @LastModifiedDate
  @Column(name="updated_at")
  private LocalDateTime updatedAt;
}
