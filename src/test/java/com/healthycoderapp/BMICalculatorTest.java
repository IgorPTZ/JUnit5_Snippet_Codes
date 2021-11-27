package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BMICalculatorTest {
	
	// Invoked before all unit tests. Used, for example to open database connections
	@BeforeAll
	static void beforeAll() {
		
		System.out.println("Before all unit tests!");
	}
	
	// Invoked after all unit test. Used, for example to close database connections
	@AfterAll
	static void afterAll() {
		
		System.out.println("After all unit tests!");
	}

	// Enable a test runs several times, each time with a diferent value specified on @ValueSource
	@ParameterizedTest
	@ValueSource(doubles = {85.0, 89.0, 95.0, 110.0})
	void should_ReturnTrue_When_DietRecommended_One(Double coderWeight) {
		
		// Given
		double weight = coderWeight;
		
		double height = 1.72;
		
		// When
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		// Then
		assertTrue(recommended);
	}
	
	// Enable a test runs several times, each time with a diferent pair of values specified on @CsvFileSource
	@ParameterizedTest(name = "Weight= {0}, Height= {1}")
	@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1) //  Get values from a .csv file
	void should_ReturnTrue_When_DietRecommended_Two(Double coderWeight, Double coderHeight) {
		
		// Given
		double weight = coderWeight;
		
		double height = coderHeight;
		
		// When
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		// Then
		assertTrue(recommended);
	}
	
	// Enable a test runs several times, each time with a diferent pair of values specified on @CsvSource
	@ParameterizedTest(name = "Weight= {0}, Height= {1}")
	@CsvSource(value = {"30.0, 1.72", "40.0, 1.75", "42.0, 1.78", "45.0, 1.80"})
	void should_ReturnFalse_When_DietNotRecommended(Double coderWeight, Double coderHeight) {
		
		// Given
		double weight = coderWeight;
		
		double height = coderHeight;
		
		// When
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		// Then
		assertFalse(recommended);
	}
	
	@Test
	void should_ThrowArithmeticException_When_HeightZero() {
		
		// Given
		double weight = 50.0;
		
		double height = 0.0;
		
		// When
		Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
		
		// Then
		assertThrows(ArithmeticException.class, executable);
	}
	
	@Test
	void should_ReturnCoderWithWorstBMI_When_CoderListIsNotEmpty() {
		
		// Given
		List<Coder> coders = new ArrayList<>();
		
		coders.add(new Coder(1.80, 60.0));
		
		coders.add(new Coder(1.82, 98.0));
		
		coders.add(new Coder(1.82, 64.7));
		
		// When
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
		
		// Then
		assertAll(
			() -> assertEquals(1.82, coderWorstBMI.getHeight()),
			() -> assertEquals(98.0, coderWorstBMI.getWeight())
		);
	}
	
	// Test execution time of a method
	@Test
	void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements() {
		
		// Given
		List<Coder> coders = new ArrayList<>();
		
		for(int i = 0; i < 10000; i++) {
			
			coders.add(new Coder(1.0 + i, 10.0 + i));
		}
		
		// When
		Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
		
		// Then
		assertTimeout(Duration.ofMillis(500), executable);
	}
	
	@Test
	void shoud_ReturnNullWorstBMICoder_WHen_CoderListEmpty() {
		
		// Given
		List<Coder> coders = new ArrayList<>();
		
		// When
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
		
		// Then
		assertNull(coderWorstBMI);
	}
	
	@Test
	void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
		
		// Given
		List<Coder> coders = new ArrayList<>();
		
		coders.add(new Coder(1.80, 60.0));
		
		coders.add(new Coder(1.82, 98.0));
		
		coders.add(new Coder(1.82, 64.7));
		
		double[] expected = {18.52, 29.59, 19.53};
		
		// When
		double[] bmiScores = BMICalculator.getBMIScores(coders);
		
		// then
		assertArrayEquals(expected, bmiScores);
	}
}
