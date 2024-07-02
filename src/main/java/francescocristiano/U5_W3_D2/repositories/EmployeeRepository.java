package francescocristiano.U5_W3_D2.repositories;

import francescocristiano.U5_W3_D2.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmail(String email);
}
