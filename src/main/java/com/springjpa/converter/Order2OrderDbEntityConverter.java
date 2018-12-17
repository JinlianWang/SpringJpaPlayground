package com.springjpa.converter;

import com.springjpa.model.core.Order;
import com.springjpa.model.db.OrderDbEntity;

public class Order2OrderDbEntityConverter extends ConversionServiceAwareConverter<Order, OrderDbEntity> {

	@Override
	public OrderDbEntity convert(Order order) {
		OrderDbEntity orderDbEntity = new OrderDbEntity(order.getAmount(), order.getProduct());
		return orderDbEntity;
	}
}
