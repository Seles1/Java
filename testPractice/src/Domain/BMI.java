package Domain;

public class BMI extends MedicalAnalysis {
    float bmi;
    public BMI(int id,String date, String status, float bmi) {
        super(id,date, status);
        this.bmi = bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getBmi() {
        return bmi;
    }
    @Override
    public String toString(){
        return id+','+date+','+status+','+bmi;
    }
}
