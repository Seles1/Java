package Repository;

import Domain.MedicalAnalysis;

import java.util.ArrayList;
import java.util.List;

public class MedicalAnalysisRepository<MedicalAnalysis>{
    List<MedicalAnalysis> repo=new ArrayList<>();
    public void add(MedicalAnalysis entity){
        repo.add(entity);
    }
    public void delete(MedicalAnalysis entity){
        repo.remove(entity);
    }
    public List<MedicalAnalysis> getAll(){
        return repo;
    }
}
