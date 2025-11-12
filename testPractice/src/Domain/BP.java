package Domain;

public class BP extends MedicalAnalysis {
    int systolic;
    int diastolic;

    public BP(int id,String date, String status, int systolic, int diastolic) {
        super(id,date, status);
        this.diastolic = diastolic;
        this.systolic = systolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public int getSystolic() {
        return systolic;
    }
    @Override
    public String toString(){
        return id+','+date+','+status+','+systolic+','+diastolic;
    }
}
