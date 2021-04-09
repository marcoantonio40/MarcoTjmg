package com.project.marco.model;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Embeddable
public class RestatementId  implements Serializable {

    @NotNull
    private int yearDoc;

    @NotNull
    private int monthDoc;

    @NotNull
    private int yearFactor;
}
