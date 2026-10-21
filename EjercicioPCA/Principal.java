public class AppMesaAyuda {

    public static void main(String[] args) {

        
        Archivo objA = new Archivo();
        CRUDTickets objCrud = new CRUDTickets();

      
        Cola colaEspera = objCrud.extraccionInformacionTicketsEspera(objA);

        
        objCrud.atencionTickets(objA, colaEspera);

        
        Pila pilaAtendidos = objCrud.extraccionInformacionTicketsAtendidos(objA);

       
        Pila pilaNoCumple = objCrud.auditoriaTickets(objA, pilaAtendidos);

        
        System.out.println("Tickets pendientes de revision:");
        Tickets tk;
        while (!pilaNoCumple.isEmpty()) {
            tk = (Tickets) pilaNoCumple.pop();
            System.out.println(tk.toString());
        }
    }
}
