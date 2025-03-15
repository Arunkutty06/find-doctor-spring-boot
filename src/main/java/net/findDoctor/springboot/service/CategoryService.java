package net.findDoctor.springboot.service;

import net.findDoctor.springboot.model.Category;

public interface CategoryService {
    Category getCategoryById(Long id);
}
