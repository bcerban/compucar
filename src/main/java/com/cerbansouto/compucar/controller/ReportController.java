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

import javax.persistence.EntityNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
            processServiceRequest(model, request);
        }

        return "services/index";
    }

    @RequestMapping(value = "/services/pdf", method = RequestMethod.POST)
    public String generatePdf(Model model, Request request) throws UnauthorizedRequestException {
        if (StringUtils.hasText(request.getParameter("month"))) {
            processServiceRequest(model, request);
            return "pdfReportView";
        }

        return "redirect:/reports/services";
    }

    private void processServiceRequest(Model model, Request request) {
        int month = Integer.parseInt(request.getParameter("month"));
        List<Service> services = serviceService.getForMonth(month);
        model.addAttribute("services", services);
        model.addAttribute("month", month);
    }

    @RequestMapping(value = "/readers", method = RequestMethod.GET)
    public String readerReports() throws UnauthorizedRequestException {
        return "readers/report";
    }

    @RequestMapping(value = "/readers", method = RequestMethod.POST)
    public String queryReaders(Model model, Request request) throws UnauthorizedRequestException {
        if (StringUtils.hasText(request.getParameter("readerCode"))) {

            try {
                Reader reader = readerService.fetch(request.getParameter("readerCode"));
                processReaderRequest(model, request, reader);
            } catch (EntityNotFoundException | ParseException e) {
                log.error(e.getMessage(), e);
            }
        }

        return "readers/report";
    }

    @RequestMapping(value = "/readers/pdf", method = RequestMethod.POST)
    public String generatePdfByReader(Model model, Request request) throws UnauthorizedRequestException {
        if (StringUtils.hasText(request.getParameter("readerCode"))) {

            try {
                Reader reader = readerService.fetch(request.getParameter("readerCode"));
                processReaderRequest(model, request, reader);
                return "pdfReportView";
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
        }

        return "redirect:/reports/readers";
    }

    private void processReaderRequest(Model model, Request request, Reader reader) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date from = StringUtils.hasText(request.getParameter("from"))
                ? format.parse(request.getParameter("from"))
                : getFirstDayOfCurrentMonth();

        Date to = StringUtils.hasText(request.getParameter("to"))
                ? format.parse(request.getParameter("to"))
                : getLastDayOfCurrentMonth();

        List<Service> services = serviceService.getForRangeAndReader(reader, from, to);
        model.addAttribute("services", services);
        model.addAttribute("readerCode", reader.getCode());
        model.addAttribute("from", format.format(from));
        model.addAttribute("to", format.format(to));
        model.addAttribute("serviceCount", services.size());
        model.addAttribute("minutes", services.stream().map(s -> s.getServiceTime())
                .collect(Collectors.summingInt(Integer::intValue)));
        model.addAttribute("earnings", services.stream().map(s -> (double)s.getCost())
                .collect(Collectors.summingDouble(Double::doubleValue)));
    }

    private Date getFirstDayOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        return java.sql.Date.valueOf(today.withDayOfMonth(1));
    }

    private Date getLastDayOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        return java.sql.Date.valueOf(today.withDayOfMonth(today.lengthOfMonth()));
    }
}

