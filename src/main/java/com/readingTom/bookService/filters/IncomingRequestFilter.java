package com.readingTom.bookService.filters;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@WebFilter("/*")  //for all the incoming requests in the service
@Slf4j
public class IncomingRequestFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.info("Inside the main filter : IncomingRequestFilter :: checking the incoming request...");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse  res= (HttpServletResponse) response;
		
		if (!req.getRequestURL().toString().contains("bookservice/")){  
			log.error("FILTER ERROR ::: OH HO! incoming request is different one!");
			
            res.setStatus(HttpStatus.BAD_GATEWAY.value());
		    res.getOutputStream().flush();
		    res.getOutputStream().println("Incorrect URI -- no data here!!");
            return; 
        }
		
		log.info("Inside the main filter : IncomingRequestFilter :: incoming request LOOKS GOOD");
		log.info("Inside the main filter : IncomingRequestFilter :: entering the Book Service...");
		chain.doFilter(request, response);
		
	}

}
