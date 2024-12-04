package com.innercicle.adapter.out.persistence.v1;

import jakarta.persistence.Embeddable;

@Embeddable
public class ItemOptionEntity {

    private String option;
    private boolean checked;

}
