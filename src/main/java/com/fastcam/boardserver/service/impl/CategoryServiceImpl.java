package com.fastcam.boardserver.service.impl;

import com.fastcam.boardserver.dto.CategoryDTO;
import com.fastcam.boardserver.mapper.CategoryMapper;
import com.fastcam.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if(accountId != null) {
            categoryMapper.register(categoryDTO);

        }else {
            log.error("register ERROR: {}", categoryDTO);
            throw new RuntimeException("register ERROR! 게시물 카테고리 등록 메서드를 확인해주세요" + categoryDTO);
        }
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if(categoryDTO != null) {
            categoryMapper.updateCategory(categoryDTO);
        } else {
            log.error("update ERROR: {}", categoryDTO);
            throw new RuntimeException("update ERROR! 게시물 카테고리 수정 메서드를 확인해주세요" + categoryDTO);
        }
    }

    @Override
    public void delete(int categoryId) {
        if(categoryId != 0) {
            categoryMapper.deleteCategory(categoryId);
        } else {
            log.error("update ERROR: {}", categoryId);
            throw new RuntimeException("update ERROR! 게시물 카테고리 수정 메서드를 확인해주세요" + categoryId);
        }
    }
}
