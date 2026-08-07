package com.example.calc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.calc.dto.ResponseDTO;

@SpringBootTest
class CalculatorServiceTest {

	private CalculatorService calculator = new CalculatorService();

	@Test
	void TestAdd() {
		assertEquals(ResponseDTO.success("579.0"), calculator.calculate("123+456"));
	}

	@Test
	void TestAddDec() {
		assertEquals(ResponseDTO.success("579.4"), calculator.calculate("123.4+456"));
	}

	@Test
	void TestAddSpace() {
		assertEquals(ResponseDTO.success("579.0"), calculator.calculate("123 + 456"));
	}

	@Test
	void TestSub() {
		assertEquals(ResponseDTO.success("67.0"), calculator.calculate("123  -  56"));
	}

	@Test
	void TestMult() {
		assertEquals(ResponseDTO.success("215.0"), calculator.calculate("5*43"));
	}

	@Test
	void TestDiv() {
		assertEquals(ResponseDTO.success("2.5"), calculator.calculate("15/6"));
	}

	@Test
	void TestNegMult() {
		assertEquals(ResponseDTO.success("-127.0"), calculator.calculate("-25.4 * 5"));
	}

	@Test
	void TestNegDiv() {
		assertEquals(ResponseDTO.success("-0.5"), calculator.calculate("-32  /64"));
	}

	@Test
	void TestPoint() {
		assertEquals(ResponseDTO.success("0.3"), calculator.calculate(".5 * .6"));
	}

	@Test
	void TestLongExp() {
		assertEquals(ResponseDTO.success("56.0"), calculator.calculate("123+6/3-23*3"));
	}

	@Test
	void TestAbsErr() {
		assertEquals(ResponseDTO.error("Empty input"), calculator.calculate(""));
	}

	@Test
	void TestAbsErrSpace() {
		assertEquals(ResponseDTO.error("Empty input"), calculator.calculate("   "));
	}

	@Test
	void TestAddErr() {
		assertEquals(ResponseDTO.error("Illegal symbol placement"), calculator.calculate("123 + -456"));
	}

	@Test
	void TestDecErr() {
		assertEquals(ResponseDTO.error("Invalid number format"), calculator.calculate("123..4 + 456"));
	}

	@Test
	void TestDecErr2() {
		assertEquals(ResponseDTO.error("Invalid number format"), calculator.calculate("123.4.5 + 456"));
	}

	@Test
	void TestSpaceErr() {
		assertEquals(ResponseDTO.error("Invalid number format"), calculator.calculate("123 4  + 456"));
	}

	@Test
	void TestSymErr() {
		assertEquals(ResponseDTO.error("Illegal symbol found"), calculator.calculate("123  + e456"));
	}

	@Test
	void TestExpErr() {
		assertEquals(ResponseDTO.error("Invalid expression"), calculator.calculate("123456"));
	}

	@Test
	void TestDivErr() {
		assertEquals(ResponseDTO.error("Division by zero"), calculator.calculate("123 / 0"));
	}
}
