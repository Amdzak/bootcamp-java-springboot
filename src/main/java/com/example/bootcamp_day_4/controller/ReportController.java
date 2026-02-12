package com.example.bootcamp_day_4.controller;

import com.example.bootcamp_day_4.dto.SalesReportResponse;
import com.example.bootcamp_day_4.dto.WebResponse;
import com.example.bootcamp_day_4.entity.StockLog;
import com.example.bootcamp_day_4.entity.TransactionHistory;
import com.example.bootcamp_day_4.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(path = "/sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<SalesReportResponse>> getSalesReport(@RequestParam(name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,  @RequestParam(name = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Request Sales Report from {} to {}", startDate, endDate);

        List<SalesReportResponse> report = reportService.getSalesReport(startDate, endDate);

        return WebResponse.<List<SalesReportResponse>>builder()
                .message("Success get sales report")
                .data(report)
                .build();
    }

    @GetMapping(path = "/stock-logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<StockLog>> getStockLogReport( @RequestParam(name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,  @RequestParam(name = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Request Stock Log Report from {} to {}", startDate, endDate);
        List<StockLog> logs = reportService.getStockLogReport(startDate, endDate);

        return WebResponse.<List<StockLog>>builder()
                .message("Success get stock log report")
                .data(logs)
                .build();
    }
}