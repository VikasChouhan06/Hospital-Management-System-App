package models;


public class Appointment {
    private String patientName;
    private String doctorName;
    private String date;
    private String time;

    public Appointment() {} // Empty constructor needed for Firestore

    public Appointment(String patientName, String doctorName, String date, String time) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}
