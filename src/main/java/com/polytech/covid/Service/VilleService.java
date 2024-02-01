package com.polytech.covid.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Ville;
import com.polytech.covid.Repository.CenterRepository;
import com.polytech.covid.Repository.VilleRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VilleService {

    private final VilleRepository villeRepository;

    private final CenterRepository centerRepository;

    public Ville create(Ville ville){
        return villeRepository.save(ville);
    }

    public List<Center> getCenters(Long id){
        return centerRepository.findByVille(getVilleById(id));
    }

    public Ville getVilleById(Long id){
        return villeRepository.findById(id).get();
    }

    public Ville getVilleByName(String name){
        return villeRepository.findByName(name);
    }
}
