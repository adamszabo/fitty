package com.acme.fitness.webmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.products.GeneralProductsService;

@Controller
@RequestMapping("/imageController")
public class ImageController {
	private static final String MIME_PNG = "png";
	private static final String MIME_JPEG = "jpeg";
	private static final String MIME_JPG = "jpg";
	private static final String MIME_GIF = "gif";
	
	private GeneralProductsService gProductService; 
	
	@Autowired
	public ImageController(GeneralProductsService gProductService){
		this.gProductService=gProductService;
	}
	
	@RequestMapping(value="/{imageId}")
	@ResponseBody
	public ResponseEntity<byte[]> getProductImageFromDatabase(@PathVariable long imageId) throws FitnessDaoException{
		ProductImage productImage=gProductService.getProductImageById(imageId);
		
		HttpHeaders header=setHeaderContentType(productImage);
		
		return new ResponseEntity<byte[]>(productImage.getImage(), header, HttpStatus.OK);
	}

	private HttpHeaders setHeaderContentType(ProductImage productImage) {
		HttpHeaders header=new HttpHeaders();
		if(productImage.getMime().equals(MIME_JPG) || productImage.getMime().equals(MIME_JPEG)){
			header.setContentType(MediaType.IMAGE_JPEG);
		}
		else if(productImage.getMime().equals(MIME_PNG)){
			header.setContentType(MediaType.IMAGE_PNG);
		}
		else if(productImage.getMime().equals(MIME_GIF)){
			header.setContentType(MediaType.IMAGE_GIF);
		}
		
		return header;
	}
}
