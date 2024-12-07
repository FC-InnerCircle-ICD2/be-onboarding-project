package com.fastcampus.survey.entity;

import com.fastcampus.survey.util.constant.AnsType;
import com.fastcampus.survey.util.constant.Must;
import com.fastcampus.survey.util.converter.AnsTypeConverter;
import com.fastcampus.survey.util.converter.ChoiceConverter;
import com.fastcampus.survey.util.converter.MustConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE question SET is_deleted = true WHERE id = ?")
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String qName;
    private String qDescription;

    @Convert(converter = AnsTypeConverter.class)
    private AnsType qType;

    @Convert(converter = ChoiceConverter.class)
    private List<String> choices;

    @Convert(converter = MustConverter.class)
    private Must qMust;

    private boolean is_deleted = false;
}
