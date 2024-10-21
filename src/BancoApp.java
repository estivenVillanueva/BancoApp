//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Excepción personalizada para operaciones bancarias
class BancoException extends Exception {
    public BancoException(String mensaje) {
        super(mensaje);
    }
}

// Clase que representa una cuenta bancaria
class Cuenta {
    private String numero;
    private double saldo;
    private String titular;

    public Cuenta(String numero, String titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }
}

// Clase principal que maneja las operaciones bancarias
public class BancoApp {
    public void retirar(Cuenta cuenta, double monto) throws BancoException {
        if (monto <= 0) {
            throw new BancoException("El monto a retirar debe ser positivo");
        }
        if (cuenta.getSaldo() < monto) {
            throw new BancoException("Saldo insuficiente para realizar el retiro");
        }
        cuenta.setSaldo(cuenta.getSaldo() - monto);
    }

    public void depositar(Cuenta cuenta, double monto) throws BancoException {
        if (monto <= 0) {
            throw new BancoException("El monto a depositar debe ser positivo");
        }
        cuenta.setSaldo(cuenta.getSaldo() + monto);
    }

    public void transferir(Cuenta origen, Cuenta destino, double monto) throws BancoException {
        if (monto <= 0) {
            throw new BancoException("El monto a transferir debe ser positivo");
        }
        if (origen.getSaldo() < monto) {
            throw new BancoException("Saldo insuficiente para realizar la transferencia");
        }
        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);
    }

    public void mostrarEstadoCuenta(Cuenta cuenta) {
        System.out.println("\nEstado de cuenta:");
        System.out.println("Titular: " + cuenta.getTitular());
        System.out.println("Número de cuenta: " + cuenta.getNumero());
        System.out.println("Saldo actual: $" + cuenta.getSaldo());
    }

    public static void main(String[] args) {
        BancoApp banco = new BancoApp();

        // Crear cuentas de prueba
        Cuenta cuenta1 = new Cuenta("1001", "Juan Pérez", 1000.0);
        Cuenta cuenta2 = new Cuenta("1002", "María García", 500.0);

        try {
            // Mostrar estados iniciales
            banco.mostrarEstadoCuenta(cuenta1);
            banco.mostrarEstadoCuenta(cuenta2);

            // Probar depósito
            System.out.println("\nRealizando depósito de $300 en cuenta1...");
            banco.depositar(cuenta1, 300.0);
            banco.mostrarEstadoCuenta(cuenta1);

            // Probar retiro
            System.out.println("\nRealizando retiro de $200 de cuenta1...");
            banco.retirar(cuenta1, 200.0);
            banco.mostrarEstadoCuenta(cuenta1);

            // Probar transferencia
            System.out.println("\nTransfiriendo $400 de cuenta1 a cuenta2...");
            banco.transferir(cuenta1, cuenta2, 400.0);
            banco.mostrarEstadoCuenta(cuenta1);
            banco.mostrarEstadoCuenta(cuenta2);

            // Probar error de saldo insuficiente
            System.out.println("\nIntentando retirar más dinero del disponible...");
            banco.retirar(cuenta1, 2000.0);

        } catch (BancoException e) {
            System.out.println("\nError en la operación: " + e.getMessage());
        }
    }
}