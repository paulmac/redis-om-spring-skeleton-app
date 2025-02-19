package com.redis.om.skeleton.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redis.om.skeleton.json.Person;
import com.redis.om.skeleton.repositories.PeopleRepository;

@RestController
@RequestMapping("/api/v1/people")
public class PeopleControllerV1 {

  @Autowired
  PeopleRepository repo;

  @GetMapping("age_between")
  Iterable<Person> byAgeBetween( //
      @RequestParam("min") int min, //
      @RequestParam("max") int max) {
    return repo.findByAgeBetween(min, max);
  }

  @GetMapping("homeloc")
  Iterable<Person> byHomeLoc(//
      @RequestParam("lat") double lat, //
      @RequestParam("lon") double lon, //
      @RequestParam("d") double distance) {
    return repo.findByHomeLocNear(new Point(lon, lat), new Distance(distance, Metrics.MILES));
  }

  @GetMapping("name")
  Iterable<Person> byFirstNameAndLastName(@RequestParam("first") String firstName, //
      @RequestParam("last") String lastName) {
    return repo.findByFirstNameAndLastName(firstName, lastName);
  }

  @GetMapping("statement")
  Iterable<Person> byPersonalStatement(@RequestParam("q") String q) {
    return repo.searchByPersonalStatement(q);
  }

  @PostMapping("new")
  Person create(@RequestBody Person newPerson) {
    return repo.save(newPerson);
  }

  // "{id}": It is a placeholder for a dynamic value in the URL path. The curly
  // braces {} are used to define a path variable, where id is the name of the
  // variable. The actual value for id will be provided by the client in the URL
  // when making the request.

  // When a GET request is made to the specified URL path pattern, such as
  // /example/123, Spring MVC will map the request to the method annotated with
  // @GetMapping("{id}") and pass the value 123 as the id path variable.
  @GetMapping("{id}")
  Optional<Person> byId(@PathVariable String id) {
    return repo.findById(id);
  }

  // person -> { ... }: This is a lambda expression that handles the case when the
  // Person is found in the database. The lambda expression updates the fields of
  // the existing Person object with the values from newPerson.
  @PutMapping("{id}")
  Person update(@RequestBody Person newPerson, @PathVariable String id) {
    return repo.findById(id).map(person -> {
      person.setFirstName(newPerson.getFirstName());
      person.setLastName(newPerson.getLastName());
      person.setAge(newPerson.getAge());
      person.setAddress(newPerson.getAddress());
      person.setHomeLoc(newPerson.getHomeLoc());
      person.setPersonalStatement(newPerson.getPersonalStatement());

      return repo.save(person);
      // .orElseGet(() -> { ... }): This part of the code handles the case when the
      // Person with the given id is not found in the database. The orElseGet()
      // function is called on the Optional<Person>, and it provides a fallback action
      // using a lambda expression.
    }).orElseGet(() -> {
      return repo.save(newPerson);
    });
  }

  @DeleteMapping("{id}")
  void delete(@PathVariable String id) {
    repo.deleteById(id);
  }

  @GetMapping("city")
  Iterable<Person> byCity(@RequestParam("city") String city) {
    return repo.findByAddress_City(city);
  }

  @GetMapping("city_state")
  Iterable<Person> byCityAndState(@RequestParam("city") String city, //
      @RequestParam("state") String state) {
    return repo.findByAddress_CityAndAddress_State(city, state);
  }

  @GetMapping("skills")
  Iterable<Person> byAnySkills(@RequestParam("skills") Set<String> skills) {
    return repo.findBySkills(skills);
  }

  @GetMapping("skills/all")
  Iterable<Person> byAllSkills(@RequestParam("skills") Set<String> skills) {
    return repo.findBySkillsContainingAll(skills);
  }

  @GetMapping("search/{q}")
  Iterable<Person> fullTextSearch(@PathVariable("q") String q) {
    return repo.search(q);
  }
}
