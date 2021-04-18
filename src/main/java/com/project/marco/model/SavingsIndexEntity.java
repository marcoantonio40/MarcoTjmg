package com.project.marco.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class SavingsIndexEntity {

    @EmbeddedId
    private SavingsIndexId savingsIndexId;

    private Double value;
}
