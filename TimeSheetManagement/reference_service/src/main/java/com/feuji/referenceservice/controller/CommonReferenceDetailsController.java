package com.feuji.referenceservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.ReferenceDetailsBean;
import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.constants.CommonConstants;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.InvalidInputException;
import com.feuji.referenceservice.exception.NameNotFoundException;
import com.feuji.referenceservice.exception.NoRecordFoundException;
import com.feuji.referenceservice.exception.RecordAlreadyExistsException;
import com.feuji.referenceservice.exception.RecordNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.service.CommonReferenceDetails;
import com.feuji.referenceservice.serviceImpl.CommonReferenceTypeImpl;

@RestController
@RequestMapping("/referencedetails")
@CrossOrigin(origins = "http://localhost:4200")
public class CommonReferenceDetailsController {
	private static Logger log = LoggerFactory.getLogger(CommonReferenceTypeImpl.class);

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo;
	@Autowired
	CommonReferenceDetails commonReferenceDetails;
	
	

//	@GetMapping("/getref/{typeName}")
//	 @CrossOrigin(origins = "http://localhost:4200")
//	
//	public ResponseEntity<List<ReferenceDetailsBean>> getReferenceTypeByName(@PathVariable String typeName)
//	{
//	try{
//		log.info(typeName);
//		
//		
//		List<ReferenceDetailsBean> getbyreferenceType = commonReferenceDetails.getDetailsByTypeId(typeName);
//		log.info(getbyreferenceType+"output");
//		return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK) ;	
//	}
//	catch (Exception e) {
//    return null;
//	}
//	}
	@GetMapping("/getref/{typeName}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<ReferenceDetailsBean>> getReferenceTypeByName(@PathVariable String typeName) {
	    try {
	        log.info("Fetching reference details for type: {}", typeName);
	        
	        List<ReferenceDetailsBean> referenceDetails = commonReferenceDetails.getDetailsByTypeId(typeName);
	        
	        log.info("Retrieved reference details: {}", referenceDetails);
	        
	        return ResponseEntity.ok(referenceDetails);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching reference details for type {}: {}", typeName, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

//	@GetMapping("/getbyid/{id}")
//	public ResponseEntity<CommonReferenceDetailsBean> getrefenceDetailsById(@PathVariable Integer id)
//	{
//	try{
//		log.info(id+"id");
//		
//		
//		 CommonReferenceDetailsBean referenceById = commonReferenceDetails.getReferenceById(id);
//		log.info(referenceById+"output");
//		return new ResponseEntity<>(referenceById, HttpStatus.OK) ;	
//	}
//	catch (Exception e) {
//    return null;
//	}
//	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<CommonReferenceDetailsBean> getrefenceDetailsById(@PathVariable Integer id) {
	    try {
	        log.info("Fetching reference details by ID: {}", id);
	        
	        CommonReferenceDetailsBean referenceById = commonReferenceDetails.getReferenceById(id);
	        
	        log.info("Retrieved reference details: {}", referenceById);
	        
	        return ResponseEntity.ok(referenceById);
	    } catch (Exception e) {
	        log.error("An error occurred while fetching reference details for ID {}: {}", id, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

//	@GetMapping("/{referenceTypeId}")
//    public List<CommonReferenceDetailsEntity> getByReferenceTypeId(@PathVariable Integer referenceTypeId) {
//        log.info("Fetching Common Reference Details for Reference Type ID: {}", referenceTypeId);
//        try {
//            return commonReferenceDetails.getReferenceTypeById(referenceTypeId);
//        } catch (Exception e) {
//            log.error("Error occurred while fetching Common Reference Details: {}", e.getMessage());
//            throw new RuntimeException("Failed to fetch Common Reference Details. Please try again later.");
//        }
//    }
	
	@PostMapping("/addSubSkill")
	public ResponseEntity<CommonReferenceDetailsBean> addingSubSkillCategory(
			@RequestBody CommonReferenceDetailsBean bean) 
	{
		log.info("addingSubSkillCategory is started in  commomreferenceDetailsController"+bean);
		CommonReferenceDetailsBean addSubSkillcategory=null;
		try {
			addSubSkillcategory = commonReferenceDetails.addSubSkillcategory(bean);
			log.info("addingSubSkillCategory is end in  commomreferenceDetailsController");
			return new ResponseEntity<CommonReferenceDetailsBean>(addSubSkillcategory, HttpStatus.ACCEPTED);
		} catch (ReferenceNotFoundException e) {
			log.info(e.getCause()+"ReferenceNotFoundException occured in commomreferenceDetailsController");
			return new ResponseEntity<CommonReferenceDetailsBean>(addSubSkillcategory, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/save")
    public ResponseEntity<CommonReferenceTypeBean> saveReferenceDetails(@RequestBody CommonReferenceDetailsBean referenceDetailsBean) {
		log.info("saveReferenceDetails is started in CommonReferenceDetailsController"+referenceDetailsBean);
		CommonReferenceTypeBean saveReferenceDetails =null;
		try {
			saveReferenceDetails = commonReferenceDetails.saveReferenceDetails(referenceDetailsBean);
			log.info("saveReferenceDetails is end in CommonReferenceDetailsController");
			return new ResponseEntity<CommonReferenceTypeBean>(saveReferenceDetails,HttpStatus.CREATED);
		}catch(RecordAlreadyExistsException e)
		{
			log.info(e.getMessage());
			return new ResponseEntity<CommonReferenceTypeBean>(saveReferenceDetails,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/updateIsDeleted")
    public ResponseEntity<CommonReferenceDetailsBean> updateIsDeleted(@RequestBody CommonReferenceDetailsBean commonReferenceDetailsBean){
		log.info("updateIsDeleted is started in CommonReferenceDetailsController");
		CommonReferenceDetailsBean updateIsDeleted =null;
		try {
			 updateIsDeleted = commonReferenceDetails.updateIsDeleted(commonReferenceDetailsBean);
				log.info("updateIsDeleted is end in CommonReferenceDetailsController");
			 return new ResponseEntity<CommonReferenceDetailsBean>(updateIsDeleted,HttpStatus.OK);
		} catch (InvalidInputException e) {
			log.info(e.getMessage());
			 return new ResponseEntity<CommonReferenceDetailsBean>(updateIsDeleted,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/deleteSubskill/{referenceDetailId}")
    public ResponseEntity<CommonReferenceDetailsBean> deleteSubSkill(@PathVariable Integer referenceDetailId) {
		log.info("deleteSubSkill is started in CommonReferenceDetailsController");
		CommonReferenceDetailsBean deletedBean=null;
		try {
			deletedBean = commonReferenceDetails.deleteSubSkill(referenceDetailId, true);
			log.info("deleteSubSkill is end in CommonReferenceDetailsController");
            return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean, HttpStatus.OK);
		
		}catch(RecordNotFoundException | NoRecordFoundException | InvalidInputException e){
			log.info(e.getMessage());
			return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean,HttpStatus.NOT_FOUND);
		}
	}
    
	
	@GetMapping("/getreference/{typeName}")
	public ResponseEntity<List<TechnicalSkillsBean>> getReferenceTypeByRefName(@PathVariable String typeName) {
		List<TechnicalSkillsBean> getbyreferenceType=null;
		try {
			log.info("Enterd into getReferenceTypeByName method of commomreferenceDetailsController  " + typeName);
			getbyreferenceType = commonReferenceDetails.getRefDetailsByTypeId(typeName);
			return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK);
		} catch (TechnicalSkillsNotFoundException e) {
			log.info("Getting TechnicalSkillsNotFoundException in commomreferenceDetailsController");
			return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK);
		}
	}
	@GetMapping("/getCategories/{category}")
	public ResponseEntity<List<String>> getCategories(@PathVariable String category) {
		log.info("getCategories start");
		List<String> categories = null;
		try {
			categories = commonReferenceDetails.getCategories(category);
			log.info("getCategories end");
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (CategoryNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(categories, HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/getByName/{name}")
	public ResponseEntity<Integer> getByName(@PathVariable String name) throws NameNotFoundException, javax.naming.NameNotFoundException {
		log.info("controller-getByName() started");
		try {
			log.info("Entered into getIdByName method of service in controller");
			int idByName = commonReferenceDetails.getIdByName(name);
			log.info("Coming out from getIdByName method of service in controller");
			return new ResponseEntity<Integer>(idByName, HttpStatus.OK);
		} catch (NameNotFoundException e) {
			log.info("NameNotFoundException occured in commomreferenceDetailsController");
			throw new NameNotFoundException("Name not found: " +name);
		}

	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<String> getById(@PathVariable int id) {
		log.info("getById() started");
		String name = null;
		try {
			name = commonReferenceDetails.getByid(id);
			log.info("getById() ended");
			return new ResponseEntity<String>(name, HttpStatus.OK);
		} catch (ReferenceNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<String>(name, HttpStatus.NOT_FOUND);

		}
	}

	
	@PutMapping("/deleteSkillCategory/{skillCategory}")
	public ResponseEntity<CommonReferenceDetailsBean> deleteSkillcategory(@PathVariable String skillCategory)
	{
		log.info("deleteSkillcategory is started in CommonReferenceDetailsController");
		CommonReferenceDetailsBean deletedBean=null;
		try {
			deletedBean = commonReferenceDetails.deleteSkillCategory(skillCategory, CommonConstants.TRUE);
			log.info("deleteSkillcategory is end in CommonReferenceDetailsController");
			return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean, HttpStatus.OK);
		}catch(RecordNotFoundException | NoRecordFoundException | InvalidInputException e)
		{
			log.info(e.getMessage());
			return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean,HttpStatus.NOT_FOUND);
		}
	}


}
