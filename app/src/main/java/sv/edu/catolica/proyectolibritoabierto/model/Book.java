package sv.edu.catolica.proyectolibritoabierto.model;

public class Book {
    String title, author, book_image, summary;

    public Book(){}

    public Book(String title, String author, String book_image, String summary) {
        this.title = title;
        this.author = author;
        this.book_image = book_image;
        this.summary = summary;
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
}
