package com.handycredit.systems.core.web;

import com.handycredit.systems.security.HyperLinks;
import org.sers.webutils.server.web.controllers.AbstractApplicationController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController extends AbstractApplicationController {

    @RequestMapping(value = {"/Index"})
    public ModelAndView indexHandler(ModelMap model) {
        return new ModelAndView("Index", model);
    }

    @RequestMapping(value = {"/Error"})
    public ModelAndView errorHandler(ModelMap model) {
        return new ModelAndView("Error", model);
    }
    
 

    @RequestMapping(value = {HyperLinks.SYSTEM_SETTINGS_FORM})
    public ModelAndView getSystemSettingsForm(ModelMap model) {
        return new ModelAndView(HyperLinks.SYSTEM_SETTINGS_FORM.replace("/", ""), model);
    }

    @RequestMapping(value = {HyperLinks.BUSINESSES_VIEW})
    public ModelAndView getBusinesses(ModelMap model) {
        return new ModelAndView(HyperLinks.BUSINESSES_VIEW.replace("/", ""), model);
    }

    @RequestMapping(value = {HyperLinks.DASHBOARD})
    public ModelAndView getDashboard(ModelMap model) {
        return new ModelAndView(HyperLinks.DASHBOARD.replace("/", ""), model);
    }

    @RequestMapping(value = {HyperLinks.LOAN_PROVIDERS_VIEW})
    public ModelAndView getLoanProviders(ModelMap model) {
        return new ModelAndView(HyperLinks.LOAN_PROVIDERS_VIEW.replace("/", ""), model);
    }

    @RequestMapping(value = {HyperLinks.LOANS_VIEW})
    public ModelAndView getLoans(ModelMap model) {
        return new ModelAndView(HyperLinks.LOANS_VIEW.replace("/", ""), model);
    }
}
