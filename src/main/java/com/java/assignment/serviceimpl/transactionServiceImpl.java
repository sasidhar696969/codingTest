package com.java.assignment.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.assignment.bean.Response;
import com.java.assignment.bean.Transaction;
import com.java.assignment.repository.TransactionRepository;

@Service
public class transactionServiceImpl{
	
	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction processMessage(Transaction transaction) {
		return transactionRepository.save(transaction);	
	}
	
	public List<Transaction> processMessages(List<Transaction> transactionList) {
		return (List<Transaction>) transactionRepository.saveAll(transactionList);	
	}

	public List<Response> getRewardPoints(LocalDate startDate, LocalDate endDate) {
		List<Transaction> transactionList = transactionRepository.findAllByTransactionDateBetween(startDate, endDate);
		List<Response> responseList = new ArrayList<>();
		
		Map<Integer, List<Transaction>> customerTransactionMap = new HashMap<>();
		transactionList.forEach(transaction -> {
			if(customerTransactionMap.containsKey(transaction.getCustomerId())){
				customerTransactionMap.get(transaction.getCustomerId()).add(transaction);
			}else {
				List<Transaction> transactions = new ArrayList<>();
				transactions.add(transaction);
				customerTransactionMap.put(transaction.getCustomerId(), transactions);
			}
		});
			
		customerTransactionMap.forEach((id,trans) -> {
			Map<String, Integer> monthSpent = new HashMap<>();
			trans.forEach(transaction -> {
				String month = transaction.getTransactionDate().getMonth().name();
				int rewards = 0;
				if(transaction.getAmountSpent() < 50) {
					rewards = 0;
				} else if(transaction.getAmountSpent() <=100 && transaction.getAmountSpent() > 50) {
					rewards = transaction.getAmountSpent() - 50;
				} else if(transaction.getAmountSpent() > 100) {
					rewards = ((transaction.getAmountSpent()-50)/50) + (2* transaction.getAmountSpent()-100);
				}
				if(monthSpent.containsKey(month)){
					int total = monthSpent.get(month) + rewards;
					monthSpent.put(month, total);
				} else {
					monthSpent.put(month, rewards);
				}
			});
			
			monthSpent.forEach((month,points)->{
				Response response = new Response();
				response.setId(id);
				response.setMonth(month);
				response.setPoints(points);
				responseList.add(response);
			});			
		});
		return responseList;
	}
}
