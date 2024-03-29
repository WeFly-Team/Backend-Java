package com.example.wefly_app.controller;

import com.example.wefly_app.request.transaction.MidtransResponseModel;
import com.example.wefly_app.request.transaction.TransactionSaveModel;
import com.example.wefly_app.service.CheckinService;
import com.example.wefly_app.service.ReportService;
import com.example.wefly_app.service.TransactionService;
import com.example.wefly_app.service.impl.ReportServiceImpl;
import com.example.wefly_app.util.exception.FileHandlingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/v1/transaction/")
@Slf4j
public class TransactionController {
    @Autowired
    public TransactionService transactionService;
    @Autowired
    public ReportService reportService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@Valid @RequestBody TransactionSaveModel request) throws IOException {
        return new ResponseEntity<>(transactionService.save(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<Map> delete(@PathVariable("id") Long request) {
        return new ResponseEntity<>(transactionService.delete(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = {"/getById/{id}", "/getById/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long request) {
        return new ResponseEntity<>(transactionService.getById(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = {"/list", "/list/"})
    public ResponseEntity<Map> getAll(@RequestParam(required = true, defaultValue = "0") int page,
                                       @RequestParam(required = true, defaultValue = "10") int size,
                                       @RequestParam(required = false, defaultValue = "id") String orderBy,
                                       @RequestParam(required = false, defaultValue = "ascending") String orderType,
                                       @RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate,
                                       @RequestParam(required = false) String status,
                                      @RequestParam(required = false) String paymentStatus,
                                      @RequestParam(required = false) String exceptionStatus) {
        return new ResponseEntity<>(transactionService.getAll(page, size, orderBy, orderType, startDate, endDate,
                paymentStatus, exceptionStatus), HttpStatus.OK);
    }

    @PostMapping(value = {"/midtransNotification", "/midtransNotification/"})
    public ResponseEntity<Map> midtransNotification(@RequestBody MidtransResponseModel request) {
        return new ResponseEntity<>(transactionService.midtransGetResponse(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = {"/getInvoice/{transactionId}", "/getInvoice/{transactionId}/"})
    public ResponseEntity<Resource> getInvoice(@PathVariable("transactionId") Long transactionId, HttpServletRequest request) {
        Resource resource = transactionService.getPaymentProof(transactionId);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException ex) {
            log.error("Could not determine file type", ex);
            throw new FileHandlingException("Could not determine file type", ex);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = {"/getETicket/{transactionId}", "/getETicket/{transactionId}/"})
    public ResponseEntity<Resource> getETicket(@PathVariable("transactionId") Long transactionId, HttpServletRequest request) {
        Resource resource = transactionService.getETicket(transactionId);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Could not determine file type", e);
            throw new FileHandlingException("Could not determine file type", e);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = {"/getReport", "/getReport/"})
    public ResponseEntity<Map> getReport(@RequestParam(required = true, defaultValue = "0") int page,
                                         @RequestParam(required = true, defaultValue = "10") int size,
                                         @RequestParam(required = false) String orderBy,
                                         @RequestParam(required = false) String orderType,
                                         @RequestParam(required = false) String startDate,
                                         @RequestParam(required = false) String endDate,
                                         @RequestParam(required = false, defaultValue = "monthly") String period) {
        return new ResponseEntity<>(reportService.getReport(page, size, orderBy, orderType, startDate, endDate, period), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = {"/getETicketResponse/{transactionId}", "/getETicketResponse/{transactionId}/"})
    public ResponseEntity<Map> getETicketResponseById(@PathVariable("transactionId") Long transactionId) {
        return new ResponseEntity<>(transactionService.getEticketResponse(transactionId), HttpStatus.OK);
    }


}
