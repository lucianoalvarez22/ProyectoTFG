package com.example.demo.controllerUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.Asientos;
import com.example.demo.entity.Company;
import com.example.demo.entity.Reservas;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Salas;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdmin.IAsientosService;
import com.example.demo.serviceAdmin.IMapaService;
import com.example.demo.serviceAdmin.ISalasService;
import com.example.demo.serviceAdminCompany.ICompanyServiceAdminComp;
import com.example.demo.serviceUser.IReservaService;
import com.example.demo.serviceUser.IUserService;

@Controller
public class UserController {

    @Autowired
    private UserSessionLog userSession;

    @Autowired
    private IUserService userGeneralService;

    @Autowired
    private IMapaService mapaService; 

    @Autowired
    private ISalasService salaService;

    @Autowired
    private IAsientosService asientoService;

    @Autowired
    private IReservaService reservaService;
    
    @Autowired
	private ICompanyServiceAdminComp companyServicioAdmComp;

    @GetMapping("/myPerfilUser")
    public String myPerfilUser(Model model) {

        Usuarios userLogueado = userSession.getUser();
        Long userId = userLogueado.getUserId();

        Roles rol = userLogueado.getRolLevel();
        Long rolIDUsuario = rol.getRolLevel();

        Usuarios myPerfilUser = userGeneralService.getUsuarioGeneralById(userId); 

        model.addAttribute("usuarioGeneral", myPerfilUser);
        model.addAttribute("rolID", rolIDUsuario);

        return "userGeneral/miPerfil";
    }
    
    
    @GetMapping("/myCompanyUser")
    public String myPCompanyUser(Model model) {

        Usuarios userLogueado = userSession.getUser();
        Company empresa = userLogueado.getCompanyId();

        Roles rol = userLogueado.getRolLevel();
        Long rolIDUsuario = rol.getRolLevel();

        Long idCompany = empresa.getCompanyId();
        List<Company> listMyCompany = companyServicioAdmComp.getMyCompany(idCompany);

        model.addAttribute("myCompanyUser", listMyCompany);
        model.addAttribute("rolID", rolIDUsuario);

        return "userGeneral/myCompanyUser";
    }

    @GetMapping("/verSalas")
    public String verSalas(@RequestParam(value = "search", required = false) String search, Model model) {
        Usuarios userLogueado = userSession.getUser();
        Company company = userLogueado.getCompanyId();
        
        

        Roles rol = userLogueado.getRolLevel();
        Long rolIDUsuario = rol.getRolLevel();
        
        List<Salas> salas;
        if (search != null && !search.isEmpty()) {
            salas = mapaService.searchSalasByNumberAndCompany(search, company.getCompanyId());
        } else {
            salas = mapaService.getSalasByCompany(company.getCompanyId());
        }

        //List<Salas> salas = mapaService.getSalasByCompany(company.getCompanyId());

        model.addAttribute("salas", salas);
        model.addAttribute("search", search);
        model.addAttribute("rolID", rolIDUsuario);
        return "userGeneral/listSalasUserGeneral";
    }

    @GetMapping("/verAsientosUserGeneral/{salaId}")
    public String pintarAsientos(@PathVariable Long salaId, 
                                 @RequestParam(required = false) String fechaInicio, 
                                 @RequestParam(required = false) String fechaFin, 
                                 Model model) {
        Usuarios userLogueado = userSession.getUser();
        Roles rol = userLogueado.getRolLevel();
        Long rolIDUsuario = rol.getRolLevel();
        Salas sala = salaService.getSalaById(salaId);
        List<Asientos> asientos = sala.getAsientos();

        model.addAttribute("sala", sala);
        model.addAttribute("asientos", asientos);
        model.addAttribute("rolID", rolIDUsuario);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        
        Company company = userLogueado.getCompanyId();
        model.addAttribute("horaApertura", company.getHoraApertura().toString());
        model.addAttribute("horaCierre", company.getHoraCierre().toString());

        boolean mostrarMapa = fechaInicio != null && fechaFin != null;

        if (mostrarMapa) {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);
            long asientosLibres = asientos.stream().filter(asiento -> isAsientoDisponible(asiento, inicio, fin)).count();
            long asientosOcupados = asientos.size() - asientosLibres;
            model.addAttribute("asientosLibres", asientosLibres);
            model.addAttribute("asientosOcupados", asientosOcupados);
        } else {
            model.addAttribute("asientosLibres", asientos.size());
            model.addAttribute("asientosOcupados", 0);
        }

        model.addAttribute("mostrarMapa", mostrarMapa);

        return "userGeneral/verAsientosUserGeneral";
    }

    @PostMapping("/reservarAsiento")
    public String reservarAsiento(@RequestParam("asientoId") Long asientoId,
                                  @RequestParam("fechaInicio") String fechaInicio,
                                  @RequestParam("fechaFin") String fechaFin,
                                  @RequestParam("salaId") Long salaId,
                                  RedirectAttributes redirectAttributes) {
        Usuarios userLogueado = userSession.getUser();
        Asientos asiento = asientoService.getAsientosById(asientoId);
        Salas sala = asiento.getSala();
        LocalDateTime entrada = LocalDateTime.parse(fechaInicio);
        LocalDateTime salida = LocalDateTime.parse(fechaFin);

        if (isAsientoDisponible(asiento, entrada, salida)) {
            asiento.setAsientoEstado(false); // Cambia el estado a ocupado
            asientoService.saveAsiento(asiento);
            Reservas reserva = new Reservas();
            reserva.setAsiento(asiento);
            reserva.setUsuario(userLogueado);
            reserva.setFechaEntrada(entrada);
            reserva.setFechaSalida(salida);
            reserva.setReservaCompletada(true); // La reserva se completa
            reserva.setSala(sala);
            reserva.setMapa(sala.getMapaId());
            reservaService.saveReserva(reserva);
            redirectAttributes.addFlashAttribute("reservaExito", "Reserva gestionada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("reservaError", "El asiento no está disponible en el rango de fecha y hora seleccionados.");
        }

        return "redirect:/misReservas";
    }

    @GetMapping("/misReservas")
    public String misReservas(@RequestParam(value = "fechaEntrada", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada, Model model) {
        Usuarios userLogueado = userSession.getUser();
        Roles rol = userLogueado.getRolLevel();
        Long rolIDUsuario = rol.getRolLevel();
        
        List<Reservas> reservas;
        if (fechaEntrada != null) {
            reservas = reservaService.searchReservasByFechaEntrada(userLogueado, fechaEntrada);
        } else {
            reservas = reservaService.getReservasByUsuario(userLogueado);
        }
        
       
        
        model.addAttribute("reservas", reservas);
        model.addAttribute("fechaEntrada", fechaEntrada != null ? fechaEntrada.toString() : "");
        model.addAttribute("rolID", rolIDUsuario);
        return "userGeneral/misReservas";
    }

    @GetMapping("/deleteReserva/{id}")
    public String deleteReserva(@PathVariable(name= "id") Long id, RedirectAttributes redirectAttributes) {
        Reservas reserva = reservaService.getReservaById(id);
        if (reserva != null) {
            Asientos asiento = reserva.getAsiento();
            if (asiento != null) {
                asiento.setAsientoEstado(true); // Cambia el estado a libre
                asientoService.saveAsiento(asiento); // Guarda el estado del asiento actualizado
            }
            reservaService.eliminarReserva(id); // Eliminar la reserva
        }
        redirectAttributes.addFlashAttribute("reservaEliminadaExito", "Reserva eliminada exitosamente. Vuelva a reservar si así lo desea");
        return "redirect:/misReservas";
    }

    @GetMapping("/buscarAsientos")
    public String buscarAsientos(@RequestParam("fecha") String fecha, 
                                 @RequestParam("tipoReserva") String tipoReserva, 
                                 @RequestParam("salaId") Long salaId,
                                 @RequestParam("salaNumero") String salaNumero,
                                 @RequestParam(name = "horaInicio", required = false) String horaInicioParam,
                                 @RequestParam(name = "horaFin", required = false) String horaFinParam,
                                 @RequestParam(name = "fechaFormateada", required = false) String fechaFormateada,
                                 @RequestParam(name = "tipoReservaTexto", required = false) String tipoReservaTexto,
                                 Model model) {
        Company company = userSession.getUser().getCompanyId();
        LocalTime horaApertura = company.getHoraApertura();
        LocalTime horaCierre = company.getHoraCierre();
        LocalDate date = LocalDate.parse(fecha);
        LocalDateTime fechaInicio = null;
        LocalDateTime fechaFin = null;

        switch (tipoReserva) {
            case "completa":
                fechaInicio = date.atTime(horaApertura);
                fechaFin = date.atTime(horaCierre);
                break;
            case "media_manana":
                fechaInicio = date.atTime(horaApertura);
                fechaFin = fechaInicio.plusHours(4);
                break;
            case "media_tarde":
                fechaFin = date.atTime(horaCierre);
                fechaInicio = fechaFin.minusHours(4);
                break;
            case "por_horas":
                if (horaInicioParam != null && horaFinParam != null) {
                    fechaInicio = LocalDateTime.parse(fecha + "T" + horaInicioParam);
                    fechaFin = LocalDateTime.parse(fecha + "T" + horaFinParam);
                } else {
                    throw new IllegalArgumentException("Para reservas por horas, se deben proporcionar horaInicio y horaFin");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de reserva no válido");
        }

        Salas sala = salaService.getSalaById(salaId);
        List<Asientos> asientos = sala.getAsientos();

        for (Asientos asiento : asientos) {
            boolean disponible = isAsientoDisponible(asiento, fechaInicio, fechaFin);
            asiento.setAsientoEstado(disponible);
        }

        long asientosLibres = asientos.stream().filter(Asientos::isAsientoEstado).count();
        long asientosOcupados = asientos.size() - asientosLibres;
        
        if (fechaFormateada == null || tipoReservaTexto == null) {
            // Formatear la fecha seleccionada si no se proporciona
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM yyyy", new Locale("es", "ES"));
            fechaFormateada = date.format(formatter);
            tipoReservaTexto = tipoReserva.equals("completa") ? "Jornada Completa" :
                               tipoReserva.equals("media_manana") ? "Media Jornada Mañana" :
                               tipoReserva.equals("media_tarde") ? "Media Jornada Tarde" :
                               "Por Horas";
        }

        model.addAttribute("sala", sala);
        model.addAttribute("salaNumero", salaNumero);
        model.addAttribute("asientos", asientos);
        model.addAttribute("asientosLibres", asientosLibres);
        model.addAttribute("asientosOcupados", asientosOcupados);
        model.addAttribute("rolID", userSession.getUser().getRolLevel().getRolLevel());
        model.addAttribute("fechaInicio", fechaInicio.toString());
        model.addAttribute("fechaFin", fechaFin.toString());
        model.addAttribute("fecha", fecha); // Agregar fecha al modelo
        model.addAttribute("tipoReserva", tipoReserva);
        model.addAttribute("fechaFormateada", fechaFormateada);
        model.addAttribute("tipoReservaTexto", tipoReservaTexto);
        model.addAttribute("horaApertura", horaApertura.toString());
        model.addAttribute("horaCierre", horaCierre.toString());
        model.addAttribute("mostrarMapa", true);
        return "userGeneral/verAsientosUserGeneral";
    }

    private boolean isAsientoDisponible(Asientos asiento, LocalDateTime entrada, LocalDateTime salida) {
        List<Reservas> reservas = asiento.getReservas();
        for (Reservas reserva : reservas) {
            if (entrada.isBefore(reserva.getFechaSalida()) && salida.isAfter(reserva.getFechaEntrada())) {
                return false;
            }
        }
        return true;
    }
}
