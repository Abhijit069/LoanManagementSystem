package com.youtube.jwt.controller;

import com.youtube.jwt.entity.Loan;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.LoanRepository;
import com.youtube.jwt.service.RoleService;
import com.youtube.jwt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }
    @PostMapping({"/registerNewAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public User registerNewAdmin(@RequestBody User user) {
    	
        Role role = roleService.adminRole();
        User adminUser =user ;
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(role);
        adminUser.setRole(adminRole);
        return userService.registerNewUser(adminUser);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }
    
    @PostMapping("/saveLoan")
    @PreAuthorize("hasRole('Admin')")
    public Loan saveLoan(@RequestBody Loan loan)throws Exception{
    	return loanRepository.save(loan);
        //return "customer saved";
    }
    
    @GetMapping("/getAllLoans")
    public List<Loan> getAllLoans(){
   	 List<Loan> findAll = loanRepository.findAll();
   	 
   	 return findAll;
   }
    
    @GetMapping("/searchLoanByLoanId/{no}")
    public ArrayList<Loan> searchLoanByLoanId(@PathVariable String no){
   	 Loan l = loanRepository.getById(no);
   	 if(l==null)
   	 {
   		ArrayList<Loan> le = new ArrayList<Loan>();
   		return le;
   	 }
   	 ArrayList<Loan> loans = new ArrayList<Loan>();
   	 loans.add(l);
   	 return loans;
   }
    @GetMapping("/doesExist/{no}")
    public String doesExist(@PathVariable String no){
   	 Loan l = loanRepository.getById(no);
   	 if(l == null) {return "false";}
   	return "true";
   }
    
    @GetMapping("/searchLoanByFname/{fname}")
    public ArrayList<Loan> searchLoanByFname(@PathVariable String fname){
    	 ArrayList<Loan> byFname = loanRepository.getByFname(fname);
    	 if(byFname.size()==0) {
    		 ArrayList<Loan> byFnameE = new ArrayList<Loan>();
    		 return byFnameE;
    	 }else {
    		 return byFname;
    	 }
    }
    
    @GetMapping("/searchLoanByLname/{lname}")
    public ArrayList<Loan> searchLoanByLname(@PathVariable String lname){
    	 ArrayList<Loan> byLname = loanRepository.getByLname(lname);
    	 if(byLname.size()==0) {
    		 ArrayList<Loan> byLnameE = new ArrayList<Loan>();
    		 return byLnameE;
    	 }else {
    		 return byLname;
    	 }
    }
    @GetMapping("/searchLoanByFullName/{name}")
    public ArrayList<Loan> searchLoanByFullName(@PathVariable String name){
    	String nameArr[] = name.split(" ");
    	String fname = nameArr[0];
    	String lname = nameArr[1];
    	 ArrayList<Loan> byFname = loanRepository.getByFname(fname);
    	 if(byFname.size()==0) {
    		 ArrayList<Loan> byFnameE = new ArrayList<Loan>();
    		 return byFnameE;
    	 }else {
    		 ArrayList<Loan> details = new ArrayList<>();
    		 for(Loan l : byFname)
    		 {
    			 if(l.getLName().equals(lname)){details.add(l);}
    		 }
    		 return details;
    	 }
    }

    

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}
