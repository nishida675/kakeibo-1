package com.example.demo.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.SessionControl;
import com.example.demo.dtos.LoginForm;
import com.example.demo.entity.Balance;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BalanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private BalanceService balanceService;
	@Autowired
	SessionControl sessionControl;
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/")
	public String index() {
		logger.info(LocalDateTime.now().toString());
		return "Hello, this is a custom message!";
	}

	@PostMapping("/signin")
	@Transactional
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginForm loginForm) throws IOException {
		Map<String, Object> response = new HashMap<>();

		// パラメータをコンソールに出力
		System.out.println("Name: " + loginForm.getName());
		System.out.println("Email: " + loginForm.getEmail());

		User existingUser = this.userRepository.findByEmail(loginForm.getEmail());
		if (existingUser == null) {
			User newUser = new User();
			newUser.setName(loginForm.getName());
			newUser.setEmail(loginForm.getEmail());
			this.userRepository.saveAndFlush(newUser);
			logger.info("sign up(" + newUser + ")");
		}

		if (this.sessionControl.login(loginForm)) {
			response.put("status", "success");
			response.put("message", "Login successful");
			// 必要なセッションデータを追加
			response.put("sessionData", this.sessionControl.getUser());
			return ResponseEntity.ok().body(response);
		} else {
			response.put("status", "error");
			response.put("message", "Login failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	@PostMapping("/inputBalance")
	@Transactional
	public ResponseEntity<Void> inputBalance(@RequestBody Balance balance) {
		try {
			balanceService.saveBalance(balance);
			return ResponseEntity.ok().build(); // 成功レスポンスを返す
		} catch (Exception e) {
			// 失敗した場合の処理
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/getYear")
	public ResponseEntity<Map<String, Object>> getYear(
			@RequestParam("userId") String userId,
			@RequestParam(value = "dataYear", required = false) String dataYear) {
		try {
			Map<String, Object> response = new HashMap<>();
			if (dataYear == null || dataYear == "") {
				String currentYear = String.valueOf(Year.now().getValue());
				dataYear = currentYear;
			}
			response = balanceService.getYear(userId, dataYear);
			response.put("year", dataYear);
			response.put("status", "success");

			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("status", "error");
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

	}

	@GetMapping("/getMonth")
	public ResponseEntity<Map<String, Object>> getMonth(
			@RequestParam("userId") String userId,
			@RequestParam(value = "dataYear", required = false) String dataYear,
			@RequestParam(value = "dataMonth", required = false) String dataMonth) {
		try {
			Map<String, Object> response = new HashMap<>();
			if (dataYear == null || dataYear == "") {
				String currentYear = String.valueOf(Year.now().getValue());
				dataYear = currentYear;
			}
			if (dataMonth == null || dataMonth == "") {
				String currentMonth = String.valueOf(LocalDate.now().getMonthValue());
				dataMonth = currentMonth;
			}
			response = balanceService.getMonth(userId, dataYear, dataMonth);
			response.put("year", dataYear);
			response.put("month", dataMonth);
			response.put("status", "success");

			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("status", "error");
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

	}

	@DeleteMapping("/deleteBalance/{userId}/{balanceId}")
	@Transactional
	public ResponseEntity<Void> deleteBalance(@PathVariable String userId,
			@PathVariable String balanceId) {
		try {
			balanceService.deleteBalance(userId, balanceId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PostMapping("/editBalance")
	@Transactional
	public ResponseEntity<Void> editBalance(@RequestBody Balance balance) {
		try {
			balanceService.editBalance(balance);
			return ResponseEntity.ok().build(); // 成功レスポンスを返す
		} catch (Exception e) {
			// 失敗した場合の処理
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}