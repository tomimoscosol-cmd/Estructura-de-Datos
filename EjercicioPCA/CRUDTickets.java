public class CRUDTickets {

    private static final String ARCHIVO_TICKETS = "TicketsIngresados.txt";
    private static final String ARCHIVO_AUDITADOS = "auditados.txt";

    
    public Cola extraccionInformacionTicketsEspera(Archivo objA) {
        objA.abrirArchivoModoLectura(ARCHIVO_TICKETS);
        int cl = objA.contadorLineas(ARCHIVO_TICKETS);
        Tickets[] vec = new Tickets[cl];
        vec = objA.leerTickets();
        objA.cerrarModoLectura();

        Cola colaEspera = new Cola();
        for (int i = 0; i < vec.length; i++) {
            if (vec[i].obtenerEstado().equalsIgnoreCase("en espera")) {
                colaEspera.push(vec[i]);
            }
        }
        return colaEspera;
    }

    
    public Pila extraccionInformacionTicketsAtendidos(Archivo objA) {
        objA.abrirArchivoModoLectura(ARCHIVO_TICKETS);
        int cl = objA.contadorLineas(ARCHIVO_TICKETS);
        Tickets[] vec = new Tickets[cl];
        vec = objA.leerTickets();
        objA.cerrarModoLectura();

        int contAtendidos = 0;
        for (int i = 0; i < vec.length; i++) {
            if (vec[i].obtenerEstado().equalsIgnoreCase("atendido")) {
                contAtendidos++;
            }
        }

        Pila pilaAtendidos = new Pila(contAtendidos);
        for (int i = 0; i < vec.length; i++) {
            if (vec[i].obtenerEstado().equalsIgnoreCase("atendido")) {
                pilaAtendidos.push(vec[i]);
            }
        }
        return pilaAtendidos;
    }

  
    public void atencionTickets(Archivo objA, Cola c) {
        objA.abrirArchivoModoLectura(ARCHIVO_TICKETS);
        int cl = objA.contadorLineas(ARCHIVO_TICKETS);
        Tickets[] vec = new Tickets[cl];
        vec = objA.leerTickets();
        objA.cerrarModoLectura();

        Tickets tk;
        while (!c.isEmpty()) {
            tk = (Tickets) c.pop();
            tk.modificarEstado("atendido");

            for (int i = 0; i < vec.length; i++) {
                if (vec[i].obtenerIdTicket() == tk.obtenerIdTicket()) {
                    vec[i] = tk;
                }
            }

            reescribirArchivo(objA, vec);
        }
    }

    private void reescribirArchivo(Archivo objA, Tickets[] vec) {
        objA.eliminarArchivo(ARCHIVO_TICKETS);
        objA.abrirModoEscritura(ARCHIVO_TICKETS);
        for (int i = 0; i < vec.length; i++) {
            objA.escribir(vec[i].toString());
        }
        objA.cerrarModoEscritura();
    }


    public Pila auditoriaTickets(Archivo objA, Pila p) {
        Cola colaNoCumple = new Cola();
        Tickets tk;
        int contNoCumple = 0;

        objA.eliminarArchivo(ARCHIVO_AUDITADOS);
        objA.abrirModoEscritura(ARCHIVO_AUDITADOS);
        while (!p.isEmpty()) {
            tk = (Tickets) p.pop();
            if (tk.obtenerIdTicket() >= 1 && tk.obtenerIdTicket() <= 50) {
                objA.escribir(tk.toString());
            } else {
                colaNoCumple.push(tk);
                contNoCumple++;
            }
        }
        objA.cerrarModoEscritura();

        Pila pilaNoCumple = new Pila(contNoCumple);
        while (!colaNoCumple.isEmpty()) {
            tk = (Tickets) colaNoCumple.pop();
            pilaNoCumple.push(tk);
        }
        return pilaNoCumple;
    }
}
