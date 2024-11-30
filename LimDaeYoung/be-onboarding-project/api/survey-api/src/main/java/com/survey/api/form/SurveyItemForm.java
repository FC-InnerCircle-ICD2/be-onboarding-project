package com.survey.api.form;

import com.survey.api.base.SurveyBaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyItemForm extends SurveyBaseVo {
    private static final long serialVersionUID = -1909026336916149499L;
    private String itemName;
    private String description;
    private String itemType;
    private boolean required;

    private List<SurveyOptionForm> optionList;

}


/*
 * 아키텍처 설계
 *  1. 방향은 두 가지 밖에 없음.
 *  2. 가로로 찢을거냐, 세로로 찢을거냐
 *
 * 배달의 민족 서비스
 *  ㄴ 배달 주문하기
 *  ㄴ 배달 주문 현황 조회하기
 *  ㄴ 비마트 주문하기
 *  ㄴ 비마트 주문 현황 조회하기
 *  ㄴ 배송 기사님 위치 확인하기
 *  ㄴ 상품권 선물하기
 *  ㄴ 상품권 등록하기
 *  ㄴ 상품권 사용내역 조회하기
 *  ㄴ 계정 정보 변경하기
 *  ㄴ 계정 신규 생성
 *  ㄴ .....
 * -----
 * 보통은 이런 서비스를 특정 논리적 단위로 묶음.
 *  : a.k.a. 도메인
 * -----
 * 도메인 단위로 얼마나 찢을 것이냐
 *  주문 도메인: order-api
 *  계정 도메인: account-api
 *  배송 도메인: delivery-api
 *  상품권(페이) 도메인: pay-api
 * 찢어놓은 도메인 안에서, 각 계층은 어떻게 구분 할 것이냐
 *  controller는 어디에?
 *  service는 어디에?
 *  공통 라이브러리나 유틸 함수는 어디에?
 *  외부 API 연동, 호출하는 부분은 어디에?
 *  ...
 * -> dto, entity, handler, repository, response, config, util, ...
 */
