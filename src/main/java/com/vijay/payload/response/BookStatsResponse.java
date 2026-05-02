// ✅ Create new file: payload/response/BookStatsResponse.java
package com.vijay.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStatsResponse {
    private long totalActiveBooks;
    private long totalAvailableBooks;
}