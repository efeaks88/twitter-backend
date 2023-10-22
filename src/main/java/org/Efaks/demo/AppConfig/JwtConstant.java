package org.Efaks.demo.AppConfig;

import org.springframework.beans.factory.annotation.Value;

public class JwtConstant {
    @Value("${secretkey}")
    public static String secretkey;
    public static final String JWT_HEADER="Authorization";
}
