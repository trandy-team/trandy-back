package org.trandy.trandy_server.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "[ADMIN] 카테고리 생성", description = "신규 카테고리 생성")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping("/createCategory")
    @Secured("ADMIN")
    public ResponseEntity<ResponseDto> createCategory(@Parameter(
                                                        description = "카테고리명",
                                                        example = "아이돌") @RequestParam String categoryName){

        return ResponseEntity.ok(categoryService.createCategory(categoryName));
    }

    @Operation(summary = "[메인 화면] 전체 카테고리 조회", description = "카테고리 List 전체 조회")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @GetMapping("retrieveCategories")
    public ResponseEntity<ResponseDto> retrieveCategories(@Parameter(
                                                            description = "카테고리명. null이면 전체 리스트 반환",
                                                            example = "아이돌")@Nullable @RequestParam String categoryName){

        return ResponseEntity.ok(categoryService.retrieveCategories(categoryName));
    }

    @Operation(summary = "[ADMIN] 카테고리 수정", description = "카테고리 명 수정")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PutMapping("/updateCategory")
    @Secured("ADMIN")
    public ResponseEntity<ResponseDto> updateCategory(@Parameter(description = "기존 카테고리 id",
                                                        example = "3") @RequestParam long categoryId,
                                                      @Parameter(description = "변경할 카테고리명",
                                                        example = "아이도루") @RequestParam String categoryName){

        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryName));
    }

    @Operation(summary = "[ADMIN] 카테고리 삭제", description = "카테고리 삭제")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping("/deleteCategory")
    @Secured("ADMIN")
    public ResponseEntity<ResponseDto> deleteCategory(@Parameter(description = "기존 카테고리 id",
                                                        example = "3") @RequestParam long categoryId){

        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
}
