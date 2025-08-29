package com.example.Box_management.controllers;

import com.example.Box_management.dto.request.BoxRequestDTO;
import com.example.Box_management.dto.request.ItemRequestDTO;
import com.example.Box_management.dto.response.BoxResponseDTO;
import com.example.Box_management.dto.response.ItemResponseDTO;
import com.example.Box_management.entity.Box;
import com.example.Box_management.entity.Item;
import com.example.Box_management.service.BoxService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boxes")
public class BoxController {
    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    // Register a new Box
    @PostMapping
    public ResponseEntity<BoxResponseDTO> registerBox(@RequestBody BoxRequestDTO dto) {
        BoxResponseDTO response = boxService.registerBox(dto);
        return ResponseEntity.ok(response);
    }

    // Get all Boxes
    @GetMapping
    public ResponseEntity<List<BoxResponseDTO>> getAllBoxes() {
        List<BoxResponseDTO> boxes = boxService.getAllBoxes();
        return ResponseEntity.ok(boxes);
    }

    // Get Box by ID
    @GetMapping("/{id}")
    public ResponseEntity<BoxResponseDTO> getBox(@PathVariable Long id) {
        BoxResponseDTO box = boxService.getBox(id);
        return ResponseEntity.ok(box);
    }

    // Load an item into a Box
    @PostMapping("/{boxId}/load")
    public ResponseEntity<ItemResponseDTO> loadItem(@PathVariable Long boxId, @Valid @RequestBody ItemRequestDTO dto) {
        ItemResponseDTO response = boxService.loadItem(boxId, dto);
        return ResponseEntity.ok(response);
    }

    // Check battery level for a Box
    @GetMapping("/{id}/battery")
    public ResponseEntity<Integer> getBatteryLevel(@PathVariable Long id) {
        return ResponseEntity.ok(boxService.getBatteryLevel(id));
    }

    //  Get available boxes for loading
    @GetMapping("/available")
    public ResponseEntity<List<BoxResponseDTO>> getAvailableBoxes() {
        return ResponseEntity.ok(boxService.getAvailableBoxes());
    }


    // Get items in a Box
    @GetMapping("/{boxId}/items")
    public ResponseEntity<List<ItemResponseDTO>> getItemsInBox(@PathVariable Long boxId) {
        List<ItemResponseDTO> items = boxService.getItemsInBox(boxId);
        return ResponseEntity.ok(items);
    }
}
