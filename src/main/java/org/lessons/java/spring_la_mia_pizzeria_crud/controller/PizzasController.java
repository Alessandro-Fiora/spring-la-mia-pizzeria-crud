package org.lessons.java.spring_la_mia_pizzeria_crud.controller;


import java.util.List;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repo.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;


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

    @GetMapping("/create")
    public String create(Model model){
        Pizza pizza = new Pizza();
        pizza.setFoto("https://fakeimg.pl/600x400/ff6666/ffffff?text=Pizza");
        model.addAttribute("pizza", pizza);
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model ){

        if(bindingResult.hasErrors()){
            return "pizzas/create";
        }

        repo.save(formPizza);

        return "redirect:/pizzas";
    }

    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, @RequestParam(value = "redirect", required = false) String redirect, Model model){
        model.addAttribute("pizza", repo.findById(id).get());
        model.addAttribute("redirect", redirect);
        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, @RequestParam(value = "redirect", required = false) String redirect, Model model ){
        if(bindingResult.hasErrors()){
            return "pizzas/edit";
        }
        repo.save(formPizza);
        if ("show".equals(redirect)) {
            return "redirect:/pizzas/" + formPizza.getId();
        }
        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        
        repo.deleteById(id);

        return "redirect:/pizzas";
    }
    
}
