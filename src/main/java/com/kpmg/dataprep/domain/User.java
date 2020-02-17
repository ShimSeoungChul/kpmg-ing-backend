package com.kpmg.dataprep.domain;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
public class User {

    @Id
    String id;


}
