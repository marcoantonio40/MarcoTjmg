package com.project.marco.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class RestatementEntity {

    @EmbeddedId
    private RestatementId restatementId;

    private double factorMes;

}
