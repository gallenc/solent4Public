/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.web;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.flower.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author gallenc
 */
@Controller
@RequestMapping("/")
public class ViewController {

    final static Logger LOG = LogManager.getLogger(ViewController.class);

    {
        LOG.debug("ViewController created");
    }

    // This serviceFacade object is injected by Spring
    @Autowired(required = true)
    @Qualifier("serviceFacade")
    ServiceFacade serviceFacade = null;

    @RequestMapping("/testHeartbeat")
    public String testHeartbeat(Model m) {

        LOG.debug("testHeartbeat called");
        if (serviceFacade == null) {
            throw new RuntimeException("serviceFacade==null and has not been initialised");
        }

        m.addAttribute("serviceFacade", serviceFacade);

        // add error / response messages to page
        String errorMessage = "";
        String message = "";
        m.addAttribute("errorMessage", errorMessage);
        m.addAttribute("message", message);

        // render view with jsp
        return "testHeartbeat";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model,
            @RequestParam(value = "symbol", required = false) String symbol,
            @RequestParam(value = "synonymSymbol", required = false) String synonymSymbol,
            @RequestParam(value = "scientificNamewithAuthor", required = false) String scientificNamewithAuthor,
            @RequestParam(value = "commonName", required = false) String commonName,
            @RequestParam(value = "family", required = false) String family) {

        LOG.debug("home called");
        if (serviceFacade == null) {
            throw new RuntimeException("serviceFacade==null and has not been initialised");
        }

        List<String> familiesList = serviceFacade.getAllFamilies();
        model.addAttribute("familiesList", familiesList);

        List<Flower> flowerList = null;
        if (family == null || family.isEmpty()) {
            flowerList = new ArrayList();
            family = "";
        } else {
            flowerList = serviceFacade.findLikefamily(family);
        }

        if (family == null) {
            family = "";
        }
        if (symbol == null) {
            symbol = "";
        }
        if (synonymSymbol == null) {
            synonymSymbol = "";
        }
        if (scientificNamewithAuthor == null) {
            scientificNamewithAuthor = "";
        }
        if (commonName == null) {
            commonName="";
        }
      
        
        model.addAttribute("family", family);
        model.addAttribute("symbol", symbol);
        model.addAttribute("synonymSymbol", synonymSymbol);
        model.addAttribute("scientificNamewithAuthor", scientificNamewithAuthor);
        model.addAttribute("commonName", commonName);
        model.addAttribute("flowerList", flowerList);

        return "home";
    }

    @RequestMapping(value = {"/about"}, method = RequestMethod.GET)
    public String about(Model model) {
        return "about";
    }

    @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
    public String contact(Model model) {
        return "contact";
    }

}
