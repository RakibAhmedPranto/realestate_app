package com.rea.app.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageModel<T> {
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
    private boolean isLast;
    private boolean isFirst;
    private boolean isEmpty;
    private int pageNumber;
    private long perPage;
    private List<T> data;

    public PageModel(Page<T> page) {
        System.out.println("Page Model: " + page);
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.numberOfElements = page.getNumberOfElements();
        this.isLast = page.isLast();
        this.isEmpty = page.isEmpty();
        this.data = page.getContent();
        this.isFirst = page.isFirst();
        this.pageNumber = page.getNumber();
        this.perPage = page.getSize();
    }
}
