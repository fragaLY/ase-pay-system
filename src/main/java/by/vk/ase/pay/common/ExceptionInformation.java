package by.vk.ase.pay.common;

import org.springframework.http.HttpStatus;

public record ExceptionInformation(HttpStatus status, Integer code, String message) {

}