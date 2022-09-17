package Principal;
import java.util.ArrayList;
public class Aeroportos {
    //Atributos:
    private String nome;
    private String estado;
    private  double Lat;
    private  double Long;
    private final ArrayList<Trajetos> saidas;
    private  int indice;
    private boolean explorado;

    //Construtor:
    public Aeroportos() {
        Lat=0;
        Long=0;
        saidas = new ArrayList<>();
        nome = "";
        indice=-1;
        explorado = false;
        estado = "";
    }

    //Métodos Getter and Setter:
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getLat() {
        return Lat;
    }
    public void setLat(double lat) {
        Lat = lat;
    }
    public int getIndice() {
        return indice;
    }
    public void setIndice(int indice) {
        this.indice = indice;
    }
    public double getLong() {
        return Long;
    }
    public void setLong(double aLong) {
        Long = aLong;
    }
    public ArrayList<Trajetos> getSaidas() {return saidas;
    }
    public boolean foiExplorado() {return explorado;}
    public void setExplorado(boolean explorado) {
        this.explorado = explorado;
    }

    //Método par cálculo de distância entre aeroportos:
    public double getDist(Aeroportos aero){

        double dist = Math.pow(Math.sin((this.getLat() - aero.getLat()) / 2), 2);

        dist+=Math.cos(this.getLat()) * Math.cos(aero.getLat()) * Math.pow(Math.sin((this.getLong() - aero.getLong()) / 2), 2);

        dist = 2 * 6371 * Math.asin(Math.sqrt(dist));

        return dist;
    }
}
