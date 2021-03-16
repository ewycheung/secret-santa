package com.example.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.test.objects.Person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {	
	public static Person getApplicableAssignee(int currentPersonIndex, List<Person> personList) {
		Person currPerson = personList.get(currentPersonIndex);
		List<Person> filteredList = personList.stream()
			.filter(p -> !currPerson.equals(p) && !p.getAssigned() && !currPerson.getExclusions().contains(p.getName()))
			.collect(Collectors.toList());
		Random rand = new Random();

		if (filteredList.size() > 0) {
			return filteredList.get(rand.nextInt(filteredList.size()));
		} else {
			return null;
		}
	}

	private static void assignSecretSanta(int currentPersonIndex, List<Person> personList) {
		Person assignee = getApplicableAssignee(currentPersonIndex, personList);

		if (assignee != null) {
			Person currPerson = personList.get(currentPersonIndex);			
			assignee.setAssgined(true);
			currPerson.setAssigneeEmail(assignee.getEmail());
			currPerson.setAssigneeName(assignee.getName());
		}
	}

	public static void addPerson(String line, List<Person> personList) {
		String[] tokens = line.split(",");
		Person newPerson = new Person(tokens[0], tokens[1]);
		for (int i = 2; i < tokens.length; i++) {
			newPerson.addExclusion(tokens[i]);
		}
		personList.add(newPerson);
	}

	public static void assignSecretSantas(List<Person> personList) {
		Comparator<Person> personComparator = Comparator.comparing(Person::getExclusionsSize).reversed();
		Collections.sort(personList, personComparator);				
				
		for (int i = 0; i < personList.size(); i++) {
			assignSecretSanta(i, personList);
		}
	}

	private static void printResult(List<Person> personList) {
		personList.stream()
			.map(e -> e.toString())
			.forEach(System.out::println);
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TestApplication.class, args);		
		List<Person> personList = new ArrayList<>();
		
		Stream<String> lines = Files.lines(Paths.get("C:\\Java Projects\\secret-santa\\src\\main\\java\\com\\example\\test\\new_person.csv"));
		lines.parallel().forEach(line -> addPerson(line, personList));				
		lines.close();
	
		assignSecretSantas(personList);
		printResult(personList);		
	}
}
