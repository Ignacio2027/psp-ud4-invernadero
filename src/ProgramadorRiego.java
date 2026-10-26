import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ProgramadorRiego extends Thread {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final long PERIODO_MS = 700;
    private static final double LITROS_POR_APERTURA = 2.5;

    private final int ciclos;
    private int ciclosHechos = 0;
    private double litros = 0;

    public ProgramadorRiego() {
        this(5);
    }

    public ProgramadorRiego(int ciclos) {
        super("riego");              // ← así se le da nombre al hilo (como el Cohete del Ejemplo 0A)
        this.ciclos = ciclos;
    }

    @Override
    public void run() {
        for (int i = 1; i <= ciclos; i++) {
            boolean abierta = (i % 2 == 1);      // ciclo 1 abre, 2 cierra, 3 abre...
            if (abierta) litros += LITROS_POR_APERTURA;
            ciclosHechos++;

            System.out.printf("[%s] hilo=%s | Riego ciclo %d: %s%n",
                    LocalTime.now().format(HORA),
                    Thread.currentThread().getName(),
                    i, abierta ? "ABIERTA" : "cerrada");

            try {
                Thread.sleep(PERIODO_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public String informe() {
        return String.format("Riego: ciclos=%d, litros=%.1f L", ciclosHechos, litros);
    }
}
