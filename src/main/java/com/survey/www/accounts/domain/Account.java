package com.survey.www.accounts.domain;

import com.survey.www.accounts.code.RoleType;
import com.survey.www.commons.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 관리를 위한 도메인 객체")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Account extends BaseEntity {
    @Schema(title = "고유번호", description = "회원 고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "사용자 권한", description = "사용자 권한은 RESPONDER 로 자동 입력됩니다", example = "RESPONDER")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType = RoleType.RESPONDER;

    @Schema(title = "아이디", description = "사용자 아이디 (휴대폰번호)")
    @Column(nullable = false)
    private String phone;

    @Schema(title = "비밀번호", description = "사용자 비밀번호")
    @Column(nullable = false)
    private String userPw;
}
