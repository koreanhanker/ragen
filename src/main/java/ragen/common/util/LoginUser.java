package ragen.common.util;

import lombok.Data;

@Data
public class LoginUser {

   private String usr_id;
   private String auth_cd;
   private String token;
   private String conn_ip;
   public LoginUser() {
   }
   public LoginUser(String usr_id, String auth_cd, String token, String conn_ip) {
      this.usr_id = usr_id;
      this.auth_cd = auth_cd;
      this.token = token;
      this.conn_ip = conn_ip;
   }
}
