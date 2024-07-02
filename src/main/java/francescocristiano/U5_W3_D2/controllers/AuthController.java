package francescocristiano.U5_W3_D2.controllers;

import francescocristiano.U5_W3_D2.entities.Employee;
import francescocristiano.U5_W3_D2.exceptions.BadRequestException;
import francescocristiano.U5_W3_D2.payloads.NewEmployeeDTO;
import francescocristiano.U5_W3_D2.payloads.UserLoginDTO;
import francescocristiano.U5_W3_D2.payloads.UserLoginResponseDTO;
import francescocristiano.U5_W3_D2.services.AuthService;
import francescocristiano.U5_W3_D2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO userLoginDTO) {
        return new UserLoginResponseDTO(authService.authenticateAndGenerateToken(userLoginDTO));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Validated NewEmployeeDTO employeePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return employeeService.saveEmployee(employeePayload);
    }


}
