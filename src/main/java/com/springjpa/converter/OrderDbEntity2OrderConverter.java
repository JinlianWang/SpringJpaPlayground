package com.springjpa.converter;

import com.springjpa.model.core.Order;
import com.springjpa.model.db.OrderDbEntity;

public class OrderDbEntity2OrderConverter extends ConversionServiceAwareConverter<OrderDbEntity, Order> {

	@Override
	public Order convert(OrderDbEntity orderEntity) {
		Order order = new Order();
		order.setId(orderEntity.getOrderId());
		order.setAmount(orderEntity.getAmount());
		order.setProduct(orderEntity.getProduct());
		return order;
	}

}
