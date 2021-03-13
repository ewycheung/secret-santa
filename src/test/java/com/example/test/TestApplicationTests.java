package com.example.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.example.test.objects.Person;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {	

	@Test
	void isFound() {
		// Test Case 1 - Random Index = Current Index => Should be false		
		boolean actual1 = TestApplication.isFound(1, 1, null, null);
		assertEquals(false, actual1);

		// Test Case 2 - Assignee is being assiged to others => Should be false
		Person assignee1 = new Person("AAA", "a@example.com");
		assignee1.setAssgined(true);
		boolean actual2 = TestApplication.isFound(1, 2, assignee1, null);
		assertEquals(false, actual2);

		// Test Case 3 - Assignee is excluded from Current => Should be false
		Person assignee2 = new Person("AAA", "a@example.com");
		Person current1 = new Person("BBB", "b@example.com");
		current1.addExclusion("AAA");
		boolean actual3 = TestApplication.isFound(1, 2, assignee2, current1);
		assertEquals(false, actual3);

		// Test Case 4 - Assignee is suitable to be assigned -> Should be true
		Person assignee3 = new Person("AAA", "a@example.com");
		Person current2 = new Person("BBB", "b@example.com");		
		boolean actual4 = TestApplication.isFound(1, 2, assignee3, current2);
		assertEquals(true, actual4);
	}

	@Test
	void addPerson() {
		List<Person> personList = new ArrayList<>();
		String line1 = "AAA,a@example.com";
		TestApplication.addPerson(line1, personList);
		String line2 = "DDD,d@example.com,AAA,BBB,CCC";
		TestApplication.addPerson(line2, personList);
		
		assertEquals(2, personList.size());
		assertEquals("AAA", personList.get(0).getName());
		assertEquals("a@example.com", personList.get(0).getEmail());
		assertEquals(0, personList.get(0).getExclusionsSize());
		assertEquals("DDD", personList.get(1).getName());
		assertEquals("d@example.com", personList.get(1).getEmail());
		assertEquals(3, personList.get(1).getExclusionsSize());
		assertEquals("AAA", personList.get(1).getExclusions().get(0));
		assertEquals("BBB", personList.get(1).getExclusions().get(1));
		assertEquals("CCC", personList.get(1).getExclusions().get(2));
	}	

	@Test
	void assignSecretSantas() {
		List<Person> personList = new ArrayList<>();
		String line1 = "AAA,a@example.com";
		TestApplication.addPerson(line1, personList);
		String line2 = "DDD,d@example.com,BBB,CCC";
		TestApplication.addPerson(line2, personList);

		TestApplication.assignSecretSantas(personList);

		assertEquals(true, personList.get(0).getAssigned());
		assertEquals(true, personList.get(1).getAssigned());
		assertEquals("DDD", personList.get(0).getName());
		assertEquals("AAA", personList.get(1).getName());
		assertEquals("AAA", personList.get(0).getAssigneeName());
		assertEquals("DDD", personList.get(1).getAssigneeName());
	}
}
