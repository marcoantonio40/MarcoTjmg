package com.project.marco.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class SavingsIndexEntity implements Serializable {

    @EmbeddedId
    private SavingsIndexId savingsIndexId;

    private Double value;
}
