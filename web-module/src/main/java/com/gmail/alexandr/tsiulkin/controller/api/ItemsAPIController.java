package com.gmail.alexandr.tsiulkin.controller.api;

import com.gmail.alexandr.tsiulkin.service.ItemService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ErrorDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class ItemsAPIController {

    private final ItemService itemService;

    @GetMapping(value = "/items")
    public List<ShowItemDTO> getItems() {
        return itemService.getItems();
    }

    @GetMapping(value = "/items/{id}")
    public ShowItemDTO getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping(value = "/items")
    public ResponseEntity<Object> addItem(@RequestBody @Valid AddItemDTO addItemDTO,
                                          BindingResult result) {
        if (result.hasErrors()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(result.getFieldErrors());
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        } else {
            itemService.persist(addItemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) throws ServiceException {
        boolean deleteById = itemService.isDeleteById(id);
        if (deleteById) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
