package ic2.onboarding.survey;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import ic2.onboarding.survey.entity.Survey;
import ic2.onboarding.survey.entity.SurveyItem;
import ic2.onboarding.survey.global.ItemInputType;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.arbitraries.LongArbitrary;
import net.jqwik.api.arbitraries.StringArbitrary;

import static com.navercorp.fixturemonkey.api.instantiator.Instantiator.*;
import static ic2.onboarding.survey.global.BizConstants.*;
import static net.jqwik.api.Arbitraries.longs;
import static net.jqwik.api.Arbitraries.strings;

public final class FixtureUtil {

    private static final FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();

    private static final FixtureMonkey DEFAULT_FIXTURE_MONKEY = FixtureMonkey.create();

    private static final char[] KOREAN_CHARS = {
            '가', '나', '다', '라', '마', '바', '사', '아', '자', '차', '카', '타', '파', '하'
    };

    private static final LongArbitrary ID_ARBITRARY = longs().greaterOrEqual(1L);

    private static final StringArbitrary KOREAN_STRING_ARBITRARY = strings().withChars(KOREAN_CHARS);

    private static final StringArbitrary NAME_ARBITRARY = KOREAN_STRING_ARBITRARY
            .ofMinLength(MIN_NAME_LENGTH)
            .ofMaxLength(MAX_NAME_LENGTH);

    private static final StringArbitrary DESCRIPTION_ARBITRARY = KOREAN_STRING_ARBITRARY
            .ofMinLength(MIN_DESCRIPTION_LENGTH)
            .ofMaxLength(MAX_DESCRIPTION_LENGTH);

    private static final Arbitrary<ItemInputType> INPUT_TYPE_ARBITRARY = Arbitraries.of(ItemInputType.values());


    private static final ArbitraryBuilder<Survey> SURVEY_ARBITRARY_BUILDER =
            FIXTURE_MONKEY.giveMeBuilder(Survey.class)
                    .instantiate(
                            constructor()
                                    .parameter(String.class, "name")
                                    .parameter(String.class, "description")
                    )
                    .set("name", NAME_ARBITRARY)
                    .set("description", DESCRIPTION_ARBITRARY);

    private static final ArbitraryBuilder<SurveyItem> SURVEY_ITEM_ARBITRARY_BUILDER =
            FIXTURE_MONKEY.giveMeBuilder(SurveyItem.class)
                    .instantiate(
                            constructor()
                                    .parameter(Long.class, "id")
                                    .parameter(Survey.class, "survey")
                                    .parameter(String.class, "name")
                                    .parameter(String.class, "description")
                                    .parameter(ItemInputType.class, "inputType")
                                    .parameter(Boolean.class, "required")
                                    .parameter(String.class, "choices")
                    )
                    .set("id", ID_ARBITRARY)
                    .set("survey", null)
                    .set("name", NAME_ARBITRARY)
                    .set("description", DESCRIPTION_ARBITRARY)
                    .set("inputType", INPUT_TYPE_ARBITRARY)
                    .set("required", DEFAULT_FIXTURE_MONKEY.giveMeArbitrary(Boolean.class))
                    .set("choices", null);

    public static FixtureMonkey getDefaultFixtureMonkey() {

        return DEFAULT_FIXTURE_MONKEY;
    }

    public static StringArbitrary getKoreanStringArbitrary() {

        return KOREAN_STRING_ARBITRARY;
    }

    public static ArbitraryBuilder<Survey> surveyArbitraryBuilder() {

        return SURVEY_ARBITRARY_BUILDER;
    }

    public static ArbitraryBuilder<SurveyItem> surveyItemArbitraryBuilder() {

        return SURVEY_ITEM_ARBITRARY_BUILDER;
    }

}
