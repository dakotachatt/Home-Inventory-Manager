package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Company;

/**
 *
 * @author Dakota
 */
public class CompanyDB {
    
    public List<Company> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Company> companies = em.createNamedQuery("Company.findAll", Company.class).getResultList();
            return companies;
        } finally {
            em.close();
        }
    }
    
    public Company getCompany(int companyId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Company company = em.find(Company.class, companyId);
            return company;
        } finally {
            em.close();
        }
    }
}
