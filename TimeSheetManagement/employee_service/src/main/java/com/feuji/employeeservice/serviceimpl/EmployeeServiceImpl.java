package com.feuji.employeeservice.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.dto.AddEmployee;
import com.feuji.employeeservice.dto.EmployeeDisplayDto;
import com.feuji.employeeservice.dto.EmployeeDto;
import com.feuji.employeeservice.dto.SaveEmployeeDto;
import com.feuji.employeeservice.dto.UpadteEmployeeDto;
import com.feuji.employeeservice.entity.EmployeeEntity;
import com.feuji.employeeservice.exception.RecordsNotFoundException;
import com.feuji.employeeservice.repository.EmployeeRepository;
import com.feuji.employeeservice.service.EmployeeService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;


import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	public EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	// SAVE
	@Override

	public EmployeeEntity saveEmployee(EmployeeBean employeeBean) {
		// Convert EmployeeBean to EmployeeEntity
		
		EmployeeEntity employeeEntity = beanToEntity(employeeBean);

		// Save the EmployeeEntity
		employeeEntity = employeeRepository.save(employeeEntity);

		return employeeEntity;
	}

	@Override
	public List<EmployeeEntity> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@Override
	public List<SaveEmployeeDto> getByReferenceTypeId(Integer referenceTypeId) {
		
		return employeeRepository.getByReferenceTypeId(referenceTypeId);
	}

	// GET BY ID
	@Override
	public EmployeeEntity getById(Integer id) {
		return employeeRepository.findById(id).orElse(null);
	}

	// GET EMPLOYEE BY UUID
	@Override
	public EmployeeEntity getByUuid(String uuid) {
		return employeeRepository.findByUuid(uuid);
	}

	@Override
	public List<EmployeeDto> getByUserEmpId(Integer id) {
		return employeeRepository.getEmployeeDetailsByUserEmpId(id);
	}

	@Override
	public boolean isEmployeeCodeUnique(String empCode) {
		return !employeeRepository.existsByEmployeeCode(empCode);
	}

	
	@Override
	public EmployeeBean getReportingMngIdByEmpId(Integer employeeId) {
		EmployeeEntity entity = employeeRepository.findById(employeeId).orElseThrow();
		return entityToBean(entity);
	}

	public List<AddEmployee> getAllReportingManager() {
		

		List<AddEmployee> employees = employeeRepository.findDesignationsContainingManager();
		return employees;
	}
	
	@Override
	public List<EmployeeEntity> searchEmployeesByFirstName(String firstName) {
		 return employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
	}



	public EmployeeBean entityToBean(EmployeeEntity entity) {
		EmployeeBean employeeBean = new EmployeeBean();

		employeeBean.setEmployeeId(entity.getEmployeeId());
		employeeBean.setEmployeeCode(entity.getEmployeeCode());
		employeeBean.setFirstName(entity.getFirstName());
		employeeBean.setMiddleName(entity.getMiddleName());
		employeeBean.setLastName(entity.getLastName());
		employeeBean.setDesignation(entity.getDesignation());
		employeeBean.setEmail(entity.getEmail());
		employeeBean.setGender(entity.getGender());
		employeeBean.setDateOfJoining(entity.getDateOfJoining());
		employeeBean.setReportingManagerId(entity.getReportingManagerId());
		employeeBean.setEmploymentType(entity.getEmploymentType());
		employeeBean.setStatus(entity.getStatus());
		employeeBean.setDeliveryUnitId(entity.getDeliveryUnitId());
		employeeBean.setBusinessUnitId(entity.getBusinessUnitId());
		employeeBean.setExitDate(entity.getExitDate());
		employeeBean.setExitRemarks(entity.getExitRemarks());
		employeeBean.setIsDeleted(entity.getIsDeleted());
		employeeBean.setUuid(entity.getUuid());
		employeeBean.setCreatedBy(entity.getCreatedBy());
		employeeBean.setCreatedOn(entity.getCreatedOn());
		employeeBean.setModifiedBy(entity.getModifiedBy());
		employeeBean.setModifiedOn(entity.getModifiedOn());

		return employeeBean;
	}
	
	


	public EmployeeEntity beanToEntity(EmployeeBean employeeBean) {
		EmployeeEntity entity = new EmployeeEntity();

		entity.setEmployeeId(employeeBean.getEmployeeId());
		entity.setEmployeeCode(employeeBean.getEmployeeCode());
		entity.setFirstName(employeeBean.getFirstName());
		entity.setMiddleName(employeeBean.getMiddleName());
		entity.setLastName(employeeBean.getLastName());
		entity.setDesignation(employeeBean.getDesignation());
		entity.setEmail(employeeBean.getEmail());
		entity.setGender(employeeBean.getGender());
		entity.setDateOfJoining(employeeBean.getDateOfJoining());
		entity.setReportingManagerId(employeeBean.getReportingManagerId());
		entity.setEmploymentType(employeeBean.getEmploymentType());
		entity.setStatus(employeeBean.getStatus());
		entity.setDeliveryUnitId(employeeBean.getDeliveryUnitId());
		entity.setBusinessUnitId(employeeBean.getBusinessUnitId());
		entity.setExitDate(employeeBean.getExitDate());
		entity.setExitRemarks(employeeBean.getExitRemarks());
		entity.setIsDeleted(employeeBean.getIsDeleted());
		entity.setUuid(employeeBean.getUuid());
		entity.setCreatedBy(employeeBean.getCreatedBy());
		entity.setCreatedOn(employeeBean.getCreatedOn());
		entity.setModifiedBy(employeeBean.getModifiedBy());
		entity.setModifiedOn(employeeBean.getModifiedOn());

		return entity;
	}




		@Override
		public void updateEmployeeDetails(EmployeeEntity updateEmployee, Integer id) throws Throwable {
			EmployeeEntity existingEmployee = employeeRepository.findById(id)
					.orElseThrow(() -> new Exception("Employee not found with id: " + id));

			existingEmployee.setFirstName(updateEmployee.getFirstName());
			existingEmployee.setMiddleName(updateEmployee.getMiddleName());
			existingEmployee.setLastName(updateEmployee.getLastName());
			existingEmployee.setDesignation(updateEmployee.getDesignation());
			existingEmployee.setEmail(updateEmployee.getEmail());
			existingEmployee.setGender(updateEmployee.getGender());
			existingEmployee.setDateOfJoining(updateEmployee.getDateOfJoining());
			existingEmployee.setReportingManagerId(updateEmployee.getReportingManagerId());
			existingEmployee.setEmploymentType(updateEmployee.getEmploymentType());
			existingEmployee.setStatus(updateEmployee.getStatus());
			existingEmployee.setDeliveryUnitId(updateEmployee.getDeliveryUnitId());
			existingEmployee.setBusinessUnitId(updateEmployee.getBusinessUnitId());
			existingEmployee.setExitDate(updateEmployee.getExitDate());
			existingEmployee.setExitRemarks(updateEmployee.getExitRemarks());
			existingEmployee.setIsDeleted(updateEmployee.getIsDeleted());
			existingEmployee.setUuid(updateEmployee.getUuid());
			existingEmployee.setCreatedBy(updateEmployee.getCreatedBy());
			existingEmployee.setCreatedOn(updateEmployee.getCreatedOn());
			existingEmployee.setModifiedBy(updateEmployee.getModifiedBy());
			existingEmployee.setModifiedOn(updateEmployee.getModifiedOn());

			employeeRepository.save(existingEmployee);
		}
		@Override
		public List<EmployeeDisplayDto> getEmployeeDetails(){
				try
				{
				
				List<EmployeeDisplayDto>   empdetails=employeeRepository.getEmployeeDetails();
				System.out.println();
				return  empdetails;
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
					
				}
				return null;
		}





	@Override
	public List<UpadteEmployeeDto> getEmployeeDetailByUUiD(String uuid) {
		try {

			List<UpadteEmployeeDto> empdetails = employeeRepository.getEmployeeDetailByUUiD(uuid);

			return empdetails;
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public EmployeeEntity updateEmployee(EmployeeBean employeeBean) {
		EmployeeEntity accountEntity1 = beanToEntity(employeeBean);
		if (employeeBean == null) {
			throw new IllegalArgumentException("Account bean object is null");
		}

		EmployeeEntity savedEntity = employeeRepository.save(accountEntity1);

		return savedEntity;

	}



	@Override
	public EmployeeEntity delete(Integer employeeId) {
		log.info("service method{}", employeeId);
		EmployeeEntity optional = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new IllegalArgumentException("id not found"));
		optional.setIsDeleted(true);
		EmployeeBean entityToBean = entityToBean(optional);
		EmployeeEntity deletedEmployee = updateEmployee(entityToBean);
		
		return deletedEmployee;


		
	}
	
	@Override
	public EmployeeBean findByEmail(String employeeEmail) throws RecordsNotFoundException {
		log.info("Service findByEmail Method Start");

		Optional<EmployeeEntity> optionalEntity = employeeRepository.findByEmail(employeeEmail);
		if (optionalEntity.isPresent()) {
			EmployeeEntity entity = optionalEntity.get();
			log.info("Service findByEmail Method End");
			return entityToBean(entity);
		} else {
			throw new RecordsNotFoundException("employee not found with this email :" + employeeEmail);
		}
	}
	
}

