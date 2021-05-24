package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ItemRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.repository.model.ItemDetails;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.ItemService;
import com.gmail.alexandr.tsiulkin.service.converter.ItemConverter;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.OrderItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.OrderStatusDTOEnum;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.ItemConstant.MAXIMUM_ITEMS_ON_PAGE;
import static com.gmail.alexandr.tsiulkin.service.util.SecurityUtil.getAuthentication;
import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.getPageDTO;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public PageDTO getItemsByPage(int page) {
        Long countItems = itemRepository.getCountItems();
        PageDTO pageDTO = getPageDTO(page, countItems, MAXIMUM_ITEMS_ON_PAGE);
        List<Item> items = itemRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_ITEMS_ON_PAGE);
        pageDTO.getItems().addAll(items.stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }

    @Override
    @Transactional
    public List<ShowItemDTO> getItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShowItemDTO getItemById(Long id) throws ServiceException {
        Item item = itemRepository.findById(id);
        if (Objects.nonNull(item)) {
            return itemConverter.convert(item);
        } else {
            throw new ServiceException(String.format("Item with id: %s was not found", id));
        }
    }

    @Override
    @Transactional
    public ShowItemDTO persist(AddItemDTO addItemDTO) {
        Item item = itemConverter.convert(addItemDTO);
        UUID uuid = UUID.randomUUID();
        item.setUuid(uuid);
        itemRepository.persist(item);
        return itemConverter.convert(item);
    }

    @Override
    @Transactional
    public boolean isDeleteById(Long id) {
        itemRepository.removeById(id);
        return true;
    }

    @Override
    @Transactional
    public ShowItemDTO getItemByUuid(UUID uuid) throws ServiceException {
        Item item = itemRepository.findByUuid(uuid);
        if (Objects.nonNull(item)) {
            return itemConverter.convert(item);
        } else {
            throw new ServiceException(String.format("Item with uuid: %s was not found", uuid));
        }
    }

    @Override
    @Transactional
    public boolean isDeleteByUuid(UUID uuid) throws ServiceException {
        Item item = itemRepository.findByUuid(uuid);
        if (Objects.nonNull(item)) {
            itemRepository.removeById(item.getId());
            return true;
        } else {
            throw new ServiceException(String.format("Item with uuid: %s was not found", uuid));
        }
    }

    @Override
    @Transactional
    public boolean isCopyItemByUuid(UUID uuid) throws ServiceException {
        Item item = itemRepository.findByUuid(uuid);
        if (Objects.nonNull(item)) {
            Item copyItem = new Item();
            copyFieldsByItem(copyItem, item);
            itemRepository.persist(copyItem);
            return true;
        } else {
            throw new ServiceException(String.format("Item with uuid: %s was not found", uuid));
        }
    }

    @Override
    @Transactional
    public ShowOrderDTO orderItemByTitle(OrderItemDTO orderItemDTO, String title) throws ServiceException {
        Authentication authentication = getAuthentication();
        if (Objects.nonNull(authentication)) {
            String userName = authentication.getName();
            User user = userRepository.findUserByUsername(userName);
            if (Objects.nonNull(user)) {
                List<Item> items = itemRepository.findItemsByTitle(title);
                if (Objects.nonNull(items)) {
                    Long countItemsByTitle = itemRepository.getCountItemsByTitle(title);
                    checkTheNumberOfItemsInTheShop(countItemsByTitle, orderItemDTO);
                    return getShowOrderDTO(items, orderItemDTO, user);
                } else {
                    throw new ServiceException(String.format("Item with title: %s was not found", title));
                }
            } else {
                throw new ServiceException(String.format("User with username: %s was not found", userName));
            }
        } else {
            throw new ServiceException(String.format("An unauthenticated user tries to order an item with a title: %s", title));
        }
    }

    private ShowOrderDTO getShowOrderDTO(List<Item> items, OrderItemDTO orderItemDTO, User user) {
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();
        showOrderDTO.setOrderStatus(OrderStatusDTOEnum.NEW.name());
        showOrderDTO.setTitle(items.get(0).getTitle());
        showOrderDTO.setNumberOfOrder(UUID.randomUUID());
        Long numberOfItems = orderItemDTO.getNumberOfItems();
        showOrderDTO.setNumberOfItems(numberOfItems);
        showOrderDTO.setEmail(user.getEmail());
        showOrderDTO.setTelephone(user.getUserDetails().getTelephone());
        BigDecimal price = items.get(0).getPrice();
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(numberOfItems));
        showOrderDTO.setTotalPrice(totalPrice);
        return showOrderDTO;
    }

    private void checkTheNumberOfItemsInTheShop(Long countItemsByTitle, OrderItemDTO orderItemDTO) throws ServiceException {
        if (countItemsByTitle < orderItemDTO.getNumberOfItems()) {
            throw new ServiceException(String.format("The required number of items is not in stock, the maximum quantity: %s", countItemsByTitle));
        }
    }

    private void copyFieldsByItem(Item copyItem, Item item) {
        UUID uuid = UUID.randomUUID();
        copyItem.setUuid(uuid);
        String title = item.getTitle();
        copyItem.setTitle(title);
        BigDecimal price = item.getPrice();
        copyItem.setPrice(price);
        ItemDetails itemDetails = item.getItemDetails();
        String shortContent = itemDetails.getShortContent();
        ItemDetails copyItemDetails = new ItemDetails();
        copyItemDetails.setShortContent(shortContent);
        copyItem.setItemDetails(copyItemDetails);
        copyItemDetails.setItem(copyItem);
    }
}
