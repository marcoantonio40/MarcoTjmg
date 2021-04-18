package com.project.marco.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class SavingsIndexId implements Serializable {

    @NotNull
    private String mes;

    @NotNull
    private String ano;
}
