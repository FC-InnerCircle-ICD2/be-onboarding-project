package com.onboarding.response.repository;

import com.onboarding.response.entity.Answer;
import com.onboarding.response.entity.Response;
import com.onboarding.survey.entity.Survey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ResponseRepositoryCustomImpl implements ResponseRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Response> findResponsesByAdvancedFilters(Survey survey, String questionTitle, String responseValue) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Response> query = cb.createQuery(Response.class);
    Root<Response> root = query.from(Response.class);
    Join<Response, Answer> answersJoin = root.join("answers");

    // 조건 추가
    List<Predicate> predicates = new ArrayList<>();
    predicates.add(cb.equal(root.get("survey"), survey)); // survey 조건

    if (questionTitle != null && !questionTitle.isEmpty()) {
      predicates.add(cb.equal(answersJoin.get("questionSnapshot").get("title"), questionTitle));
    }
    if (responseValue != null && !responseValue.isEmpty()) {
      predicates.add(cb.equal(answersJoin.get("responseValue"), responseValue));
    }

    query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

    return entityManager.createQuery(query).getResultList();
  }

}
