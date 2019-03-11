package com.mil.deliveryfast.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Pedido {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer numero;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "forma_de_pago_id", nullable = false)
	private FormaDePago formaDePago;
	
	@NotEmpty
	private String calleOrigen;
	private String numeroCalleOrigen;
	private String pisoOrigen;
	private String departamentoOrigen;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ciudad_origen_id", nullable = false)
	private Ciudad ciudadOrigen;
	
	@NotEmpty
	private String calleDestino;
	private String numeroCalleDestino;
	private String pisoDestino;
	private String departamentoDestino;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ciudad_destino_id", nullable = false)
	private Ciudad ciudadDestino;
	
	@NotNull @Min(0)
	private BigDecimal precioServicio;
	
	@NotNull @Min(0)
	private BigDecimal montoEntregado;
	
	private BigDecimal comision;
	
	private BigDecimal vuelto;
	
	@NotEmpty
	private String detalle;
	
	private Boolean idaYVuelta = false;
	
	private LocalDateTime fechaHoraCreacion = LocalDateTime.now();
	
	public Pedido() {
	}
	
	public Pedido(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public FormaDePago getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}

	public String getCalleOrigen() {
		return calleOrigen;
	}

	public void setCalleOrigen(String calleOrigen) {
		this.calleOrigen = calleOrigen;
	}

	public String getNumeroCalleOrigen() {
		return numeroCalleOrigen;
	}

	public void setNumeroCalleOrigen(String numeroCalleOrigen) {
		this.numeroCalleOrigen = numeroCalleOrigen;
	}

	public String getPisoOrigen() {
		return pisoOrigen;
	}

	public void setPisoOrigen(String pisoOrigen) {
		this.pisoOrigen = pisoOrigen;
	}

	public String getDepartamentoOrigen() {
		return departamentoOrigen;
	}

	public void setDepartamentoOrigen(String departamentoOrigen) {
		this.departamentoOrigen = departamentoOrigen;
	}

	public Ciudad getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(Ciudad ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public String getCalleDestino() {
		return calleDestino;
	}

	public void setCalleDestino(String calleDestino) {
		this.calleDestino = calleDestino;
	}

	public String getNumeroCalleDestino() {
		return numeroCalleDestino;
	}

	public void setNumeroCalleDestino(String numeroCalleDestino) {
		this.numeroCalleDestino = numeroCalleDestino;
	}

	public String getPisoDestino() {
		return pisoDestino;
	}

	public void setPisoDestino(String pisoDestino) {
		this.pisoDestino = pisoDestino;
	}

	public String getDepartamentoDestino() {
		return departamentoDestino;
	}

	public void setDepartamentoDestino(String departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}

	public Ciudad getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(Ciudad ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public BigDecimal getPrecioServicio() {
		return precioServicio;
	}

	public void setPrecioServicio(BigDecimal precioServicio) {
		this.precioServicio = precioServicio;
	}

	public BigDecimal getMontoEntregado() {
		return montoEntregado;
	}

	public void setMontoEntregado(BigDecimal montoEntregado) {
		this.montoEntregado = montoEntregado;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public BigDecimal getVuelto() {
		return vuelto;
	}

	public void setVuelto(BigDecimal vuelto) {
		this.vuelto = vuelto;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Boolean getIdaYVuelta() {
		return idaYVuelta;
	}

	public void setIdaYVuelta(Boolean idaYVuelta) {
		this.idaYVuelta = idaYVuelta;
	}
	
	public LocalDateTime getFechaHoraCreacion() {
		return fechaHoraCreacion;
	}

	public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
		this.fechaHoraCreacion = fechaHoraCreacion;
	}

	public BigDecimal calcularTotalPedido() {
		return precioServicio.add(comision);
	}
	
	public void calcularVuelto() {
		if (montoEntregado != null && precioServicio != null && comision != null) {
			vuelto = montoEntregado.subtract(calcularTotalPedido());
		}
	}
	
	public String getDomicilioOrigen() {
		return calleOrigen + " " + numeroCalleOrigen + ", " + ciudadOrigen;
	}
	
	public String getDomicilioDestino() {
		return calleDestino + " " + numeroCalleDestino + ", " + ciudadDestino;
	}
	
	@Override
	public String toString() {
		return "Pedido #" + numero;
	}
}
