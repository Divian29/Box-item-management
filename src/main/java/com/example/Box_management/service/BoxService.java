package com.example.Box_management.service;

import com.example.Box_management.dto.request.BoxRequestDTO;
import com.example.Box_management.dto.request.ItemRequestDTO;
import com.example.Box_management.dto.response.BoxResponseDTO;
import com.example.Box_management.dto.response.ItemResponseDTO;
import com.example.Box_management.entity.Box;
import com.example.Box_management.entity.Item;
import com.example.Box_management.enums.BoxState;
import com.example.Box_management.exceptionHandler.BatteryLowException;
import com.example.Box_management.exceptionHandler.WeightLimitExceededException;
import com.example.Box_management.repository.BoxRepository;
import com.example.Box_management.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxService {
    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;

    public BoxService(BoxRepository boxRepository, ItemRepository itemRepository) {
        this.boxRepository = boxRepository;
        this.itemRepository = itemRepository;
    }

    public BoxResponseDTO registerBox(BoxRequestDTO dto) {
        Box box = new Box();
        box.setSerialNumber(dto.getSerialNumber());
        box.setWeightLimit(dto.getWeightLimit());
        box.setBatteryLevel(dto.getBatteryLevel());
        box.setState(BoxState.IDLE);

        Box savedBox = boxRepository.save(box);
        return mapToBoxResponse(savedBox);
    }

    public List<BoxResponseDTO> getAllBoxes() {
        return boxRepository.findAll()
                .stream()
                .map(this::mapToBoxResponse)
                .collect(Collectors.toList());
    }

    public BoxResponseDTO getBox(Long id) {
        Box box = boxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box not found"));
        return mapToBoxResponse(box);
    }

    public ItemResponseDTO loadItem(Long boxId, ItemRequestDTO dto) {
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        // Prevent LOADING if battery < 25%
        if (box.getBatteryLevel() < 25) {
            throw new BatteryLowException("Battery level too low for loading.");
        }

        // Prevent overload
        BigDecimal newWeight = box.getCurrentWeight().add(BigDecimal.valueOf(dto.getWeight()));
        if (newWeight.compareTo(box.getWeightLimit()) > 0) {
            throw new WeightLimitExceededException("Weight limit exceeded.");
        }

        // Set state to LOADING
        box.setState(BoxState.LOADING);

        Item item = new Item();
        item.setName(dto.getName());
        item.setWeight(BigDecimal.valueOf(dto.getWeight()));
        item.setCode(dto.getCode());
        item.setBox(box);

        itemRepository.save(item);
        box.getItems().add(item);

        boxRepository.save(box);

        return mapToItemResponse(item);
    }

    public List<ItemResponseDTO> getItemsInBox(Long boxId) {
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));
        return box.getItems()
                .stream()
                .map(this::mapToItemResponse)
                .collect(Collectors.toList());
    }

    // Get available boxes for loading
    public List<BoxResponseDTO> getAvailableBoxes() {
        return boxRepository.findAll().stream()
                .filter(box -> box.getBatteryLevel() >= 25
                        && box.getCurrentWeight().compareTo(box.getWeightLimit()) < 0
                        && box.getState() == BoxState.IDLE)
                .map(this::mapToBoxResponse)
                .collect(Collectors.toList());
    }

    // In BoxService
    public Integer getBatteryLevel(Long boxId) {
        // Always call the method that returns the entity
        Box boxEntity = boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));
        return boxEntity.getBatteryLevel();
    }


    // Utility mappers
    private BoxResponseDTO mapToBoxResponse(Box box) {
        return new BoxResponseDTO(
                box.getId(),
                box.getSerialNumber(),
                box.getWeightLimit(),
                box.getBatteryLevel(),
                box.getState(),
                box.getItems().stream().map(this::mapToItemResponse).collect(Collectors.toList())
        );
    }

    private ItemResponseDTO mapToItemResponse(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getWeight(),
                item.getCode()
        );
    }
}
