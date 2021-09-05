package tech.keshav.payment.paymentproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.keshav.payment.paymentproject.entities.Employee;
import tech.keshav.payment.paymentproject.exceptions.AuthenticationFailed;
import tech.keshav.payment.paymentproject.exceptions.UserAlreadyExistsException;
import tech.keshav.payment.paymentproject.models.AuthRequest;
import tech.keshav.payment.paymentproject.models.AuthResponse;
import tech.keshav.payment.paymentproject.repositories.EmployeeRepository;
import tech.keshav.payment.paymentproject.util.JwtUtil;


@RestController()
@RequestMapping("api")
@AllArgsConstructor
public class AuthController {
    EmployeeRepository employeeRepository;

    PasswordEncoder passwordEncoder;

    AuthenticationManager authenticationManager;

    JwtUtil jwtUtil;

    @PostMapping("register")
    public Employee registerUser(@RequestBody AuthRequest request) throws UserAlreadyExistsException {
        if (employeeRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(request.getUsername());
        } else
            return employeeRepository.save(new Employee(request.getUsername(), passwordEncoder.encode(request.getPassword())));
    }

    @PostMapping("login")
    public AuthResponse generateToken(@RequestBody AuthRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new AuthenticationFailed(ex);
        }
        return new AuthResponse(jwtUtil.generateToken(loginRequest.getUsername()));
    }
}
