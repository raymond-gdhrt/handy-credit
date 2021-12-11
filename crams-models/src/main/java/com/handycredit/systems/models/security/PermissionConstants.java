package com.handycredit.systems.models.security;

public final class PermissionConstants {
	private PermissionConstants() {

	}
	
	@CRAMSPermission(name = "Api user", description = "Has role for api users")
	public static final String PERM_API_USER = "Api User";

	@CRAMSPermission(name = "Business owner", description = "Has role for business owner")
	public static final String PERM_BUSINESS_OWNER = "Business owner";
        
        
        @CRAMSPermission(name = "Loan provider", description = "Has role for loan provider")
	public static final String PERM_LOAN_PROVIDER = "Loan provider";
	
}
