package com.zhanarbek.controllers;

import com.zhanarbek.entities.Company;
import com.zhanarbek.entities.Course;
import com.zhanarbek.service.interfaces.CompanyService;
import com.zhanarbek.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 26/2/22
 */
@Controller
public class CoursesController {
    private final CompanyService companyService;
    private final CourseService coursesService;
    @Autowired
    public CoursesController(CompanyService companyService, CourseService coursesService) {
        this.companyService = companyService;
        this.coursesService = coursesService;
    }

    //Sorting courses by id
    @RequestMapping("/getCourses")
    public String getAllCourses(@RequestParam("companyId") Long id, Model model){
        List<Course> courses = companyService.getCompanyById(id).getCourses();
        Comparator<Course> comparator = new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return (int) (o1.getId()-o2.getId());
            }
        };
        Collections.sort(courses, comparator); // use the comparator as much as u want
        model.addAttribute("courses",courses);
        return "courses/courses";
    }

    @RequestMapping("/addCourse")
    public String addCourse(Model model){
        model.addAttribute("course",new Course());
        return "courses/addCourse";
    }
    @RequestMapping("saveCourse")
    public String saveCourse(@RequestParam("companyId") Long id,@ModelAttribute("course") Course course){
        companyService.getCompanyById(id).getCourses().add(course);
        course.setCompany(companyService.getCompanyById(id));
        coursesService.addCourse(course);
        return "redirect:/getCourses?companyId="+id;
    }
    @RequestMapping("/updateCourse")
    public String updateCourse(@RequestParam("courseId") Long id, Model model){
        Course course =  coursesService.getCourseById(id);
        model.addAttribute("course", course);
        return "courses/updateCourse";
    }
    @RequestMapping("/saveUpdateCourse")
    public String saveUpdateCourse(@RequestParam("companyId") Long id,@ModelAttribute("course") Course course){
        course.setCompany(companyService.getCompanyById(id));
        coursesService.updateCourse(course);
        return "redirect:/getCourses?companyId="+id;
    }
    @RequestMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("courseId") Long id, @RequestParam("companyId") Long id2){
        coursesService.deleteCourse(coursesService.getCourseById(id));
        return "redirect:/getCourses?companyId="+id;
    }
}