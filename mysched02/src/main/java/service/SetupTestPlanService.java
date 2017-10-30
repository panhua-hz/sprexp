package service;

import java.util.Date;

import domain.TPlan;

public interface SetupTestPlanService {
	TPlan runOnce(Long testcaseId, Long env, Date runTime);
	TPlan repeatRun(Long testcaseId, Long env, String cronExp);
}
