package com.gmail.alexandr.tsiulkin.controller.mvc;

import com.gmail.alexandr.tsiulkin.service.ItemService;
import com.gmail.alexandr.tsiulkin.service.OrderService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.OrderItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ITEMS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.SELLER_PATH;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping(value = {CUSTOMER_PATH + ITEMS_PATH,
            SELLER_PATH + ITEMS_PATH})
    public String getItemsByPagination(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        PageDTO pageDTO = itemService.getItemsByPage(page);
        model.addAttribute("page", pageDTO);
        model.addAttribute("orderItem", new OrderItemDTO());
        return "items";
    }

    @GetMapping(value = {SELLER_PATH + ITEMS_PATH + "/{uuid}",
            CUSTOMER_PATH + ITEMS_PATH + "/{uuid}"})
    public String getItemDetailsByUuid(@PathVariable UUID uuid, Model model) throws ServiceException {
        ShowItemDTO showItemDTO = itemService.getItemByUuid(uuid);
        model.addAttribute("item", showItemDTO);
        return "item";
    }

    @GetMapping(value = SELLER_PATH + ITEMS_PATH + "/{uuid}/delete")
    public String deleteItemByUuid(@PathVariable UUID uuid) throws ServiceException {
        itemService.isDeleteByUuid(uuid);
        return "redirect:/seller/items";
    }

    @GetMapping(value = SELLER_PATH + ITEMS_PATH + "/{uuid}/copy")
    public String copyItemByUuid(@PathVariable UUID uuid) throws ServiceException {
        itemService.CopyItemByUuid(uuid);
        return "redirect:/seller/items";
    }

    @PostMapping(value = "/customer/items/{uuid}/order-item")
    public String addOrderItemWithNumberOfItems(@Valid @ModelAttribute("orderItem") OrderItemDTO orderItemDTO,
                                                BindingResult result,
                                                @PathVariable UUID uuid) throws ServiceException {
        if (result.hasErrors()) {
            return "items";
        }
        ShowItemDTO showItemDTO = itemService.getItemByUuid(uuid);
        orderService.persist(showItemDTO, orderItemDTO);
        return String.format("redirect:%s%s", CUSTOMER_PATH, ITEMS_PATH);
    }
}
