package com.project.marco.model;


import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class RestatementId implements Serializable {

    @NotNull
    private int yearDoc;

    @NotNull
    private int monthDoc;

    @NotNull
    private int yearFactor;

    @NotNull
    private int mes;
}
