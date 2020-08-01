package com.itomelet.eduservice.entity.subject;

import lombok.Data;

import java.util.List;


@Data
public class Subject {
    private String id;
    private String title;
    private List<Subject> children;
}
