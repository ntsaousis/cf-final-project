package gr.aueb.cf.tsaousisfinal.core.filters;

import org.springframework.data.domain.Page;

import java.util.List;

public class Paginated<T> {

    List<T> data;
    long totalElements;
    int totalPages;
    int numberOfElements;
    int currentPage;
    int pageSize;

    public Paginated(Page<T> page) {
        this.data = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.numberOfElements = page.getNumberOfElements();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
    }
}
