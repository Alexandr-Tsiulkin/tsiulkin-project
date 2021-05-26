package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ItemRepository;
import com.gmail.alexandr.tsiulkin.repository.OrderRepository;
import com.gmail.alexandr.tsiulkin.repository.OrderStatusRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.repository.model.Order;
import com.gmail.alexandr.tsiulkin.repository.model.OrderDetails;
import com.gmail.alexandr.tsiulkin.repository.model.OrderStatus;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.OrderService;
import com.gmail.alexandr.tsiulkin.service.converter.OrderConverter;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.OrderItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.OrderStatusDTOEnum;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.OrderPaginateConstant.MAXIMUM_ORDERS_ON_PAGE;
import static com.gmail.alexandr.tsiulkin.service.util.SecurityUtil.getAuthentication;
import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.getPageDTO;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Override
    @Transactional
    public PageDTO getOrdersByPage(int page) {
        Long countOrders = orderRepository.getCountOrders();
        PageDTO pageDTO = getPageDTO(page, countOrders, MAXIMUM_ORDERS_ON_PAGE);
        List<Order> orders = orderRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_ORDERS_ON_PAGE);
        pageDTO.getOrders().addAll(orders.stream()
                .map(orderConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }

    @Override
    @Transactional
    public ShowOrderDTO getOrderById(Long id) throws ServiceException {
        Order order = orderRepository.findById(id);
        if (Objects.nonNull(order)) {
            return orderConverter.convert(order);
        } else {
            throw new ServiceException(String.format("Order with id: %s was not found", id));
        }
    }

    @Override
    @Transactional
    public ShowOrderDTO changeStatusById(String status, Long id) throws ServiceException {
        Order order = orderRepository.findById(id);
        if (Objects.nonNull(order)) {
            OrderStatus statusName = orderStatusRepository.findByStatusName(status);
            if (Objects.nonNull(statusName)) {
                order.setOrderStatus(statusName);
                return orderConverter.convert(order);
            } else {
                throw new ServiceException(String.format("Status with status name: %s was not found", status));
            }
        } else {
            throw new ServiceException(String.format("Order with id: %s was not found", id));
        }
    }

    @Override
    @Transactional
    public ShowOrderDTO persist(ShowItemDTO showItemDTO, OrderItemDTO orderItemDTO) throws ServiceException {
        Authentication authentication = getAuthentication();
        String userName = authentication.getName();
        User userByUsername = userRepository.findUserByUsername(userName);
        if (Objects.nonNull(userByUsername)) {
            Item item = itemRepository.findByUuid(showItemDTO.getUuid());
            if (Objects.nonNull(item)) {
                String statusName = OrderStatusDTOEnum.NEW.name();
                OrderStatus status = orderStatusRepository.findByStatusName(statusName);
                if (Objects.nonNull(status)) {
                    Order order = getOrder(orderItemDTO, userByUsername, item, status);
                    orderRepository.persist(order);
                    return orderConverter.convert(order);
                } else {
                    throw new ServiceException(String.format("Status with status name: %s was not found", statusName));
                }
            } else {
                throw new ServiceException(String.format("Item with uuid: %s was not found", showItemDTO.getUuid()));
            }
        } else {
            throw new ServiceException(String.format("User with username: %s was not found", userName));
        }
    }

    private Order getOrder(OrderItemDTO orderItemDTO, User userByUsername, Item item, OrderStatus status) {
        Order order = new Order();
        order.setNumberOfOrder(UUID.randomUUID());
        order.setOrderStatus(status);
        order.setItem(item);
        Long numberOfItems = orderItemDTO.getNumberOfItems();
        order.setNumberOfItems(orderItemDTO.getNumberOfItems());
        order.setLocalDateTime(LocalDateTime.now());
        BigDecimal price = item.getPrice();
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(numberOfItems));
        order.setTotalPrice(totalPrice);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setUser(userByUsername);
        orderDetails.setOrder(order);
        order.setOrderDetails(orderDetails);
        return order;
    }


    @Override
    @Transactional
    public List<ShowOrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderConverter::convert)
                .collect(Collectors.toList());
    }
}
