package sv.edu.catolica.proyectolibritoabierto.model;

public class Categorie {
    String id_categorie, categorie, image_categorie;

    public Categorie(){}

    public Categorie(String id_categorie, String categorie, String image_categorie) {
        this.id_categorie = id_categorie;
        this.categorie = categorie;
        this.image_categorie = image_categorie;
    }

    public String getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(String id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage_categorie() {
        return image_categorie;
    }

    public void setImage_categorie(String image_categorie) {
        this.image_categorie = image_categorie;
    }
}
