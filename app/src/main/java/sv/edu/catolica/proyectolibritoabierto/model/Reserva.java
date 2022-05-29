package sv.edu.catolica.proyectolibritoabierto.model;

import java.util.Date;

public class Reserva {
    String book_title, email, fecha_prestamo, fecha_devolucion, transaction_type, state;
    int copy_quantity;
    Date fecha_prestamo_detalle, fecha_devolucion_detalle;

    public Reserva(){}

    public Reserva(String book_title, String email, String fecha_prestamo, String fecha_devolucion, String transaction_type, String state, int copy_quantity, Date fecha_prestamo_detalle, Date fecha_devolucion_detalle) {
        this.book_title = book_title;
        this.email = email;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
        this.transaction_type = transaction_type;
        this.state = state;
        this.copy_quantity = copy_quantity;
        this.fecha_prestamo_detalle = fecha_prestamo_detalle;
        this.fecha_devolucion_detalle = fecha_devolucion_detalle;
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

    public int getCopy_quantity() {
        return copy_quantity;
    }

    public void setCopy_quantity(int copy_quantity) {
        this.copy_quantity = copy_quantity;
    }

    public Date getFecha_prestamo_detalle() {
        return fecha_prestamo_detalle;
    }

    public void setFecha_prestamo_detalle(Date fecha_prestamo_detalle) {
        this.fecha_prestamo_detalle = fecha_prestamo_detalle;
    }

    public Date getFecha_devolucion_detalle() {
        return fecha_devolucion_detalle;
    }

    public void setFecha_devolucion_detalle(Date fecha_devolucion_detalle) {
        this.fecha_devolucion_detalle = fecha_devolucion_detalle;
    }
}
