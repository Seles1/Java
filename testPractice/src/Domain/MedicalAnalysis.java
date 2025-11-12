package Domain;


public class MedicalAnalysis implements Comparable<MedicalAnalysis>{
    int id;
    String date = "";
    String status = "";
    public MedicalAnalysis(int id,String date, String status) {
        this.date = date;
        this.status = status;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(MedicalAnalysis other){
        return this.getDate().compareTo(other.getDate());
    }
    @Override
    public String toString(){
        return id+','+date+','+status;
    }

}
