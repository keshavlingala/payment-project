package tech.keshav.payment.paymentproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.keshav.payment.paymentproject.entities.Employee;
import tech.keshav.payment.paymentproject.models.AuthRequest;
import tech.keshav.payment.paymentproject.models.AuthResponse;
import tech.keshav.payment.paymentproject.models.CustomResponse;
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
    public ResponseEntity<Object> registerUser(@RequestBody AuthRequest request) {
        return employeeRepository.existsByUsername(request.getUsername()) ?
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse("Registration Failed!", "Username already exists!")) :
                ResponseEntity.status(HttpStatus.OK)
                        .body(employeeRepository
                                .save(new Employee(request.getUsername(), passwordEncoder.encode(request.getPassword()))));
    }

    @PostMapping("login")
    public ResponseEntity<Object> generateToken(@RequestBody AuthRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse("Login Failed", "Username/Password is not valid"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(jwtUtil.generateToken(loginRequest.getUsername())));
    }
}
