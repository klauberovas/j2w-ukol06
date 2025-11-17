package cz.czechitas.java2webapps.ukol6.controller;

import cz.czechitas.java2webapps.ukol6.entity.Vizitka;
import cz.czechitas.java2webapps.ukol6.repository.VizitkaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class VizitkaController {
    private final VizitkaRepository vizitkaRepository;

    public VizitkaController(VizitkaRepository vizitkaRepository) {
        this.vizitkaRepository = vizitkaRepository;
    }

    @InitBinder
    public void nullStringBinding(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/")
    public ModelAndView seznam() {
        ModelAndView mav = new ModelAndView("/seznam");
        mav.addObject("seznam", vizitkaRepository.findAll());
        return mav;
    }

    @GetMapping("/{id}")
    public Object detail(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("vizitka");
        var detail = vizitkaRepository.findById(id).orElse(null);
        if(detail == null) {
            return ResponseEntity.notFound().build();
        }else {
            mav.addObject("vizitka", detail);
            return mav;
        }
    }

    @GetMapping("/nova")
    public ModelAndView nova() {
       ModelAndView mav= new ModelAndView("formular");
       mav.addObject("vizitka", new Vizitka());
       return mav;
    }

    @PostMapping("/nova")
    public ModelAndView pridat(@ModelAttribute("vizitka") @Valid Vizitka vizitka, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("formular");
            return mav;
        }

        vizitkaRepository.save(vizitka);
        return new ModelAndView("redirect:/");
    }
}
