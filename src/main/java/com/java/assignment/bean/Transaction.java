package com.java.assignment.bean;

import javax.persistence.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@ApiModelProperty(required = true)
	private Integer customerId;
	@ApiModelProperty(required = true)
	private LocalDate transactionDate;
	@ApiModelProperty(required = true)
	private Integer amountSpent;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [id=").append(id).append(", transactionDate=").append(transactionDate)
				.append(", amountSpent=").append(amountSpent).append("]");
		return builder.toString();
	}
	
	
	
	
}
