package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.api.ServiceService;
import com.cerbansouto.compucar.api.UnauthorizedRequestException;
import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.model.Service;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ReaderService readerService;

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String serviceReports() throws UnauthorizedRequestException {
        return "services/index";
    }

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public String queryServices(Model model, Request request) throws UnauthorizedRequestException {
        if (StringUtils.hasText(request.getParameter("month"))) {
            log.info("Requesting services for month {}", request.getParameter("month"));
            int month = Integer.parseInt(request.getParameter("month"));
            List<Service> services = serviceService.getForMonth(month);
            model.addAttribute("services", services);
            model.addAttribute("month", month);
        }

        return "services/index";
    }

    @RequestMapping(value = "/services/pdf", method = RequestMethod.POST)
    public String generatePdf(Model model, Request request) throws UnauthorizedRequestException {
        if (StringUtils.hasText(request.getParameter("month"))) {
            int month = Integer.parseInt(request.getParameter("month"));
            List<Service> services = serviceService.getForMonth(month);
            model.addAttribute("services", services);
            return "pdfReportView";
        }

        return "redirect:/reports/services";
    }

    @RequestMapping(value = "/readers", method = RequestMethod.GET)
    public String readerReports() throws UnauthorizedRequestException {
        return "readers/report";
    }

    @RequestMapping(value = "/readers", method = RequestMethod.POST)
    public String queryReaders(Model model, Request request) throws UnauthorizedRequestException {
        if (StringUtils.hasText(request.getParameter("readerCode"))) {
            Reader reader = readerService.fetch(request.getParameter("readerCode"));

            try {
                // TODO: Read real dates
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                List<Service> services = serviceService.getForRangeAndReader(reader, format.parse("2019-11-01"), format.parse("2019-11-30"));
                model.addAttribute("services", services);
                model.addAttribute("readerCode", reader.getCode());
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
        }

        return "readers/report";
    }

    @RequestMapping(value = "/readers/pdf", method = RequestMethod.POST)
    public String generatePdfByReader(Model model, Request request) throws UnauthorizedRequestException {
        if (StringUtils.hasText(request.getParameter("readerCode"))) {
            Reader reader = readerService.fetch(request.getParameter("readerCode"));

            try {
                // TODO: Read real dates
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                List<Service> services = serviceService.getForRangeAndReader(reader, format.parse("2019-11-01"), format.parse("2019-11-30"));
                model.addAttribute("services", services);
                model.addAttribute("readerCode", reader.getCode());
                return "pdfReportView";
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
        }

        return "redirect:/reports/readers";
    }
}

