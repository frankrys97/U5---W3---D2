package francescocristiano.U5_W3_D2.services;

import francescocristiano.U5_W3_D2.entities.Employee;
import francescocristiano.U5_W3_D2.exceptions.UnauthorizedException;
import francescocristiano.U5_W3_D2.payloads.UserLoginDTO;
import francescocristiano.U5_W3_D2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;


    public String authenticateAndGenerateToken(UserLoginDTO userLoginDTO) {

        Employee employee = employeeService.findEmployeeByUsername(userLoginDTO.username());
        if (employee.getPassword().equals(userLoginDTO.password())) {
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
