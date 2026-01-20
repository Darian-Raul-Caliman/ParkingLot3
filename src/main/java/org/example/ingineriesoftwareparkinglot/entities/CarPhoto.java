package org.example.ingineriesoftwareparkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "car_photos")
public class CarPhoto {

    @Id
    // FIX: Changed to IDENTITY to match your Database Auto-Increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "FILETYPE")
    private String fileType;

    @Lob
    @Column(name = "FILECONTENT")
    private byte[] fileContent;

    @OneToOne
    @JoinColumn(name = "CAR_ID", unique = true)
    private Car car;

    public CarPhoto() {
    }

    public CarPhoto(String filename, String fileType, byte[] fileContent) {
        this.filename = filename;
        this.fileType = fileType;
        this.fileContent = fileContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}