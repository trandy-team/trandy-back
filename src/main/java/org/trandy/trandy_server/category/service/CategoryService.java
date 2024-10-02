package org.trandy.trandy_server.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.trandy.trandy_server.category.domain.Category;
import org.trandy.trandy_server.category.repository.CategoryRepository;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.exception.CustomException;
import org.trandy.trandy_server.exception.ExceptionStatus;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseDto<String> createCategory(String categoryName) {
        categoryRepository.findByCategoryName(categoryName).ifPresent(category -> {
            throw new CustomException(ExceptionStatus.CategoryNameIsDuplicatedException);
        });

        categoryRepository.save(Category.builder().categoryName(categoryName).build());

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    public ResponseDto retrieveCategories(String categoryName) {
        if(categoryName != null && !categoryName.isEmpty()){
            Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(
                    () -> new CustomException(ExceptionStatus.DataNotFoundException));
            return ResponseDto.success(category);
        } else {
            return ResponseDto.success(categoryRepository.findAll());
        }
    }

    public ResponseDto updateCategory(long categoryId, String categoryName) {
        categoryRepository.findByCategoryName(categoryName).ifPresent(category -> {
            throw new CustomException(ExceptionStatus.CategoryNameIsDuplicatedException);
        });

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CustomException(ExceptionStatus.DataNotFoundException));

        category.update(categoryName);
        categoryRepository.save(category);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    public ResponseDto deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CustomException(ExceptionStatus.DataNotFoundException));

        // 카테고리 Deleted Flag 수정 (false -> true)
        category.delete(category.getDeleted());

        categoryRepository.save(category);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    public Category retrieveCategory(long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new CustomException(ExceptionStatus.DataNotFoundException));
    }
}
