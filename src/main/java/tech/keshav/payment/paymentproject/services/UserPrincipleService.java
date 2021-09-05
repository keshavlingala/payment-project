package tech.keshav.payment.paymentproject.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.keshav.payment.paymentproject.repositories.EmployeeRepository;
@Service
@AllArgsConstructor
public class UserPrincipleService implements UserDetailsService {

    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return employeeRepository.getById(s);
    }
}
