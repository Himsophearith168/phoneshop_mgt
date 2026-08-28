package com.example.PhoneShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO {
    private int pagesize;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private long numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;
}
