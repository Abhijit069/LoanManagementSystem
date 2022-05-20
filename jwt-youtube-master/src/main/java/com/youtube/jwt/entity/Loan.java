package com.youtube.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "LOAN")
public class Loan {
	@Id
	private String loanNo;
	private String fName;
	private String lName;
	private String propertyAddress;
	private int loanamount;
	private String loanType;
	private String loanTerm ;
	
	
	
}
