package ivanovvasil.u5d5w2Project.controllers;

import ivanovvasil.u5d5w2Project.entities.Employee;
import ivanovvasil.u5d5w2Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w2Project.payloads.NewEmployeeDTO;
import ivanovvasil.u5d5w2Project.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  @Autowired
  private EmployeesService employeesService;

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Employee saveEmployee(@RequestBody @Validated NewEmployeeDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return employeesService.save(body);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @GetMapping("")
  public Page<Employee> getAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "15") int size,
                               @RequestParam(defaultValue = "name") String orderBy) {
    return employeesService.findAll(page, size, orderBy);
  }

  @GetMapping("/{id}")
  public Employee findById(@PathVariable int id) {
    return employeesService.findById(id);
  }

  @PutMapping("/{id}")
  public Employee findByIdAndUpdate(@PathVariable int id, @RequestBody Employee body) {
    return employeesService.findByIdAndUpdate(id, body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void findByIdAndDelete(@PathVariable int id) {
    employeesService.findByIdAndDelete(id);
  }
}
