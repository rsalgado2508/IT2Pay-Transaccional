package co.com.it2ex.it2pay.util.negocio.servicios.correo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import co.com.it2ex.it2pay.util.modelo.mensajeria.ArchivoAdjuntoDTO;
import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("servicioCorreoSpring")
public class ServicioCorreoSpringImpl implements ServicioCorreoSpring {

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;

	@Autowired
	JavaMailSender mailSender;

	private static final String DEFAULT_PARAMETRO_FROM = "nromero@it2ex.com";


	private SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
	
	@Async
	public void enviarCorreo(CorreoDTO dto) throws Exception {

		long currentTimeMillis = System.currentTimeMillis();
		Date horaInicio = new Date();
		
		MimeMessagePreparator preparator = getMessagePreparator(dto);
		try {
			mailSender.send(preparator);            
		} catch (MailException ex) {
			loggerAuditoriasComponent.registrarLoggerError("", this.getClass(), ex);
			
		} catch(Exception e){
			loggerAuditoriasComponent.registrarLoggerError("", this.getClass(), e);
		}
		finally {
			Date horaFin = new Date();

		}
		

		
	}
	
//	@Override
	public void enviarCorreoSincrono(CorreoDTO dto) throws Exception {
		
		long currentTimeMillis = System.currentTimeMillis();
		Date horaInicio = new Date();

		MimeMessagePreparator preparator = getMessagePreparator(dto);

		try {
			mailSender.send(preparator);            
		} catch (MailException ex) {
			loggerAuditoriasComponent.registrarLoggerError("", this.getClass(), ex);
		} catch(Exception e){
			loggerAuditoriasComponent.registrarLoggerError("", this.getClass(), e);
		}
		finally {
			Date horaFin = new Date();

		}
	}

	private MimeMessagePreparator getMessagePreparator(final CorreoDTO dto) {

		String from = DEFAULT_PARAMETRO_FROM;

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
//			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				try{

					if(dto != null && 
							dto.getAdjunto() != null && 
							dto.getAdjunto().getArchivo() != null || (dto != null && 
									dto.getListaAdjunto() != null && 
									!dto.getListaAdjunto().isEmpty())){
	
						MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
						message.setTo(new InternetAddress(dto.getPara()));
						message.setFrom(new InternetAddress(from));				
	
						//Envio de correo electronico con adjunto
						if(dto != null && 
								dto.getListaAdjunto() != null && 
								!dto.getListaAdjunto().isEmpty()){
							for (ArchivoAdjuntoDTO adjunto : dto.getListaAdjunto()) {
								//message.addAttachment(adjunto.getNombre(),FileUtils.decodeFileBase64(adjunto.getArchivo()) );
							}
						} 
						if(dto != null && 
								dto.getAdjunto() != null && 
								dto.getAdjunto().getArchivo() != null){
							
							//message.addAttachment(dto.getAdjunto().getNombre(), FileUtils.decodeFileBase64(dto.getAdjunto().getArchivo()));
						}
						if(dto.getCopia() != null && 
								dto.getCopia().length > 0){
							InternetAddress[] direccionesCopia = new InternetAddress[dto.getCopia().length];
	
							int i = 0;
	
							for(String correo : dto.getCopia()){						
								InternetAddress internetAddress = new InternetAddress(correo);
								direccionesCopia[i] = internetAddress;
								i++;
							}
	
							message.setCc(direccionesCopia);
						}
	
						if(dto.getCopiaOculto() != null && 
								dto.getCopiaOculto().length > 0){
							InternetAddress[] direccionesCopiaOculta = new InternetAddress[dto.getCopiaOculto().length];
	
							int i = 0;
	
							for(String correoOculto : dto.getCopiaOculto()){						
								InternetAddress internetAddressOculto = new InternetAddress(correoOculto);
								direccionesCopiaOculta[i] = internetAddressOculto;
								i++;
							}
	
							message.setBcc(direccionesCopiaOculta);
						}
						
						if(dto != null && (dto.getContenido() == null || dto.getContenido().trim().equals(""))){

						}
	
						message.setText(dto.getContenido(), true);
						message.setSubject(dto.getAsunto());
					} else {
						MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
						message.setTo(new InternetAddress(dto.getPara()));
						message.setFrom(new InternetAddress(from));				
	
						//mimeMessage.setFrom(new InternetAddress(configuracionMapper.consultarValorConfiguracion(ConstantesCorreo.CORREO_FROM)));
						//mimeMessage.setRecipient(Message.RecipientType.TO,
						//		new InternetAddress(dto.getPara()));
	
						if(dto.getCopia() != null && 
								dto.getCopia().length > 0){
							InternetAddress[] direccionesCopia = new InternetAddress[dto.getCopia().length];
	
							int i = 0;
	
							for(String correo : dto.getCopia()){						
								InternetAddress internetAddress = new InternetAddress(correo);
								direccionesCopia[i] = internetAddress;
								i++;
							}
	
							message.setCc(direccionesCopia);
						}
	
						if(dto.getCopiaOculto() != null && 
								dto.getCopiaOculto().length > 0){
							InternetAddress[] direccionesCopiaOculta = new InternetAddress[dto.getCopiaOculto().length];
	
							int i = 0;
	
							for(String correoOculto : dto.getCopiaOculto()){						
								InternetAddress internetAddressOculto = new InternetAddress(correoOculto);
								direccionesCopiaOculta[i] = internetAddressOculto;
								i++;
							}
	
							message.setBcc(direccionesCopiaOculta);
						}
						
						
						if(dto != null && (dto.getContenido() == null || dto.getContenido().trim().equals(""))){

						}
						
						message.setText(dto.getContenido(), true);
						message.setSubject(dto.getAsunto());
					}
				}catch (Exception e){
					loggerAuditoriasComponent.registrarLoggerError("", this.getClass(), e);
					throw e;
				}
			}
		};
		return preparator;
	}
}
