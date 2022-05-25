package sv.edu.catolica.proyectolibritoabierto.model;

public class Loan {
    String book_title, email, fecha_prestamo, fecha_devolucion, transaction_type, state;
    int copy_quantity;

    public Loan(){}

    public Loan(String book_title, String email, String fecha_prestamo, String fecha_devolucion, String transaction_type, String state, int copy_quantity) {
        this.book_title = book_title;
        this.email = email;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
        this.copy_quantity = copy_quantity;
        this.transaction_type = transaction_type;
        this.state = state;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public int getCopy_quantity() {
        return copy_quantity;
    }

    public void setCopy_quantity(int copy_quantity) {
        this.copy_quantity = copy_quantity;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
