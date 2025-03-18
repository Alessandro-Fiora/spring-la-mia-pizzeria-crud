package org.lessons.java.spring_la_mia_pizzeria_crud.controller;


import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/pizzas")
public class PizzasController {

    @Autowired
    private PizzaRepository repo;
    
    @GetMapping
    public String index(Model model){

        List<Pizza> pizzas = repo.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("pizza", repo.findById(id).get());

        return "pizzas/show";
    }

    @GetMapping("/search")
    public String searchByName(@RequestParam("search") String search, Model model){
        List<Pizza> pizzas = repo.findByNomeContainingIgnoreCaseOrDescrizioneContaining(search, search);

        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }
    
}
