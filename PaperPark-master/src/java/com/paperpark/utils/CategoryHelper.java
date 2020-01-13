/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.utils;

import com.paperpark.categories_mapping.CategoryMapping;
import com.paperpark.categories_mapping.CategoryMappings;
import java.util.UUID;
import javax.servlet.ServletContext;

/**
 *
 * @author NhanTT
 */
public class CategoryHelper {

    private CategoryMappings mappings;
    private ServletContext context;

    public CategoryHelper(ServletContext context) {
        this.context = context;
        this.mappings = (CategoryMappings) context.getAttribute("CATEGORY_MAPPINGS");
    }
    
    public String getRealCategoryName(String altName) {
        if (mappings == null || mappings.getCategoryMapping() == null) {
            return null;
        }
        
        for (CategoryMapping categoryMapping : mappings.getCategoryMapping()) {
            if (categoryMapping.getMapping().contains(altName)) {
                return categoryMapping.getName();
            }
        }
        
        return null;
    }
    
    public synchronized static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
