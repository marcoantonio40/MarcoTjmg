package com.project.marco.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class RestatementEntity {

    @EmbeddedId
    private RestatementId restatementId;

    private double factorMes;

}
