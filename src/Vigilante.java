public class Vigilante extends Thread {

    private final Thread[] vigilados;

    public Vigilante(Thread... vigilados) {
        super("vigilante");
        this.vigilados = vigilados;
    }

    @Override
    public void run() {
        while (true) {
            StringBuilder linea = new StringBuilder("[vigilante] ");
            for (Thread t : vigilados) {
                linea.append(t.getName()).append("=").append(t.getState()).append("  ");
            }
            System.out.println(linea);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
