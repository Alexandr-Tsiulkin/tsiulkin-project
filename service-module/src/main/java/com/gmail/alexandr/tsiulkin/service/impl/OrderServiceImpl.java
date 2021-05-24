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
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.OrderPaginateConstant.MAXIMUM_ORDERS_ON_PAGE;
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
    public List<ShowOrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShowOrderDTO persist(ShowOrderDTO showOrderDTO) throws ServiceException {
        User userByUsername = userRepository.findUserByUsername(showOrderDTO.getEmail());
        if (Objects.nonNull(userByUsername)) {
            OrderStatus orderStatus = orderStatusRepository.findByStatusName(showOrderDTO.getOrderStatus());
            if (Objects.nonNull(orderStatus)) {
                List<Item> items = itemRepository.findItemsByTitle(showOrderDTO.getTitle());
                if (Objects.nonNull(items)) {
                    Order order = orderConverter.convert(showOrderDTO);
                    order.setOrderStatus(orderStatus);
                    order.getItems().addAll(items);
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setUser(userByUsername);
                    userByUsername.setOrderDetails(orderDetails);
                    orderDetails.setOrder(order);
                    order.setOrderDetails(orderDetails);
                    orderRepository.persist(order);
                    return orderConverter.convert(order);
                } else {
                    throw new ServiceException(String.format("Item with title: %s was not found", showOrderDTO.getTitle()));
                }
            } else {
                throw new ServiceException(String.format("Order status with status name: %s was not found", showOrderDTO.getOrderStatus()));
            }
        } else {
            throw new ServiceException(String.format("User with username: %s was not found", showOrderDTO.getEmail()));
        }
    }
}
