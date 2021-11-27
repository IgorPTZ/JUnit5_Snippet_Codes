package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class DietPlannerTest {

	private DietPlanner dietPlanner;
	
	
	// Invoked before each test method
	@BeforeEach
	void setup() {
		
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}
	
	@AfterEach
	void complete() {
		
		System.out.println("A unit test was finished!");
	}
	
	@Test
	void should_ReturnCorrectDietPlan_When_CorrectCoder_One() {
		
		// Given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		
		// When
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		// Then
		assertAll(
			() -> assertEquals(expected.getCalories(), actual.getCalories()),	
			() -> assertEquals(expected.getProtein(), actual.getProtein()),
			() -> assertEquals(expected.getFat(), actual.getFat()),
			() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
		);
	}
	
	// Especify number of times a test should run
	@RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	void should_ReturnCorrectDietPlan_When_CorrectCoder_Two() {
		
		// Given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		
		// When
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		// Then
		assertAll(
			() -> assertEquals(expected.getCalories(), actual.getCalories()),	
			() -> assertEquals(expected.getProtein(), actual.getProtein()),
			() -> assertEquals(expected.getFat(), actual.getFat()),
			() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
		);
	}
}
