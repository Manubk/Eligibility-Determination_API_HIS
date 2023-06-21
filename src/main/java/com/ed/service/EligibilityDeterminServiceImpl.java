package com.ed.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Delayed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ed.constants.AppConstants;
import com.ed.dto.EligibilityDeterminDto;
import com.ed.entity.ApplicationRegistration;
import com.ed.entity.CoTriggers;
import com.ed.entity.DcCase;
import com.ed.entity.DcChildren;
import com.ed.entity.DcIncome;
import com.ed.entity.EligibilityDeterminEntity;
import com.ed.entity.Plan;
import com.ed.repo.ApplicationRegistrationRepo;
import com.ed.repo.CoTriggersRepo;
import com.ed.repo.DcCaseRepo;
import com.ed.repo.DcChildrenRepo;
import com.ed.repo.DcEducationRepo;
import com.ed.repo.DcIncomeRepo;
import com.ed.repo.EligibilityDeterminRepo;
import com.ed.repo.PlanRepo;
import com.ed.serviceinterface.IEligibilityDeterminService;

@Service
public class EligibilityDeterminServiceImpl implements IEligibilityDeterminService {

	private static final Logger log = LoggerFactory.getLogger(EligibilityDeterminServiceImpl.class);

	@Autowired
	private DcCaseRepo caseRepo;

	@Autowired
	private DcIncomeRepo incomeRepo;

	@Autowired
	private DcEducationRepo educationRepo;

	@Autowired
	private DcChildrenRepo childrenRepo;

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private EligibilityDeterminRepo eligibilityRepo;

	@Autowired
	private ApplicationRegistrationRepo appRegRepo;

	@Autowired
	private CoTriggersRepo triggersRepo;

	/*
	 * It will check is Eligibility is already present or not If present it will
	 * send the same value
	 */
	@Override
	public EligibilityDeterminDto saveEligDtl(Long CaseNum) {
		log.info("saveEligDtl caseNum = " + CaseNum);

		EligibilityDeterminEntity eligibility = eligibilityRepo.findByCaseNum(CaseNum);
		EligibilityDeterminDto eligibilityDeterminDto = null;

		if (eligibility != null) {
			EligibilityDeterminDto eligibilityDto = new EligibilityDeterminDto();
			BeanUtils.copyProperties(eligibility, eligibilityDto);
			return eligibilityDto;
		}

		Integer planId = null;
		String planName = null;
		Plan plan = null;
		DcCase dcCase = caseRepo.findByCaseNum(CaseNum);

		if (dcCase != null) {
			planId = dcCase.getPlanId();
			Optional<Plan> OPlan = planRepo.findById(planId);

			if (OPlan.isPresent()) {
				plan = OPlan.get();
			}

		}

		eligibilityDeterminDto = determineEligibility(plan, CaseNum);
		eligibility = (eligibility == null)?new EligibilityDeterminEntity():eligibility;
		BeanUtils.copyProperties(eligibilityDeterminDto, eligibility);
	
		EligibilityDeterminEntity savedEligibility = eligibilityRepo.save(eligibility);
		log.info(eligibilityDeterminDto.toString());
		
		CoTriggers triggers = new CoTriggers();
		triggers.setStatus(AppConstants.PENDING);
		triggers.setCaseNum(CaseNum);
		
		CoTriggers savedTriggers = triggersRepo.save(triggers);
		log.info(triggers.toString());

		return eligibilityDeterminDto;
	}

	private EligibilityDeterminDto determineEligibility(Plan plan, Long caseNum) {
		log.info("determineEligibility planName = "+plan.getPlanName());

		EligibilityDeterminDto eligDeterDto = new EligibilityDeterminDto();
		eligDeterDto.setPlanName(plan.getPlanName());
		eligDeterDto.setCaseNum(caseNum);

		DcIncome income = incomeRepo.findByCaseNum(caseNum);
		Optional<DcCase> ODcCase = caseRepo.findById(caseNum);
		
		ApplicationRegistration appReg = null;
		Integer age = 0;
		Double totalIncome =0.0;

		if (ODcCase.isPresent()) {

			Optional<ApplicationRegistration> OAppReg = appRegRepo.findById(ODcCase.get().getAppId());
			if (OAppReg.isPresent()) {

				appReg = OAppReg.get();
				age = getAge(appReg.getDob());
			}

		}
		
		totalIncome = income.getEmpIncome()+income.getPropertyIncome()+income.getPropertyIncome();
		
		
		boolean validIncome = false;
		boolean validChildrens = false;
		boolean validChildrensAge = false;
		boolean validAge = false;
	 
		String denialReason = "";
		
		switch (plan.getPlanName()) {
		
		case "SNAP" :
			
			if(age >= 18 && age <=49) {
				validAge = true;
			}else
				denialReason += AppConstants.AGE_LIMIT+",";
			
			if (totalIncome <= plan.getIncomeLimit()) {
				validIncome = true;
			} else
				denialReason += AppConstants.MORE_INCOME;
			
			
			if(validAge && validIncome) {
				eligDeterDto.setPlanStartDate(LocalDate.now());
				eligDeterDto.setPlanStatus(AppConstants.ACCEPTED);
				eligDeterDto.setBenefitAmount(plan.getBenefitAmt());
				eligDeterDto.setPlanEndDate(LocalDate.now().plusMonths(6));
				eligDeterDto.setDenialReason(AppConstants.PERFECT);
			}else {
				eligDeterDto.setPlanStartDate(LocalDate.now());
				eligDeterDto.setPlanEndDate(LocalDate.now());
				eligDeterDto.setDenialReason(denialReason);
				eligDeterDto.setPlanStatus(AppConstants.DENIED);
				eligDeterDto.setBenefitAmount(0.00);
			}
			
			break;

			
		case "CCAP" :
			
			List<DcChildren> childrens = childrenRepo.findByCaseNum(caseNum);
			
//			boolean validIncome = false;
//			boolean validChildrens = false;
//			boolean validChildrensAge = false;
			
			int validChilds = 0;
			
			if(childrens.size() > 0) {
				validChildrens = true;
				
				if(totalIncome <= plan.getIncomeLimit()) {
					validIncome = true;
				}else
					denialReason += AppConstants.MORE_INCOME+",";
				
				
				for(DcChildren child : childrens) {
					int childAge = getAge(child.getChildrenDob());
					
					if(childAge >=5 && childAge <= 13) {
						validChilds++;
					}
					
				}
				
				if(validChilds == 0)
					denialReason += AppConstants.CHILDREN_AGE_LIMIT;
				else
					validChildrensAge = true;
				
			}else
				denialReason = AppConstants.NO_CHILDREN+",";
			
				eligDeterDto.setPlanStartDate(LocalDate.now());
				
			if (validIncome && validChildrensAge && validChildrens) {
				eligDeterDto.setBenefitAmount(plan.getBenefitAmt() * validChilds);
				eligDeterDto.setPlanEndDate(LocalDate.now().plusMonths(6));
				eligDeterDto.setDenialReason(AppConstants.PERFECT);
				eligDeterDto.setPlanStatus(AppConstants.ACCEPTED);
			}else {
				eligDeterDto.setPlanEndDate(LocalDate.now());
				eligDeterDto.setDenialReason(denialReason);
				eligDeterDto.setPlanStatus(AppConstants.DENIED);
				eligDeterDto.setBenefitAmount(0.0);
			}
			
			break;

		case "MEDICAID" :
			
			if ( age >=18 && age<= 64) {
				validAge = true;
			} else
				denialReason += AppConstants.AGE_LIMIT+",";
			
			if(totalIncome <= plan.incomeLimit) 
				validIncome = true;
			else
				denialReason += AppConstants.MORE_INCOME+",";
			
			
			if(validAge && validIncome) {
				eligDeterDto.setBenefitAmount(plan.getBenefitAmt());
				eligDeterDto.setPlanEndDate(LocalDate.now().plusMonths(6));
				eligDeterDto.setDenialReason(AppConstants.PERFECT);
				eligDeterDto.setPlanStatus(AppConstants.ACCEPTED);
			}else {
				eligDeterDto.setPlanStatus(AppConstants.DENIED);
				eligDeterDto.setDenialReason(denialReason);
				eligDeterDto.setPlanEndDate(LocalDate.now());
			}
				eligDeterDto.setPlanStartDate(LocalDate.now());
			
			break;

		case "MEDICARE" :
			
			if(age >= 65) {
				eligDeterDto.setBenefitAmount(plan.getBenefitAmt());
				eligDeterDto.setPlanEndDate(LocalDate.now().plusMonths(6));
				eligDeterDto.setDenialReason(AppConstants.PERFECT);
				eligDeterDto.setPlanStatus(AppConstants.ACCEPTED);
			}else {
				eligDeterDto.setDenialReason(AppConstants.AGE_LIMIT);
				eligDeterDto.setPlanStatus(AppConstants.DENIED);
				eligDeterDto.setPlanEndDate(LocalDate.now());
				eligDeterDto.setBenefitAmount(0.0);
			}
				eligDeterDto.setPlanStartDate(LocalDate.now());
				
			break;
			
		default:
				
		}
		return eligDeterDto;
	}
	
	
	private Integer getAge(LocalDate dob) {
		log.info("getAge dob = "+dob);
		return Period.between(dob, LocalDate.now()).getYears();
	}
	
}
