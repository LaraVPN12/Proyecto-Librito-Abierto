package sv.edu.catolica.proyectolibritoabierto.model;

public class Transaccion {
    String transaction_type;

    public Transaccion(){}

    public Transaccion(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
