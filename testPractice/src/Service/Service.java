package Service;

import Domain.MedicalAnalysis;
import Repository.MedicalAnalysisRepository;

import java.util.Collections;
import java.util.List;

public class Service {
    MedicalAnalysisRepository<MedicalAnalysis> service=new MedicalAnalysisRepository<>();
    public Service(){}
    public Service(MedicalAnalysisRepository<MedicalAnalysis> service){
        this.service=service;
    }
    public void add(MedicalAnalysis entity){
        service.add(entity);
    }
    public void delete(MedicalAnalysis entity){
        service.delete(entity);
    }
    public MedicalAnalysisRepository<MedicalAnalysis> view(){
        return service;
    }

    @Override
    public String toString() {
        List<MedicalAnalysis> list=service.getAll();
        Collections.sort(list);
        return list.toString();
    }
}
