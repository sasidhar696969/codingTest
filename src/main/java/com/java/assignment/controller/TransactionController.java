package com.java.assignment.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.assignment.bean.Response;
import com.java.assignment.bean.Transaction;
import com.java.assignment.serviceimpl.transactionServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Validated
@Api(tags = "RewardPoint APIs")
@RestController
@RequestMapping(value = "/")
public class TransactionController {
	
	@Autowired
	private transactionServiceImpl challengeServiceImpl;
	
	@PostMapping(value="/transaction")
	@ApiOperation(value = "Create Transaction ", notes = "Create single transaction. Id can be null and date format should in in yyyy-MM-dd fromat.")
	 public ResponseEntity<Transaction> createTransaction(@RequestBody(required = true) Transaction transaction) {
		Transaction msg = challengeServiceImpl.processMessage(transaction);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	} 
	
	@PostMapping(value="/transactionList")
	@ApiOperation(value = "Save Multiple Transactions.", notes = "Save Multiple Transactions. Id can be null and date format should in in yyyy-MM-dd fromat.")
	 public ResponseEntity<List<Transaction>> createChallenges(@RequestBody(required = true) List<Transaction> transactions) {
		List<Transaction> msg = challengeServiceImpl.processMessages(transactions);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
	@GetMapping(value = "/rewards/{startDate}/{endDate}")
	@ApiOperation(value = "Get Challenges by interviewid", notes = "Use this end point to fetch the reward points per customer between the given dates. Date format should in in yyyy-MM-dd.")
	public ResponseEntity<List<Response>> getChallengesByInterviewId(@RequestParam("startDate") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<Response> result = challengeServiceImpl.getRewardPoints(startDate, endDate);
		return new ResponseEntity<>(result, HttpStatus.OK);
	} 
}
