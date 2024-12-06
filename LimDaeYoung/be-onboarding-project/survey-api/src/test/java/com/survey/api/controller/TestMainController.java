package com.survey.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.api.constant.CommonConstant;
import com.survey.api.form.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestMainController {
    @Autowired
    private MockMvc mockMvc;  // MockMvc 자동 주입

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Test
    public void save() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        SurveyForm surveyForm = new SurveyForm();
        surveyForm.setName("설문조사");
        surveyForm.setDescription("테스트 설문조사 입니다.");

        List<SurveyItemForm> itemList = new ArrayList<>();
        SurveyItemForm itemForm = new SurveyItemForm();
        itemForm.setItemName("선호도");
        itemForm.setDescription("어떤걸 좋아하나요?");
        itemForm.setItemType("MULTI");
        itemForm.setRequired(true);

        List<SurveyOptionForm> optionList = new ArrayList<>();
        SurveyOptionForm optionForm1 = new SurveyOptionForm();
        optionForm1.setOptionName("먹거리");
        optionForm1.setOptionOrder(1);
        optionList.add(optionForm1);

        SurveyOptionForm optionForm2 = new SurveyOptionForm();
        optionForm2.setOptionName("장난감");
        optionForm2.setOptionOrder(2);
        optionList.add(optionForm2);

        itemForm.setOptionList(optionList);
        itemList.add(itemForm);
        surveyForm.setItems(itemList);

        mockMvc.perform(MockMvcRequestBuilders.post("/survey/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(surveyForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())  // 응답 상태 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(CommonConstant.ERR_SUCCESS))  // JSON 응답 내용 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(CommonConstant.ERR_MSG_SUCCESS));  // JSON 응답 내용 확인

        SurveyResponseForm responseForm = new SurveyResponseForm();

        List<SurveyResponseItemForm> responseItemList = new ArrayList<>();
        SurveyResponseItemForm responseItemForm = new SurveyResponseItemForm();
        responseItemForm.setId(1L);
        responseItemForm.setItemType("MULTI");

        String[] str = new String[2];
        str[0] ="1";
        str[1] ="2";
        responseItemForm.setSelectOptions(str);

        responseItemList.add(responseItemForm);
        responseForm.setItems(responseItemList);

        mockMvc.perform(MockMvcRequestBuilders.put("/survey/response/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(responseForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())  // 응답 상태 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(CommonConstant.ERR_SUCCESS))  // JSON 응답 내용 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(CommonConstant.ERR_MSG_SUCCESS));  // JSON 응답 내용 확인

        SurveyUpdateForm updateForm = new SurveyUpdateForm();
        updateForm.setName("설문조사 수정");
        updateForm.setDescription("수정 테스트 설문조사 입니다.");

        List<SurveyUpdateItemForm> updateItemList = new ArrayList<>();
        SurveyUpdateItemForm updateItemForm = new SurveyUpdateItemForm();
        updateItemForm.setId(1L);
        updateItemForm.setItemType("MULTI");
        updateItemForm.setItemName("선호도 수정");
        updateItemForm.setDescription("어떤걸 좋아하나요? 수정");
        updateItemForm.setRequired(true);
        updateItemForm.setActionType("UPDATE");

        List<SurveyUpdateOptionForm> updateOptionList = new ArrayList<>();
        SurveyUpdateOptionForm updateOptionForm1 = new SurveyUpdateOptionForm();
        updateOptionForm1.setId(1L);
        updateOptionForm1.setOptionName("먹거리 수정");
        updateOptionForm1.setOptionOrder(1);
        updateOptionForm1.setActionType("UPDATE");
        updateOptionList.add(updateOptionForm1);

        SurveyUpdateOptionForm updateOptionForm2 = new SurveyUpdateOptionForm();
        updateOptionForm2.setId(2L);
        updateOptionForm2.setOptionName("장난감 수정");
        updateOptionForm2.setOptionOrder(2);
        updateOptionForm2.setActionType("UPDATE");
        updateOptionList.add(updateOptionForm2);

        updateItemForm.setOptionList(updateOptionList);
        updateItemList.add(updateItemForm);
        updateForm.setItems(updateItemList);

        mockMvc.perform(MockMvcRequestBuilders.put("/survey/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())  // 응답 상태 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(CommonConstant.ERR_SUCCESS))  // JSON 응답 내용 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(CommonConstant.ERR_MSG_SUCCESS));  // JSON 응답 내용 확인

        SurveyResponseForm responseForm2 = new SurveyResponseForm();

        List<SurveyResponseItemForm> responseItemList2 = new ArrayList<>();
        SurveyResponseItemForm responseItemForm2 = new SurveyResponseItemForm();
        responseItemForm2.setId(1L);
        responseItemForm2.setItemType("MULTI");

        String[] str2 = new String[2];
        str2[0] ="1";
        str2[1] ="2";
        responseItemForm2.setSelectOptions(str2);

        responseItemList2.add(responseItemForm2);
        responseForm2.setItems(responseItemList2);

        mockMvc.perform(MockMvcRequestBuilders.put("/survey/response/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(responseForm2)))
                .andExpect(MockMvcResultMatchers.status().isOk())  // 응답 상태 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(CommonConstant.ERR_SUCCESS))  // JSON 응답 내용 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(CommonConstant.ERR_MSG_SUCCESS));  // JSON 응답 내용 확인

        SurveySelectForm selectForm = new SurveySelectForm();
        selectForm.setId(1L);
        selectForm.setPageNumber(0);
        selectForm.setSearchParam("");

        mockMvc.perform(MockMvcRequestBuilders.put("/survey/response/select/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(selectForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())  // 응답 상태 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(CommonConstant.ERR_SUCCESS))  // JSON 응답 내용 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusMessage").value(CommonConstant.ERR_MSG_SUCCESS));  // JSON 응답 내용 확인


    }
}

