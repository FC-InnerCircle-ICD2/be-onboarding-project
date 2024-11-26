package ic2.onboarding.survey.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyItem> items = new ArrayList<>();


    public Survey(String name, String description) {

        this.name = name;
        this.description = description;
    }


    public void addItem(SurveyItem item) {
        items.add(item);
        item.setSurvey(this);
    }


    public void addAllItems(List<SurveyItem> items) {
        if (this.items.addAll(items)) {
            items.forEach(item -> item.setSurvey(this));
        }
    }


    public void removeItem(SurveyItem item) {
        if (items.remove(item)) {
            item.setSurvey(null);
        }
    }

}
