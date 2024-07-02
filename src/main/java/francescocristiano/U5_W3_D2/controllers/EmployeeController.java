package francescocristiano.U5_W3_D2.controllers;

import francescocristiano.U5_W3_D2.entities.Employee;
import francescocristiano.U5_W3_D2.exceptions.BadRequestException;
import francescocristiano.U5_W3_D2.payloads.NewEmployeeDTO;
import francescocristiano.U5_W3_D2.payloads.NewRoleDTO;
import francescocristiano.U5_W3_D2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    // Ho messo un controllo anche su la lista dei dipendenti perchè nel mio modo di vedere le cose soltanto un dipendente admin
    // potrebbe vedere tutte le informazioni di tutti i dipendenti
    public Page<Employee> getEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return employeeService.findAllEmployees(page, size, sortBy);
    }

    @GetMapping("/me")
    public Employee getProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        return currentAuthenticatedEmployee;
    }

    @PutMapping("/me")
    public Employee updateProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee, @RequestBody @Validated NewEmployeeDTO employeePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return employeeService.findEmployeeByIdAndUpdate(currentAuthenticatedEmployee.getId(), employeePayload);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        employeeService.deleteEmployeeById(currentAuthenticatedEmployee.getId());
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    // Stesso ragionamento vale nel cercare un dipendente nel database tramite il suo id, solo un ADMIN dovrebbe
    // poterlo fare
    public Employee getEmployeeById(@PathVariable UUID id) {
        return employeeService.findEmployeeById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable UUID id) {
        employeeService.deleteEmployeeById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee updateEmployeeById(@PathVariable UUID id, @RequestBody @Validated NewEmployeeDTO employeePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return employeeService.findEmployeeByIdAndUpdate(id, employeePayload);
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee updateRole(@PathVariable UUID id, @RequestBody NewRoleDTO role) {
        return employeeService.updateRole(id, role);
    }

    @PostMapping("/{id}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee uploadAvatar(@PathVariable UUID id, @RequestParam("avatar") MultipartFile file) throws IOException {
        return employeeService.uploadAvatar(id, file);
    }

}


