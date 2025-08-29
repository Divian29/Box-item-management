package com.example.Box_management.service;

import com.example.Box_management.dto.request.ItemRequestDTO;
import com.example.Box_management.dto.response.ItemResponseDTO;
import com.example.Box_management.entity.Item;
import com.example.Box_management.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponseDTO registerItem(ItemRequestDTO dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setWeight(BigDecimal.valueOf(dto.getWeight()));
        item.setCode(dto.getCode());
        Item savedItem = itemRepository.save(item);

        return mapToResponseDTO(savedItem);
    }

    public List<ItemResponseDTO> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemResponseDTO getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        return mapToResponseDTO(item);
    }

    private ItemResponseDTO mapToResponseDTO(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getWeight(),
                item.getCode()
        );
    }
}
