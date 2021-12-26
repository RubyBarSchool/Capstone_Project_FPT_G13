package com.university.fpt.acf;


import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.config.security.repository.RoleRepository;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.impl.*;
import com.university.fpt.acf.vo.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
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
	@Autowired
	private EmployeeServiceImpl employeeService;
	@Autowired
	private AccountManagerServiceImpl accountManagerServiceImpl;
	@Autowired
	private PersonalLeaveApplicationEmployeeServiceImpl personalLeaveApplicationEmployeeService;
	@Autowired
	private PersonalLeaveApplicationAdminServiceImpl personalLeaveApplicationAdminService;
	@Autowired
	private AdvanceSalaryAdminServiceImpl advanceSalaryAdminService;
	@Autowired
	private AdvanceSalaryEmployeeServiceImpl advanceSalaryEmployeeService;
	@Autowired
	private BonusServiceImpl bonusService;
	@Autowired
	private  PunishServiceImpl punishService;
	@Autowired
	private SalaryServiceImpl salaryService;
	@Autowired
	private AttendancesServiceImpl attendancesService;
	@Autowired
	private UnitMeasureRepository unitMeasureRepository;
	@Autowired
	private HeightMaterialRepository heightMaterialRepository;
	@Autowired
	private  CompanyRespository companyRespository;
	@Autowired
	private GroupMaterialRepository groupMaterialRepository;
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private ContactServiceImpl contactService;
	@Autowired
	private FrameMaterialRepository frameMaterialRepository;
	@Autowired
	private PriceMaterialRepository priceMaterialRepository;
	@Autowired
	private ProductionOrderServiceImpl productionOrderService;
	@Autowired
	private ContactMoneyServiceImpl contractMoneyService;
	@Autowired
	private PositionServiceImpl positionService;
	@Autowired
	private MaterialServiceImpl materialService;

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
		Employee employee1 = new Employee();
		employee.setAddress("HN");
		employee.setDob(LocalDate.now());
		employee.setEmail("anhlhhe130300@fpt.edu.vn");
		employee.setFullName("Lê Hoàng Anh");
		employee.setGender(true);
		employee.setNation("Kinh");
		employee.setPhone("0972776142");
		employee.setSalary(1500000l);
		employee.setPosition(position2);
		employeeRepository.save(employee1);

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
		Account account1 = new Account();
		account1.setCreated_by("truongtv");
		account1.setModified_by("truongtv");
		account1.setUsername("anhlh");
		account1.setEmployee(employee);
		account1.setPassword(passwordEncoder.encode("123456"));
		account1.setRoles(roles);
		accountManagerRepository.save(account1);
	}
	private void generateDefaultDataContract(){
		//unit
		UnitMeasure unitMeasure = new UnitMeasure();
		unitMeasure.setName("mm");
		unitMeasureRepository.save(unitMeasure);
		//height
		HeightMaterial heightMaterial = new HeightMaterial();
		heightMaterial.setFrameHeight("10");
		heightMaterialRepository.save(heightMaterial);
		//frame
		FrameMaterial frameMaterial = new FrameMaterial();
		frameMaterial.setFrameWidth("8840");
		frameMaterial.setFrameLength("12345");
		frameMaterialRepository.save(frameMaterial);
		//company
		Company company = new Company();
		company.setName("Công ty Anh Hòa");
		company.setAddress("Cầu Giấy - Hà Nội");
		company.setPhone("0975776174");
		company.setEmail("anhhoa@gmail.com");
		companyRespository.save(company);
		//group material
		GroupMaterial groupMaterial = new GroupMaterial();
		groupMaterial.setName("GỖ CÔNG NGHIỆP");
		groupMaterialRepository.save(groupMaterial);

		//material
		Material material = new Material();
		material.setCheckMaterial(true);
		GroupMaterial groupMaterial1 = new GroupMaterial();
		groupMaterial1.setId(1l);
		material.setGroupMaterial(groupMaterial);
		Company company1 = new Company();
		company1.setId(1l);
		material.setCompany(company1);
		material.setName("MFC");
		materialRepository.save(material);
		//price material
		PriceMaterial priceMaterial = new PriceMaterial();
		priceMaterial.setPrice("500000");
		Material material1 = new Material();
		material1.setId(1l);
		priceMaterial.setMaterial(material1);
		HeightMaterial heightMaterial1 = new HeightMaterial();
		heightMaterial1.setId(1l);
		priceMaterial.setHeightMaterial(heightMaterial);
		FrameMaterial frameMaterial1 = new FrameMaterial();
		frameMaterial1.setId(1l);
		UnitMeasure unitMeasure1 = new UnitMeasure();
		unitMeasure1.setId(1l);
		priceMaterial.setUnitMeasure(unitMeasure1);
		priceMaterial.setFrameMaterial(frameMaterial1);
		priceMaterialRepository.save(priceMaterial);

	}

	private void login(String username, String password){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Test
	@Order(1)
	void contextLoads() {
		generateDefaultData();
	}

	@Test
	@Order(2)
	void addEmployee() {
		login("truongtv","123456");
		//given
		AddEmployeeForm addEmployeeForm = new AddEmployeeForm();
		addEmployeeForm.setFullName("Lê Thị Ngọc");
		addEmployeeForm.setAddress("Kim Quan - Hà Nội");
		addEmployeeForm.setGender(false);
		addEmployeeForm.setEmail("ngoclthe131028@fpt.edu.vn");
		addEmployeeForm.setPhone("0961352582");
		addEmployeeForm.setNation("Kinh");
		addEmployeeForm.setIdPosition(2l);
		addEmployeeForm.setSalary(12000000l);
		LocalDate dob = LocalDate.of(1999, 7, 14);
		addEmployeeForm.setDob(dob);
		//when
		Boolean expected = employeeService.AddEmployee(addEmployeeForm);
		//then
		assertThat(expected).isTrue();
	}

	@Test
	@Order(3)
	void generateUsername() {
		//given
		String username="ngoclt";
		//when
		String expected = accountManagerServiceImpl.GenerateUsername(3l);
		//then
		assertThat(expected.equals(username));
	}
	@Test
	@Order(4)
	void insertAccount() {
		login("truongtv","123456");
		//given
		AddAccountForm addAccountForm = new AddAccountForm();
		addAccountForm.setUsername("ngoclt");
		addAccountForm.setEmployee(3l);
		List<Long> list = new ArrayList<>();
		list.add(1l);
		list.add(2l);
		addAccountForm.setListRole(list);
		//when
		Boolean expected = accountManagerServiceImpl.insertAccount(addAccountForm);
		//then
		assertThat(expected).isTrue();
	}
	@Test
	@Order(5)
	void resetPassword() {
		login("truongtv","123456");
		//given
		Long id =3l;
		//when
		Boolean expected = accountManagerServiceImpl.resetPassword(id);
		//then
		assertThat(expected).isTrue();
	}

//	/ ------------------đơn xin nghỉ--------------
//	/ employee log personal leave application
	@Test
	@Order(6)
	void addPersonalLeaveApplication(){
		login("truongtv","123456");
		//given
		AddPerLeaveAppEmployeeForm addForm = new AddPerLeaveAppEmployeeForm();
		addForm.setTitle("ĐƠN XIN NGHỈ");
		addForm.setContent("Em xin phép nghỉ ốm 1-2 ngày");
		List<LocalDate> listDate = new ArrayList<>();
		LocalDate dateStart = LocalDate.of(2021, 12, 25);
		LocalDate dateEnd = LocalDate.of(2021, 12, 26);
		listDate.add(dateStart);
		listDate.add(dateEnd);
		addForm.setDate(listDate);
		addForm.setFileAttach("");

		//when
		Boolean expected = personalLeaveApplicationEmployeeService.AddLeaveApplication(addForm);
		// then
		assertThat(expected).isTrue();
	}
	//Admin accept Personal Leave Application
	@Test
	@Order(7)
	void acceptPersonalLeaveApplication(){
		login("truongtv","123456");
		//Give
		AcceptPersonalLeaveApplicationAdminForm acceptForm = new AcceptPersonalLeaveApplicationAdminForm();
		acceptForm.setIdApplication(1l);
		acceptForm.setComment("Đồng ý");
		//when
		Boolean result = personalLeaveApplicationAdminService.acceptPersonalLeaveApplication(acceptForm);
		//then
		assertThat(result).isTrue();
	}
	//------------------View personal leave application
	@Test
	@Order(8)
	void viewPersonalLeaveApplication(){
		login("truongtv","123456");
		//given
		SearchPersonalApplicationEmployeeForm searchForm = new SearchPersonalApplicationEmployeeForm();
		searchForm.setStatus("1");
		searchForm.setPageIndex(1);
		searchForm.setPageSize(10);
		//when
		List<SearchPersonalApplicationEmployeeVO> listExpected = personalLeaveApplicationEmployeeService.searchPersonalLeaveApplicationEmployee(searchForm);
		SearchPersonalApplicationEmployeeVO personalApplicationEmployeeVO = listExpected.get(0);
		//then
		assertThat(personalApplicationEmployeeVO.getIdApplication()==1l && personalApplicationEmployeeVO.getStatusAccept().equals("1")).isTrue();
	}
	///------------------------- employee log advance salary
	@Test
	@Order(9)
	void addAdvanceSalary(){
		login("truongtv","123456");
		//given
		AddAdvanceSalaryEmployeeForm addForm = new AddAdvanceSalaryEmployeeForm();
		addForm.setTitle("ĐƠN XIN ỨNG LƯƠNG");
		addForm.setContent("Em xin phép ứng lương trước vì nhà có việc cần tiền");
		addForm.setAdvanceSalary("10000000");

		//when
		Boolean result = advanceSalaryEmployeeService.addAdvanceSalaryEmployee(addForm);
		// then
		assertThat(result).isTrue();
	}
	//Admin reject advance salary
	@Test
	@Order(10)
	void rejectAdvanceSalary(){
		login("truongtv","123456");
		//Give
		AcceptAdvanceSalaryAdminForm acceptForm = new AcceptAdvanceSalaryAdminForm();
		acceptForm.setId(1l);
		acceptForm.setComment("Không đồng ý");
		//when
		Boolean result = advanceSalaryAdminService.rejectAdvanceSalary(acceptForm);
		//then
		assertThat(result).isTrue();
	}
	//Employee view advance salary after receive notice of confirmation of salary application from admin
	@Test
	@Order(11)
	void viewRejectAdvanceSalary(){
		login("truongtv","123456");
		//given
		SearchAdvanceEmployeeForm searchForm = new SearchAdvanceEmployeeForm();
		searchForm.setAccept("0");
		searchForm.setTitle("ứng lương");
		searchForm.setContent("");
		searchForm.setPageIndex(1);
		searchForm.setPageSize(10);
		//when
		List<GetAllAdvanceSalaryEmployeeVO> listResult = advanceSalaryEmployeeService.searchAdvanceSalaryEmployee(searchForm);
		GetAllAdvanceSalaryEmployeeVO allAdvanceSalaryEmployeeVO = listResult.get(0);
		//then
		assertThat(allAdvanceSalaryEmployeeVO.getId()==1l && allAdvanceSalaryEmployeeVO.getAccept().equals("0")).isTrue();
	}
	///------------Admin tạo "Quyết định khen thưởng" cho nhân viên
	@Test
	@Order(12)
	void addBonus(){
		login("truongtv","123456");
		//given
		AddBonusAdminForm addFrom =new AddBonusAdminForm();
		addFrom.setReason("Hoàn thành công việc tốt và sớm hơn kế hoạch");
		addFrom.setTitle("QUYẾT ĐỊNH KHEN THƯỞNG");
		List<Long> listIdEmployee = new ArrayList<>();
		listIdEmployee.add(3l);
		addFrom.setListIdEmployee(listIdEmployee);
		LocalDate dateEffective = LocalDate.of(2021, 12, 26);
		addFrom.setEffectiveDate(dateEffective);
		addFrom.setMoney("2000000");
		addFrom.setStatus(true);
		//when
		Boolean result = bonusService.addBonus(addFrom);
		//then
		assertThat(result).isTrue();
	}
	// Tạo quyết định khen thưởng với trạng thái "Nháp"
	@Test
	@Order(13)
	void addBonus1(){
		login("truongtv","123456");
		//given
		AddBonusAdminForm addFrom =new AddBonusAdminForm();
		addFrom.setReason("Hoàn thành công việc rất tốt và sớm hơn kế hoạch");
		addFrom.setTitle("QUYẾT ĐỊNH KHEN THƯỞNG");
		List<Long> listIdEmployee = new ArrayList<>();
		listIdEmployee.add(3l);
		addFrom.setListIdEmployee(listIdEmployee);
		LocalDate dateEffective = LocalDate.of(2021, 12, 26);
		addFrom.setEffectiveDate(dateEffective);
		addFrom.setMoney("1000000");
		addFrom.setStatus(true);
		//when
		Boolean result  = bonusService.addBonus(addFrom);
		//then
		assertThat(result).isTrue();
	}

	@Test
	@Order(14)
	void addPunish(){
		login("truongtv","123456");
		//given
		AddBonusAdminForm addFrom =new AddBonusAdminForm();
		addFrom.setReason("Làm việc không có trách nhiệm");
		addFrom.setTitle("QUYẾT ĐỊNH KỶ LUẬT");
		List<Long> listIdEmployee = new ArrayList<>();
		listIdEmployee.add(3l);
		addFrom.setListIdEmployee(listIdEmployee);
		LocalDate dateEffective = LocalDate.of(2021, 12, 26);
		addFrom.setEffectiveDate(dateEffective);
		addFrom.setMoney("2000000");
		addFrom.setStatus(false);
		//when
		Boolean result = punishService.addPunish(addFrom);
		//then
		assertThat(result).isTrue();
	}
//	@Test
//	@Order(15)
//	void searchSalaryAccept() {
//		login("truongtv","123456");
//		//given
//		SearchSalaryForm searchForm = new SearchSalaryForm();
//		searchForm.setName("ngoc");
//		List<LocalDate> listDate = new ArrayList<>();
//		searchForm.setDate(listDate);
//		List<Long> listIdPosition= new ArrayList<>();
//		searchForm.setIdPositons(listIdPosition);
//		searchForm.setPageIndex(1);
//		searchForm.setPageSize(10);
//
//		//when
//		List<SearchSalaryVO> listResult = salaryService.searchSalaryAccept(searchForm);
//		SearchSalaryVO searchSalary = listResult.get(0);
//		//then
////		assertThat(searchSalary.getBonus().equals("2000000")&&searchSalary.getNameEmployee().equals("Lê Thị Ngọc")&&searchSalary.getPenalty().equals("2000000")).isTrue();
//	}
	//-----------------timekeeping-----------------
	@Test
	@Order(16)
	void addAttendance(){
		login("truongtv","123456");
		//given
		AddAttendanceForm addForm = new AddAttendanceForm();
		addForm.setDate(LocalDate.now());
		List<AttendanceNote> listNote = new ArrayList<>();
		AttendanceNote attendanceNote = new AttendanceNote();
		attendanceNote.setId(3l);
		attendanceNote.setNote("Đi làm đầy đủ");
		listNote.add(attendanceNote);
		addForm.setAttendance(listNote);
		addForm.setType("1");
		//when
		List<TimeKeep> listResult = attendancesService.saveAttendance(addForm);
		TimeKeep result = listResult.get(0);

		//then
		assertThat(result.getEmployee().getId()==3l&& result.getType().equals("1")).isTrue();
	}
	//---------------------------View timekeeping
	@Test
	@Order(17)
	void getAllAttendance(){
		login("truongtv","123456");
		//given
		AttendanceFrom searchForm = new AttendanceFrom();
		searchForm.setNote("");
		searchForm.setName("");
		List<LocalDate> listDate = new ArrayList<>();
		searchForm.setDate(listDate);
		searchForm.setType("");
		searchForm.setPageIndex(1);
		searchForm.setPageSize(10);
		//when
		List<AttendanceVO> listResult = attendancesService.getAllAttendance(searchForm);
		AttendanceVO  result = listResult.get(0);
		// then
		assertThat(result.getIdEmpl()==3l && result.getType().equals("1")).isTrue();
	}
///-------------------------Luồng sản xuất--------------------------
	@Test
	@Order(18)
	void  addContract(){
		generateDefaultDataContract();
		login("truongtv","123456");
//		FileMaterialVO
		FileMaterialVO fileMaterialVO = new FileMaterialVO();
		fileMaterialVO.setIdMaterial(1l);
		fileMaterialVO.setCountMaterial(4);
		fileMaterialVO.setIdUnitMaterial(1l);
		fileMaterialVO.setNoteMaterial("");
		fileMaterialVO.setFrameWidthMaterial("8840");
		fileMaterialVO.setFrameHeightMaterial("10");
		fileMaterialVO.setFrameLengthMaterial("12345");
		fileMaterialVO.setIdCompanyMaterial(1l);
		//fileProductVO
		FileProductVO fileProductVO = new FileProductVO();
		List<FileMaterialVO> listFileMaterialVO = new ArrayList<>();
		listFileMaterialVO.add(fileMaterialVO);
		fileProductVO.setFileMaterialVOList(listFileMaterialVO);
		fileProductVO.setCountProduct(1);
		fileProductVO.setNameProduct("MFC");
		fileProductVO.setFrameHeightProduct("500");
		fileProductVO.setFrameLengthProduct("3500");
		fileProductVO.setFrameWidthProduct("1000");
		fileProductVO.setNoteProduct("");
		//fileContractVO
		FileContactVO fileContactVO = new FileContactVO();
		List<FileProductVO> listFileProductVO = new ArrayList<>();
		listFileProductVO.add(fileProductVO);
		fileContactVO.setNoteContact("Đánh bóng kỹ");
		fileContactVO.setPriceContact("45000000");
		fileContactVO.setFileProductVOList(listFileProductVO);
		AddContactForm addContactForm = new AddContactForm();
		addContactForm.setName("Hợp Đồng Nội Thất Nhà Anh Hòa");
		addContactForm.setIdCompany(1l);
		addContactForm.setFileExcel(fileContactVO);
		LocalDate dateEffective = LocalDate.of(2021, 12, 28);
		addContactForm.setTime(dateEffective);
		//when
		Contact result = contactService.addContact(addContactForm);
		//then
		assertThat(result.getName().equals("Hợp Đồng Nội Thất Nhà Anh Hòa")).isTrue();
	}
	//---------------------Create production order and send working for employee---------------------------
	@Test
	@Order(19)
	void AddProductionOrder(){
		login("truongtv","123456");
		//given
		AddProductionOrderFrom addFrom = new AddProductionOrderFrom();
		addFrom.setIdProduct(1l);
		addFrom.setName("Tủ bếp");
		List<Long> listIdEmployee = new ArrayList<>();
		listIdEmployee.add(2l);
		addFrom.setIdEmployees(listIdEmployee);
		LocalDate dateStart = LocalDate.of(2021, 12, 15);
		LocalDate dateEnd = LocalDate.of(2021, 12, 25);
		addFrom.setDateStart(dateStart);
		addFrom.setDateEnd(dateEnd);
		addFrom.setIdContact(1l);
		//when
		Boolean result = productionOrderService.addProductionOrder(addFrom);
		//then
		assertThat(result).isTrue();
	}
	//---------------------Employee confirmed working---------------------
	@Test
	@Order(20)
	void confirmWork(){
		login("anhlh","123456");
		//given
		Long id = 1l;
		//when
		Boolean result = productionOrderService.confirmWork(id);
		//then
		assertThat(result).isTrue();
	}
	//----------------Complete working ---------------------
	@Test
	@Order(21)
	void completeWorking(){
		login("anhlh","123456");
		//given
		UpdateWorkEmployeeFrom updateForm = new UpdateWorkEmployeeFrom();
		updateForm.setNumber(1);
		updateForm.setId(1l);
		//when
		Boolean result = productionOrderService.updateWork(updateForm);
		//then
		assertThat(result).isTrue();
	}
	//-----------admin confirm complete working----------
	@Test
	@Order(22)
	void confirmWorkDone(){
		login("truongtv","123456");
		//given
		Long id =1l;
		//when
		Boolean result = productionOrderService.confirmWorkDone(id);
		//then
		assertThat(result).isTrue();
	}
	@Test
	@Order(23)
	void confirmContactMoney(){
		login("truongtv","123456");
		//given
		AddContactMoneyForm addContactMoneyForm = new AddContactMoneyForm();
		addContactMoneyForm.setContact(1l);
		addContactMoneyForm.setMoney("46000000");
		//when
		Boolean result = contractMoneyService.confirmContactMoney(addContactMoneyForm);
		//then
		assertThat(result).isTrue();
	}
	@Test
	@Order(24)
	void deleteAccount() {
		login("truongtv","123456");
		//given
		Long id = 3l;
		//when
		Boolean expected = accountManagerServiceImpl.deleteAccount(id);
		//then
		assertThat(expected).isTrue();
	}
	@Test
	@Order(25)
	void updateEmployee() {
		login("truongtv","123456");
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		updateEmployeeForm.setId(3l);
		updateEmployeeForm.setFullName("Lê Thị Ngọc");
		updateEmployeeForm.setAddress("Thạch Thất - Hà Nội");
		updateEmployeeForm.setGender(true);
		updateEmployeeForm.setEmail("ngoclthe131028@fpt.edu.vn");
		updateEmployeeForm.setPhone("0961352582");
		updateEmployeeForm.setNation("Kinh");
		updateEmployeeForm.setIdPosition(2l);
		updateEmployeeForm.setSalary(12500000l);
		LocalDate dob = LocalDate.of(1999, 07, 14);
		updateEmployeeForm.setDob(dob);
		//when
		Boolean expected = employeeService.UpdateEmployee(updateEmployeeForm);
		//then
		assertThat(expected).isTrue();
	}

	@Test
	@Order(26)
	void deleteEmployee() {
		login("truongtv","123456");
		//given
		Long id = 3l;
		//when
		Boolean expected = employeeService.DeleteEmployee(id);
		//then
		assertThat(expected).isTrue();
	}
	@Test
	@Order(27)
	void searchEmployeeDelete() {
		//given
		SearchAllEmployeeForm searchAllEmployeeForm = new SearchAllEmployeeForm();
		searchAllEmployeeForm.setName("");
		searchAllEmployeeForm.setStatusDelete(true);
		searchAllEmployeeForm.setPageIndex(1);
		searchAllEmployeeForm.setPageSize(10);
		//when
		List<SearchEmployeeVO> listExpected = employeeService.searchEmployee(searchAllEmployeeForm);
		//then
		assertThat(listExpected.size()== 1).isTrue();
	}
	@Test
	@Order(28)
	void getEmployeeSNotDelete() {
		//given
		SearchEmployeeForm searchEmployeeForm = new SearchEmployeeForm();
		searchEmployeeForm.setName("");
		searchEmployeeForm.setPageIndex(1);
		searchEmployeeForm.setPageSize(10);
		// when
		List<GetAllEmployeeVO> expectedList = employeeService.getEmployeeSNotDelete(searchEmployeeForm);
		//then
		assertThat(expectedList.size()==1).isTrue();
	}
	@Test
	@Order(29)
	void getFullNameEmployeeNotAccount() {
		addEmployee();
		//given
		SearchEmployeeForm searchEmployeeForm = new SearchEmployeeForm();
		searchEmployeeForm.setName("");
		searchEmployeeForm.setPageIndex(1);
		searchEmployeeForm.setPageSize(10);
		//when
		List<GetAllEmployeeVO> listExpected = employeeService.getFullNameEmployeeNotAccount(searchEmployeeForm);
		//then
		assertThat(listExpected.size()==1);
	}
	@Test
	@Order(30)
	void getEmployeeDetailById() {
		//given
		Long id = 1l;
		//when
		EmployeeDetailVO expected = employeeService.getEmployeeDetailById(id);
		//then
		assertThat(expected!=null);

	}
	@Test
	@Order(31)
	void getTotalEmployeeDelete() {
		//given
		SearchAllEmployeeForm searchAllEmployeeForm = new SearchAllEmployeeForm();
		searchAllEmployeeForm.setName("");
		searchAllEmployeeForm.setStatusDelete(true);
		searchAllEmployeeForm.setPageIndex(1);
		searchAllEmployeeForm.setPageSize(10);
		//when
		Integer expected = employeeService.getTotalEmployee(searchAllEmployeeForm);
		//then
		assertThat(expected==1);
	}
	@Test
	@Order(32)
	void searchPosition() {
		//given
		PositionForm positionForm = new PositionForm();
		positionForm.setName("");
		positionForm.setPageIndex(1);
		positionForm.setPageSize(10);
		//when
		List<PositionResponseVO> listExpect =positionService.searchPosition(positionForm);
		// then
		assertThat(listExpect.size()==3).isTrue();
	}
	@Test
	@Order(33)
	void totalSearchPosition() {
		//given
		PositionForm positionForm = new PositionForm();
		positionForm.setName("");
		positionForm.setPageIndex(1);
		positionForm.setPageSize(10);
		//when
		Integer totalExpected =positionService.totalSearchPosition(positionForm);
		// then
		assertThat(totalExpected==3).isTrue();
	}
	@Test
	@Order(34)
	void addPosition() {
		login("truongtv","123456");
		//given
		AddPositionForm positionForm = new AddPositionForm();
		positionForm.setCode("BV");
		positionForm.setName("Bảo vệ");
		//when
		Boolean result = positionService.addPosition(positionForm);
		//then
		assertThat(result).isTrue();
	}
	@Test
	@Order(35)
	void updatePosition() {
		login("truongtv","123456");
		//given
		UpdatePositionForm updatePositionForm = new UpdatePositionForm();
		updatePositionForm.setId(4l);
		updatePositionForm.setCode("BV");
		updatePositionForm.setName("Bao Ve");
		//when
		Boolean expected = positionService.updatePosition(updatePositionForm);
		//then
		assertThat(expected).isTrue();
	}
	@Test
	@Order(36)
	void deletePosition() {
		login("truongtv","123456");
		//given
		Long id = 4l;
		//when
		Boolean expected = positionService.deletePosition(id);
		// then
		assertThat(expected).isTrue();
	}
	@Test
	@Order(37)
	void addMaterial() {
		//given
		login("truongtv","123456");
		AddMaterialForm addMaterialForm = new AddMaterialForm();
		List<String> listName = new ArrayList<>();
		listName.add("Tấm ván HDE");
		addMaterialForm.setListName(listName);
		addMaterialForm.setIdCompany(1l);
		addMaterialForm.setIdUnit(1l);
		addMaterialForm.setIdGroup(1l);
		List<Long> listHeight = new ArrayList<>();
		listHeight.add(1l);
		addMaterialForm.setListIdHeight(listHeight);
		List<Long> listFrame = new ArrayList<>();
		listFrame.add(1l);
		addMaterialForm.setListIdFrame(listFrame);
		addMaterialForm.setPrice("550000");
		addMaterialForm.setImage("");
		//when
		Boolean expected = materialService.addMaterial(addMaterialForm);
		//then
		assertThat(expected).isTrue();
	}

	@Test
	@Order(38)
	void searchMaterial() {
		//given
		SearchMaterialForm searchMaterialForm = new SearchMaterialForm();
		searchMaterialForm.setCodeMaterial("");
		searchMaterialForm.setFrame("");
		List<Long> listGroup = new ArrayList<>();
		searchMaterialForm.setListGroupID(listGroup);
		List<Long> listCompany = new ArrayList<>();
		searchMaterialForm.setListIdCompany(listCompany);
		searchMaterialForm.setPageIndex(1);
		searchMaterialForm.setPageSize(10);
		//when
		List<MaterialVO> listExpected = materialService.searchMaterial(searchMaterialForm);
		//then
		assertThat(listExpected.size()==2).isTrue();
	}
	@Test
	@Order(39)
	void totalSearchMaterial() {
		//given
		SearchMaterialForm searchMaterialForm = new SearchMaterialForm();
		searchMaterialForm.setCodeMaterial("");
		searchMaterialForm.setFrame("");
		List<Long> listGroup = new ArrayList<>();
		searchMaterialForm.setListGroupID(listGroup);
		List<Long> listCompany = new ArrayList<>();
		searchMaterialForm.setListIdCompany(listCompany);
		searchMaterialForm.setPageIndex(1);
		searchMaterialForm.setPageSize(10);
		//when
		Integer expected = materialService.totalSearchMaterial(searchMaterialForm);
		//then
		assertThat(expected==2).isTrue();
	}

}
