package org.querypie.bookmanagement.common.support.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonInclude
@Getter
public class PageResponse<T> {
    @JsonProperty("content")
    private List<T> content;
    @JsonProperty("pagination")
    private PaginationInfo pagination;

    public PageResponse() {
    }


    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.pagination = new PaginationInfo(page);
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(page);
    }

    public static class PaginationInfo {
        @JsonProperty("currentPage")
        private int currentPage;
        @JsonProperty("pageSize")
        private int pageSize;
        @JsonProperty("totalElements")
        private long totalElements;
        @JsonProperty("totalPages")
        private int totalPages;
        @JsonProperty("last")
        private boolean last;

        public PaginationInfo() {
        }

        public PaginationInfo(Page<?> page) {
            this.currentPage = page.getNumber();
            this.pageSize = page.getSize();
            this.totalElements = page.getTotalElements();
            this.totalPages = page.getTotalPages();
            this.last = page.isLast();
        }
    }
}
