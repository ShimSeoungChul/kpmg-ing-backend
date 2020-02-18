package com.kpmg.dataprep.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    String id;
    int totThroughput;
}
