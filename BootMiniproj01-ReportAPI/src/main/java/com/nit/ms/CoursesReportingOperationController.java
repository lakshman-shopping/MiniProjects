package com.nit.ms;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nit.model.SearchInputs;
import com.nit.model.SearchResults;
import com.nit.service.ICourseMgmtService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/reporting/api")
@OpenAPIDefinition(info =
@Info(
		   title= "Reporting API",
		   version = "1.0",
		   description = "Reporting API supporting File Download operations",
		   license =@License(name= "NARESHIT", url = "http://nareshit.com"),
		   contact =@Contact(url ="http://gigantic=server.com", name = "suresh", email ="suresh@gigantic-server.com")
	)		
)
public class CoursesReportingOperationController {
	@Autowired
     private ICourseMgmtService courseService;
	
	@Operation(summary="Get Courses Information",
			responses= {
					@ApiResponse(description="courseInfo",
							content= @Content(mediaType="application/json",
							schema= @Schema(implementation=String.class))),
					@ApiResponse(responseCode="500", description="Wron url")})
	
	@GetMapping("/courses")
	public ResponseEntity<?>  fetchCourseCategories(){
		try {
			//use service
			Set<String> courseInfo = courseService.showAllCourseCategories();
			    return new ResponseEntity<Set<String>>(courseInfo, HttpStatus.OK); 
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//method
	   
	@GetMapping("/training-modes")
	public ResponseEntity<?>  fetchTrainingModes(){
		try {
			//use service
			Set<String> trainingModeInfo = courseService.showAllTrainingModes();
			    return new ResponseEntity<Set<String>>(trainingModeInfo, HttpStatus.OK); 
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//method
	
	@GetMapping("/faculties")
	public ResponseEntity<?>  fetchFaculties(){
		try {
			//use service
			Set<String> facultiesInfo = courseService.showAllFaculties();
			    return new ResponseEntity<Set<String>>(facultiesInfo, HttpStatus.OK); 
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//method
	
	@PostMapping("/search")
	public ResponseEntity<?>  fectchCoursesByFilters(@RequestBody SearchInputs inputs){
		try {
			//use Service
			List<SearchResults> list = courseService.showCoursesByFilters(inputs);
			      return new ResponseEntity<List<SearchResults>>(list, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//method
	
	@PostMapping("/pdf-report")
	public void  showPdfReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
		try {
			//set the response content type 
			res.setContentType("application/pdf");
			//set the content-disposition header  to response content going to browser as downloading file
			res.setHeader("Content-Disposition","attachment;fileName=courses.pdf");
			//use service
			courseService.generatePdfReport(inputs, res);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//method
	
	@PostMapping("/excel-report")
	public void  showExcelReport(@RequestBody SearchInputs inputs, HttpServletResponse res) {
		try {
			//set the response content type 
			res.setContentType("application/vnd.ms-excel");
			//set the content-disposition header  to response content going to browser as downloading file
			res.setHeader("Content-Disposition","attachment;fileName=courses.xls");
			//use service
			courseService.generateExcelReport(inputs, res);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//method
	
	@GetMapping("/all-pdf-report")
	public void  showPdfReportAllData(HttpServletResponse res) {
		try {
			//set the response content type 
			res.setContentType("application/pdf");
			//set the content-disposition header  to response content going to browser as downloading file
			res.setHeader("Content-Disposition","attachment;fileName=courses.pdf");
			//use service
			courseService.generatePdfReportAllData(res);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//method
	
	@GetMapping("/all-excel-report")
	public void  showExcelReportAllData(HttpServletResponse res) {
		try {
			//set the response content type 
			res.setContentType("application/vnd.ms-excel");
			//set the content-disposition header  to response content going to browser as downloading file
			res.setHeader("Content-Disposition","attachment;fileName=courses.xls");
			//use service
			courseService.generateExcelReportAllData(res);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//method
}//end class
