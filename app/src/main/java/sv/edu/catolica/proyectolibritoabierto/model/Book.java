package sv.edu.catolica.proyectolibritoabierto.model;

import java.io.Serializable;

public class Book implements Serializable {
    String id_book, title, author, book_image, summary, categorie;

    public Book(){}

    public Book(String id_book, String title, String author, String book_image, String summary, String categorie) {
        this.id_book = id_book;
        this.title = title;
        this.author = author;
        this.book_image = book_image;
        this.summary = summary;
        this.categorie = categorie;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
