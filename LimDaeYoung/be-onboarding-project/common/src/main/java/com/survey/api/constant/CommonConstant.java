package com.survey.api.constant;

public class CommonConstant {
    public static final String MULTI_ITEM = "MULTI";
    public static final String SINGLE_ITEM = "SINGLE";
    public static final String SHORT_ITEM = "SHORT";
    public static final String LONG_ITEM = "LONG";

    public static final String ACTION_TYPE_CREATE = "CREATE";
    public static final String ACTION_TYPE_UPDATE = "UPDATE";
    public static final String ACTION_TYPE_DELETE = "DELETE";

    public static final String Y = "Y";
    public static final String N = "N";


    public static final int ERR_SUCCESS = 200;
    public static final int ERR_FAIL = 400;

    public static final int ERR_CONTENT_TYPE_ERROR = 4014;
    public static final int ERR_JSON_ERROR = 4013;// JSON파싱 오류
    public static final int ERR_DB_DATA_ERROR = 4012; //데이터처리 오류
    public static final int ERR_DATA_NOT_FOUND = 4201; //필수값 누락
    public static final int ERR_DB_DATA_ID_ERROR = 4202; // 존재하지 않는 아이디

    public static final String ERR_MSG_SUCCESS = "성공";

    public static final String ERR_MSG_CONTENT_TYPE_ERROR = "Content-Type은 application/json;charset=UTF-8 를 사용해야 합니다.";
    public static final String ERR_MSG_JSON_ERROR = "JSON파싱 오류가 발생하였습니다.";
    public static final String ERR_MSG_DB_DATA_ERROR = "데이터처리 오류가 발생하였습니다.";
    public static final String ERR_MSG_DATA_NOT_FOUND = "데이터 없음: ";
    public static final String ERR_MSG_DATA_ID_NOT_FOUND = "잘못된 ID: ";

    public static final String ERROR_TYPE_REQUEST_INVALID = "REQUEST_INVALID";
}
