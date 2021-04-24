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

    public double january;
    public double february;
    public double march;
    public double april;
    public double may;
    public double june;
    public double july;
    public double august;
    public double september;
    public double october;
    public double november;
    public double december;



}
