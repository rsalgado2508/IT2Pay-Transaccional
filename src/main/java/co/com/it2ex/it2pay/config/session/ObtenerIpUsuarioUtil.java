package co.com.it2ex.it2pay.config.session;

import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Component
public class ObtenerIpUsuarioUtil {

    /** Constante logger. */
    final static Logger logger = LoggerFactory.getLogger(ObtenerIpUsuarioUtil.class);

    @Autowired
    HttpServletRequest request;


    public String ipAccesoUsuario(){

        return ipAccesoUsuario(request);
    }

    public String ipAccesoUsuario(HttpServletRequest request){

        String ipAcceso = null;

        try {

            if(ipAcceso ==null){

                outerloop:
                for (Enumeration e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {

                    NetworkInterface ni = (NetworkInterface) e.nextElement();
                    for (Enumeration ee = ni.getInetAddresses(); ee.hasMoreElements();) {
                        InetAddress ip = (InetAddress) ee.nextElement();
                        if (ip instanceof Inet4Address && !ip.getHostAddress().equals("127.0.0.1")){
                            ipAcceso = ip.getHostAddress();
                            break outerloop;
                        }
                    }
                }
            }
        } catch (Exception e) {
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
                ipAcceso = details.getRemoteAddress();
                logger.info(CaracteresUtil.neutralizeMessage("IPSSS "+ipAcceso));
            } catch (Exception e2) {
                logger.debug((new Exception().getStackTrace()[0].getMethodName())+ ": ", e);
                //System.err.println("Error al obtener la IP");
            }
        }

        return ipAcceso;
    }
}