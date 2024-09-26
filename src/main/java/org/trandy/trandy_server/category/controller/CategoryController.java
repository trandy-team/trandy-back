package org.trandy.trandy_server.category.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.trandy.trandy_server.category.service.CategoryService;
import org.trandy.trandy_server.common.ResponseDto;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/createCategory")
    @Secured("ADMIN")
    public ResponseEntity<ResponseDto> createCategory(@Parameter(
                                                        description = "카테고리명",
                                                        example = "아이돌") @RequestParam String categoryName){

        return ResponseEntity.ok(categoryService.createCategory(categoryName));
    }

    @GetMapping("retrieveCategories")
    public ResponseEntity<ResponseDto> retrieveCategories(@Parameter(
                                                            description = "카테고리명. null이면 전체 리스트 반환",
                                                            example = "아이돌")@Nullable @RequestParam String categoryName){

        return ResponseEntity.ok(categoryService.retrieveCategories(categoryName));
    }

    @PutMapping("/updateCategory")
    @Secured("ADMIN")
    public ResponseEntity<ResponseDto> updateCategory(@Parameter(description = "기존 카테고리 id",
                                                        example = "3") @RequestParam long categoryId,
                                                      @Parameter(description = "변경할 카테고리명",
                                                        example = "아이도루") @RequestParam String categoryName){

        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryName));
    }

    @PostMapping("/deleteCategory")
    @Secured("ADMIN")
    public ResponseEntity<ResponseDto> deleteCategory(@Parameter(description = "기존 카테고리 id",
                                                        example = "3") @RequestParam long categoryId){

        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
}
