import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Sensor implements Runnable {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    protected final String nombre;
    protected final String unidad;
    protected final long periodoMs;
    protected final int numLecturas;
    protected final double umbralMin;
    protected final double umbralMax;

    // Estadísticas PROPIAS de este sensor
    private int lecturasHechas = 0;
    private double suma = 0;
    private double minimo = Double.MAX_VALUE;
    private double maximo = -Double.MAX_VALUE;
    private int alertas = 0;

    public Sensor(String nombre, String unidad, long periodoMs, int numLecturas,
                  double umbralMin, double umbralMax) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.periodoMs = periodoMs;
        this.numLecturas = numLecturas;
        this.umbralMin = umbralMin;
        this.umbralMax = umbralMax;
    }

    // Cada sensor concreto dice cómo mide
    public abstract double medir();

    @Override
    public void run() {
        for (int i = 1; i <= numLecturas; i++) {
            double valor = medir();
            boolean fueraDeRango = valor < umbralMin || valor > umbralMax;

            // actualizar estadísticas
            lecturasHechas++;
            suma += valor;
            if (valor < minimo) minimo = valor;
            if (valor > maximo) maximo = valor;
            if (fueraDeRango) alertas++;

            System.out.printf("[%s] hilo=%s | %s = %.1f %s%s%n",
                    LocalTime.now().format(HORA),
                    Thread.currentThread().getName(),   // ← así se sabe el hilo (Sensor no hereda de Thread)
                    nombre, valor, unidad,
                    fueraDeRango ? "  ⚠ ALERTA" : "");

            try {
                Thread.sleep(periodoMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public String informe() {
        double media = lecturasHechas > 0 ? suma / lecturasHechas : 0;
        double min = lecturasHechas > 0 ? minimo : 0;
        double max = lecturasHechas > 0 ? maximo : 0;
        return String.format("%s: lecturas=%d, media=%.2f %s, mín=%.1f, máx=%.1f, alertas=%d",
                nombre, lecturasHechas, media, unidad, min, max, alertas);
    }
}
