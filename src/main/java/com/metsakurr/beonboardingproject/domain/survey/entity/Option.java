package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity(name = "option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @JsonIgnore
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_idx", referencedColumnName = "idx", nullable = true)
    private Question question;

    @Column(nullable = false)
    private String name;

    public static Option of(String name) {
        return new Option(name);
    }

    @Builder
    public Option(
            String name
    ) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Option option = (Option) obj;

        return Objects.equals(name, option.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx, name);
    }
}
