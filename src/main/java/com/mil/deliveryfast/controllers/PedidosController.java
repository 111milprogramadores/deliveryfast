package com.mil.deliveryfast.controllers;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mil.deliveryfast.models.Ciudad;
import com.mil.deliveryfast.models.Pedido;
import com.mil.deliveryfast.models.Usuario;
import com.mil.deliveryfast.repositories.FormasDePagoRepository;
import com.mil.deliveryfast.repositories.PedidosRepository;
import com.mil.deliveryfast.repositories.UsuariosRepository;

@Controller
@RequestMapping("pedidos")
public class PedidosController {
	
	public static final BigDecimal MONTO_COMISION = new BigDecimal(35);
	
	private @Autowired FormasDePagoRepository formasDePagoRepository;
	private @Autowired UsuariosRepository usuariosRepository;
	private @Autowired PedidosRepository pedidosRepository;
	
	@GetMapping
	public void todos(Model model) {
		model.addAttribute("pedidos", pedidosRepository.findAll());
	}
	
	@GetMapping("nuevo")
	public void pedirLoQueSea(Model model) {
		// a los fines del ejercicio dejamos fijo el número del
		// usuario que buscaremos en la base de datos
		// considerando que su cuenta ya ha sido creada con
		// anterioridad y ha iniciado sesión en la aplicación
		Usuario pabloAuster = obtenerUsuarioEnSesion();
		
		// agregamos las formas de pago disponible a nuestra vista
		// para poder mostrarlas en el combo de selección
		model.addAttribute("formasDePago", formasDePagoRepository.findAll());
		
		// pasamos a nuestra página un nuevo Pedido "en blanco"
		// creado para el usuario "Pablo Auster"
		model.addAttribute("pedido", new Pedido(pabloAuster));
		
		// pasamos a nuestra vista el monto de la comisión para
		// poder mostrarlo
		model.addAttribute("montoComision", MONTO_COMISION);
	}
	
	@PostMapping("nuevo")
	public String procesarPedidoDeLoQueSea(@Valid Pedido pedido, BindingResult resultado, Model model) {
		String retorno = "pedidos/creado";
		
		// asignamos nuevamente el usuario en sesión al pedido
		// por motivos de seguridad
		Usuario pabloAuster = obtenerUsuarioEnSesion();
		pedido.setUsuario(pabloAuster);
		
		// si el formulario tiene errores...
		if (resultado.hasErrors()) {
			// ...agregamos nuevamente las entidades a nuestra
			// vista, para poder mostrar los mensajes de error
			// que correspondan a cada campo
			model.addAttribute("montoComision", MONTO_COMISION);
			model.addAttribute("formasDePago", formasDePagoRepository.findAll());
			
			retorno = null;
		}
		else {
			// en cambio si no tenemos errores en el formulario
			// también asignamos al pedido la ciudad de origen y
			// destino, que corresponde a la ciudad del usuario
			Ciudad ciudadDelUsuario = pabloAuster.getCiudad();
			pedido.setCiudadOrigen(ciudadDelUsuario);
			pedido.setCiudadDestino(ciudadDelUsuario);
			
			// asignamos al pedido la comisión para guardarlo
			pedido.setComision(MONTO_COMISION);
			
			// calculamos el vuelto a entregar al solicitante
			pedido.calcularVuelto();
			
			// y finalmente lo guardamos en nuestra base de datos
			pedidosRepository.save(pedido);
		}
		
		// pasamos el pedido a la vista, tanto si tiene errores
		// como si ha sido guardado correctamente para mostrarlo
		model.addAttribute("pedido", pedido);

		return retorno;
	}
	
	/**
	 * Devolvemos el Usuario que ha iniciado sesión en la aplicación, 
	 * para los fines del ejercicio devuelve siempre el mismo usuario.
	 * 
	 * @return usuario logueado
	 */
	private Usuario obtenerUsuarioEnSesion() {
		return usuariosRepository.findById(1).get();
	}

}
