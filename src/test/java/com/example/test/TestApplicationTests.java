package com.example.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.example.test.objects.Person;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {	
	
	@Test
	void getApplicableAssignee() {
		List<Person> personList = new ArrayList<>();
		TestApplication.addPerson("AAA,a@example.com", personList);
		TestApplication.addPerson("BBB,b@example.com,AAA", personList);
		TestApplication.addPerson("CCC,c@example.com", personList);
		TestApplication.addPerson("DDD,d@example.com,AAA,BBB,CCC", personList);
		TestApplication.addPerson("EEE,e@example.com,AAA,DDD", personList);

		Person actual1 = TestApplication.getApplicableAssignee(personList.get(3), personList);
		assertEquals("EEE", actual1.getName());
		actual1.setAssgined(true);

		Person actual2 =  TestApplication.getApplicableAssignee(personList.get(4), personList);
		assertTrue("BBB".equals(actual2.getName()) || "CCC".equals(actual2.getName()));
		actual2.setAssgined(true);

		Person actual3 =  TestApplication.getApplicableAssignee(personList.get(1), personList);
		assertTrue("DDD".equals(actual3.getName()) || "CCC".equals(actual3.getName()));
		actual3.setAssgined(true);

		Person actual4 =  TestApplication.getApplicableAssignee(personList.get(0), personList);
		assertTrue("BBB".equals(actual4.getName()) || "CCC".equals(actual4.getName()) || "DDD".equals(actual4.getName()));
		actual4.setAssgined(true);

		Person actual5 =  TestApplication.getApplicableAssignee(personList.get(2), personList);
		assertTrue(actual5 == null || "AAA".equals(actual5.getName()) || "BBB".equals(actual5.getName()) || "DDD".equals(actual4.getName()));
		actual5.setAssgined(true);
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
