package com.project.marco.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Data
@Embeddable
public class SavingsIndexId {

    @NotNull
    private String mes;

    @NotNull
    private String ano;
}
