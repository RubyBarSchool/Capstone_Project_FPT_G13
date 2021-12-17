package com.university.fpt.acf;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.config.security.repository.RoleRepository;
import com.university.fpt.acf.config.security.service.AuthenticationService;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.repository.PositionRespository;
import com.university.fpt.acf.service.AccountManagerService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class AcfApplicationTests {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository  roleRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PositionRespository positionRespository;

	@Autowired
	private AccountManagerRepository accountManagerRepository;

	private void generateDefaultData(){
		// data role
		Role role = new Role();
		role.setCreated_by("truongtv");
		role.setModified_by("truongtv");
		role.setCode("SP_ADMIN");
		role.setName("Quản Lý Hệ Thống");
		roleRepository.save(role);
		Role role1 = new Role();
		role1.setCreated_by("truongtv");
		role1.setModified_by("truongtv");
		role1.setCode("ADMIN");
		role1.setName("Quản Lý");
		roleRepository.save(role1);
		Role role2 = new Role();
		role2.setCreated_by("truongtv");
		role2.setModified_by("truongtv");
		role2.setCode("EMPLOYEE");
		role2.setName("Nhân Viên");
		roleRepository.save(role2);

		Position position = new Position();
		position.setCreated_by("truongtv");
		position.setModified_by("truongtv");
		position.setCode("GD");
		position.setName("Giám Đốc");
		positionRespository.save(position);

		Position position1 = new Position();
		position1.setCreated_by("truongtv");
		position1.setModified_by("truongtv");
		position1.setCode("QL");
		position1.setName("Quản Lý");
		positionRespository.save(position1);

		Position position2 = new Position();
		position2.setCreated_by("truongtv");
		position2.setModified_by("truongtv");
		position2.setCode("NV");
		position2.setName("Nhân Viên");
		positionRespository.save(position2);

		Employee employee = new Employee();
		employee.setAddress("Nam Định");
		employee.setDob(LocalDate.now());
		employee.setEmail("truongtvhe130302@fpt.edu.vn");
		employee.setFullName("Trần Vũ Trường");
		employee.setGender(true);
		employee.setNation("Kinh");
		employee.setPhone("0382945665");
		employee.setSalary(1500000l);
		employee.setPosition(position);
		employeeRepository.save(employee);

		List<Role> roles = new ArrayList<>();
		roles.add(role);
		roles.add(role1);
		roles.add(role2);

		Account account = new Account();
		account.setCreated_by("truongtv");
		account.setModified_by("truongtv");
		account.setUsername("truongtv");
		account.setEmployee(employee);
		account.setPassword(passwordEncoder.encode("123456"));
		account.setRoles(roles);
		accountManagerRepository.save(account);


	}


	@Test
	@Order(1)
	void contextLoads() {

		generateDefaultData();

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken("truongtv", "123456"));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
