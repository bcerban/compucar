package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/readers")
public class ReaderJspController {

    @Autowired
    private ReaderService readerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listReaders(Model model) {
        List<Reader> readers = readerService.list();
        model.addAttribute("readers", readers);
        return "readers/index";
    }
}
