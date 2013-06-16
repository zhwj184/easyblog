package org.springweb.controller.front;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ToolsController {
	
	private static final Logger logger = Logger.getLogger(ToolsController.class);

	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public String qrcodeindex(){
		return "blog/tools/qrcode";
	}
	
	
	@RequestMapping(value = "/createqrcode", method = RequestMethod.GET)
	public void qrcode(@RequestParam String qrtext, HttpServletResponse response) {

		ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG)
				.stream();

		response.setContentType("image/png");
		response.setContentLength(out.size());

		OutputStream outStream;
		try {
			outStream = response.getOutputStream();
			outStream.write(out.toByteArray());
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
