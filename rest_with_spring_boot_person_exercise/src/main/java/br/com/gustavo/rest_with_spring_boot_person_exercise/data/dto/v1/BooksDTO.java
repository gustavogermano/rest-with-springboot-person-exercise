package br.com.gustavo.rest_with_spring_boot_person_exercise.data.dto.v1;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class BooksDTO extends RepresentationModel<BooksDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String author;

    private Date launchDate;

    private double price;

    private String title;

    public BooksDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BooksDTO book = (BooksDTO) o;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getLaunchDate(), book.getLaunchDate()) && Objects.equals(getPrice(), book.getPrice()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getLaunchDate(), getPrice(), getTitle());
    }
}

