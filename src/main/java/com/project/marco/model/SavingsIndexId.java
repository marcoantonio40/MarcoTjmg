package com.project.marco.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class SavingsIndexId implements Serializable {

    @NotNull
    private int mes;

    @NotNull
    private int ano;
}
