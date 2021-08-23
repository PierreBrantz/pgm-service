package com.cinqc.maraichage.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ProducerProductId implements Serializable {

	private static final long serialVersionUID = 1208226889088589847L;

	@Column(name = "product_id")
	private Long productId;
	@Column(name = "producer_id")
	private Long producerId;
	
	public ProducerProductId() {}

	public ProducerProductId(Long producerId, Long productId) {
		this.producerId = producerId;
		this.productId = productId;
	}
	
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		ProducerProductId that = (ProducerProductId)o;
		return Objects.equals(productId, that.productId) &&
				Objects.equals(producerId, that.producerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, producerId);
	}

}
