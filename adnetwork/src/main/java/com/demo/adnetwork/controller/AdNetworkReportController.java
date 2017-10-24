package com.demo.adnetwork.controller;

import com.demo.adnetwork.entity.AdNetworkSource;
import com.demo.adnetwork.importer.DailyReportImporter;
import com.demo.adnetwork.importer.exception.MalformedReportException;
import com.demo.adnetwork.importer.exception.ReportAlreadyImportedException;
import com.demo.adnetwork.importer.exception.UrlResourceIoException;
import com.demo.adnetwork.service.ReportService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
public class AdNetworkReportController
{

    @Autowired
    private ReportService reportService;

    @Autowired
    private DailyReportImporter dailyReportImporter;

    @RequestMapping(value = "/importReport", method = RequestMethod.POST)
    public String importReport(@RequestParam final String adNetwork, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date, final RedirectAttributes redirectAttributes)
    {
        dailyReportImporter.importReport(adNetwork, date);

        redirectAttributes.addFlashAttribute("message", "Success!");

        return "redirect:/";
    }

    @GetMapping("/")
    public String listAdNetworkOptions(@Nonnull final Model model) throws IOException
    {

        Preconditions.checkNotNull(model, "Model must not be null!");

        model.addAttribute("adNetworkSources", reportService.findAdNetworkSources().stream().map(AdNetworkSource::getName).sorted().collect(Collectors.toList()));
        model.addAttribute("reportDate", LocalDate.now());

        return "importForm";
    }

    @ExceptionHandler(ReportAlreadyImportedException.class)
    public ResponseEntity<?> handleReportAlreadyImportedException(ReportAlreadyImportedException exc)
    {
        return new ResponseEntity<Object>("Report for: {" + exc.getAdNetwork() + ", " + exc.getDate() + "} is already imported!", HttpStatus.SEE_OTHER);
    }

    @ExceptionHandler(UrlResourceIoException.class)
    public ResponseEntity<?> handleUrlIoException(UrlResourceIoException exc)
    {
        return new ResponseEntity<Object>("Report not found at: " + exc.getUrl() + "!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MalformedReportException.class)
    public ResponseEntity<?> handleMalformedReportException(MalformedReportException exc)
    {
        return new ResponseEntity<Object>("Report is malformed! Message: " + exc.getMessage() + "!", HttpStatus.SEE_OTHER);
    }
}
