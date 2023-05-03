package com.hojun.service.controller;

import com.hojun.service.domain.aggregate.material.Material;
import com.hojun.service.domain.aggregate.material.infra.MaterialRepository;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
public class MaterialController {
    private final MaterialRepository materialRepository;

    public MaterialController(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @PostMapping("/materials")
    public Material create(MaterialCreateParams params) {
        Material material = new Material(params.getName());
        if(params.hasPriceInfo()) {
            material.setPriceInfo(params.getPrice(), params.getAmount());
        }
        return materialRepository.save(material);
    }

    @PutMapping("/materials/{materialId}l")
    public Material update(@PathVariable String materialId, MaterialCreateParams params) {
        Material material = new Material(params.getName());
        if(params.hasPriceInfo()) {
            material.setPriceInfo(params.getPrice(), params.getAmount());
        }
        return materialRepository.update(materialId, material);
    }

    @DeleteMapping("/materials/{materialId}")
    public Material delete(@PathVariable String materialId) {
        return materialRepository.delete(materialId);
    }

    @GetMapping("/materials/{materialId}")
    public Material get(@PathVariable String materialId) {
        return materialRepository.findById(materialId);
    }

    @Data
    public static class MaterialCreateParams {
        private String name;
        private Double price;
        private Double amount;

        public boolean hasPriceInfo() {
            return price != null && amount != null;
        }
    }
}
