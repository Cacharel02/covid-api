package com.polytech.covid.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polytech.covid.Model.Personne;
import com.polytech.covid.Repository.PersonneRepository;

@Service
public class PersonneService {
    
    @Autowired
    private PersonneRepository personneRepository;

    public void create(Personne personne){
        personneRepository.save(personne);
    }

    public Personne findByName(String name){
        return personneRepository.findByName(name);
    }

    public Boolean isVaccinated(Personne personne){
        return personne.getIsVaccinated();
    }

    public Personne findById(Long id){
        return personneRepository.findById(id).get();
    }

    public Personne setVaccination(Long id){
        return personneRepository.findById(id).map(personne -> {
            personne.setIsVaccinated(true);
            return personneRepository.save(personne);
        }).orElseThrow();
    }

    public boolean equals(Personne p, Personne q){
        return p.getName().getBytes() == q.getName().getBytes();
    }

}
