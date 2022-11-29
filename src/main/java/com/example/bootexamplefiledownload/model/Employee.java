package com.example.bootexamplefiledownload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    private Long id;
    private String name;
    private String email;
    private int age;

}
