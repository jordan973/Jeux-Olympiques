package com.example.backend.model;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GenerateurCle {
	
	public String genererCle() {
		return UUID.randomUUID().toString();
	}
}