package services;

import dataaccess.CompanyDB;
import java.util.List;
import models.Company;

/**
 *
 * @author Dakota
 */
public class CompanyService {
    
    public List<Company> getAll() {
        CompanyDB companyDB = new CompanyDB();
        List<Company> companies = companyDB.getAll();
        return companies;
    }
    
    public Company getCompany(int companyId) {
        CompanyDB companyDB = new CompanyDB();
        Company company = companyDB.getCompany(companyId);
        return company;
    }
}
