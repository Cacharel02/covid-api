package com.polytech.covid.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Personne;
import com.polytech.covid.Service.GlobalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final GlobalService globalService;
    @GetMapping("/centers")
    public List<Center> centers(){
        return globalService.centers();
    }

    @GetMapping("/{ville}/centers")
    public List<Center> centersByTown(@PathVariable("ville") Long id){
        return globalService.centersByTown(id);
    }

    @PostMapping("/books")
    public void book(@RequestParam(name = "center_id") Long id, @RequestBody Personne personne){
        globalService.book(id, personne);
    }

    @PutMapping("/cancel")
    public void cancel(@RequestParam(name = "bookId") Long id){
        globalService.cancel(id);
    }
}
