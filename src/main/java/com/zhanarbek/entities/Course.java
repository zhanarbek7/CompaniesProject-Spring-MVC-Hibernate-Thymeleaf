package com.zhanarbek.entities;

import javax.persistence.*;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 25/2/22
 */
@Entity
@Table(name = "courses")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "duration_in_month")
    private String durationInMonth;

}
