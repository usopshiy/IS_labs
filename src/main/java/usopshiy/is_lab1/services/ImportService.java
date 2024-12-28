package usopshiy.is_lab1.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import usopshiy.is_lab1.db.ImportDAO;
import usopshiy.is_lab1.entity.Import;

import java.util.List;

@Stateless
public class ImportService {
    @EJB
    private ImportDAO importDAO;

    public void addImport(Import imp) {
        importDAO.addImport(imp);
    }

    public List<Import> getImports() {
        return importDAO.getImports();
    }
}
