package com.example.Box_management.controllers;

import com.example.Box_management.dto.request.ItemRequestDTO;
import com.example.Box_management.dto.response.ItemResponseDTO;
import com.example.Box_management.entity.Item;
import com.example.Box_management.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Register a new item
    @PostMapping
    public ResponseEntity<ItemResponseDTO> registerItem(@RequestBody ItemRequestDTO dto) {
        ItemResponseDTO response = itemService.registerItem(dto);
        return ResponseEntity.ok(response);
    }

    // Get all items
    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        List<ItemResponseDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable Long id) {
        ItemResponseDTO item = itemService.getItem(id);
        return ResponseEntity.ok(item);
    }
}
